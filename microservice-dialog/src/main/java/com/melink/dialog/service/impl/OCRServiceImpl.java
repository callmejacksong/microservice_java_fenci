package com.melink.dialog.service.impl;

import com.melink.dialog.ocr.OcrImage;
import com.melink.dialog.service.OCRService;
import com.melink.dialog.thread.OcrThread;
import com.melink.microservice.image.GifDecoder;
import com.melink.microservice.json.JsonSerializer;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class OCRServiceImpl implements OCRService {

    @Autowired
    private OcrImage ocrImage;

    private static ExecutorService pool = null;
    private static  File pack = null;
    static {
        pool = Executors.newFixedThreadPool(3);
        pack = new File("image");
        if (!pack.exists()) {
            pack.mkdir();
        }
    }

    @Override
    public String getTextByImage(MultipartFile image) {
        Random r = new Random();
        String text = "";
        File file = null;
        try {
            String name = System.currentTimeMillis()+"-"+ r.nextInt(1000000) + ".jpg";

            name = System.currentTimeMillis()+"-"+ r.nextInt(1000000) + ".jpg";
            file = multipartFileToFile(image, pack, name);
            long size = image.getSize()/1000;
            if (size >= 30) {
                long s = System.currentTimeMillis();
                Thumbnails.of(image.getInputStream()).scale(0.9f).outputQuality(0.9f).toFile(file);//2.4
                long e = System.currentTimeMillis();
                System.out.println("压缩时间：" + (e - s));
            }
            String result = ocrImage.imageToText(file);
            text = OCRResultFormat(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
        return text;
    }

    // 耗时 大概 0.9 - 1.5 这间
    @Override
    public String getTextByGif(MultipartFile gif) {
        InputStream input = null;

        try {
            input = gif.getInputStream();
            GifDecoder decoder = new GifDecoder();
            int read = decoder.read(input);
            if (read > 0) {
                return null;
            }
            int frameCount = decoder.getFrameCount();
            if (frameCount < 3) {
                // 总帧数少于三取第一个和最后一个
                List<Future<String>> futures = new ArrayList<>();
                futures.add(pool.submit(new OcrThread(ocrImage, decoder,0)));
                futures.add(pool.submit(new OcrThread(ocrImage, decoder,frameCount-1)));
                int len = 0;
                String text = "";
                for (Future<String> f : futures) {
                    String result = f.get();
                    if (result.length() > len) {
                        len = result.length();
                        text = result;
                    }
                }
                return text;
            }{
                // 总帧数大于三取第一个和中间一个和最后一个
                List<Future<String>> futures = new ArrayList<>();
                futures.add(pool.submit(new OcrThread(ocrImage, decoder,0)));
                futures.add(pool.submit(new OcrThread(ocrImage, decoder,frameCount/2)));
                futures.add(pool.submit(new OcrThread(ocrImage, decoder,frameCount-1)));
                int len = 0;
                String text = "";
                for (Future<String> f : futures) {
                    String result = f.get();
                    if (result.length() > len) {
                        len = result.length();
                        text = result;
                    }
                }
                return text;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                streamClose(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private File multipartFileToFile(MultipartFile image,File pack,String fileName) {
        long start = System.currentTimeMillis();
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        File file = null;

        try {
            file = new File(pack, fileName);
            file.createNewFile();
            input = new BufferedInputStream(image.getInputStream());
            output = new BufferedOutputStream(new FileOutputStream(file));

            byte[] b = new byte[1024];
            int len = -1;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }

    private void streamClose(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String OCRResultFormat(String result) throws Exception {
        StringBuilder s = new StringBuilder(" ");
        Map<String, Object> map = JsonSerializer.json2map(result);
        if (!CollectionUtils.isEmpty(map)) {

            Object code = map.get("code");
            if ((Integer)code == 0) {
                Map<String,Object> data = (Map<String,Object>)map.get("data");
                List<Object> items = (List<Object>)data.get("items");
                if (!CollectionUtils.isEmpty(items)) {
                    for (Object o : items) {
                        Map<String, Object> item = (Map<String, Object>) o;
                        String itemstring = (String) item.get("itemstring");
                        if (itemstring.length() > 1) {
                            s.append(itemstring + ",");
                        }else{
                            s.append(itemstring + " ");
                        }

                    }
                }
            }
        }
        Long end = System.currentTimeMillis();
        return s.toString();
    }

}
