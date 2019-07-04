package com.melink.dialog.thread;

import com.melink.dialog.ocr.OcrImage;
import com.melink.microservice.image.GifDecoder;
import com.melink.microservice.json.JsonSerializer;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

public class OcrThread implements Callable<String> {
    private OcrImage ocrImage;
    private GifDecoder decoder;
    private int number;
    private static File pack = null;

    public OcrThread(OcrImage ocr, GifDecoder decoder,int number) {
        this.ocrImage = ocr;
        this.decoder = decoder;
        this.number = number;
    }

    public OcrThread(){

    }

    static{
        pack = new File("image");
        if (!pack.exists()) {
            pack.mkdir();
        }
    }

    @Override

    public String call() throws Exception {

        Random r = new Random();
        File file = null;
        File jpgFile = null;
        String s = "";
        FileInputStream input = null;
        try {

            file = GifToPng(decoder, number, pack, System.currentTimeMillis() + "-" + r.nextInt(1000000) + ".png");
            if (file.length() / 1000 >= 30) {
                String path = file.getPath();
                String f = path.substring(0, path.lastIndexOf('.'));
                jpgFile = new File(f + ".jpg");
                file.renameTo(jpgFile);
                input = new FileInputStream(jpgFile);
                Thumbnails.of(input).scale(0.9f).outputQuality(0.9f).toFile(jpgFile);
                s = ocrImage.imageToText(jpgFile);

                System.out.println("GIF压缩："+ f);
            } else {
                s = ocrImage.imageToText(file);
            }

        } catch (Exception e) {
        } finally {
            if (input != null) {
                input.close();
            }
            if (file != null) {
                file.delete();
            }
            if (jpgFile != null) {
                jpgFile.delete();
            }

            System.out.println("thread:" + System.currentTimeMillis());
        }
        return OCRResultFormat(s);
    }

    private File GifToPng(GifDecoder decoder,int position,File pack,String fileName ) {
        BufferedImage frame = null;
        File file = null;
        try {
            frame = decoder.getFrame(position);
            file = new File(pack, fileName);
            file.createNewFile();
            ImageIO.write(frame, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
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
