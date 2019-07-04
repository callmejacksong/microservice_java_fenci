package com.melink.dialog.test;


import com.melink.dialog.feignClient.OpenApiClient;
import com.melink.dialog.ocr.OcrImage;
import com.melink.dialog.tuling.Dialog;
import com.melink.microservice.image.GifDecoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroserviceDialogTest {

    @Autowired
    private Dialog dialog;
    @Autowired
    private OpenApiClient openApiClient;
    @Autowired
    private OcrImage ocrImage;
    @Test
    public void test1() throws Exception {
        File file = new File("/Users/Admin/Downloads/b.gif");
        FileInputStream input = new FileInputStream(file);
        GifDecoder s = new GifDecoder();
        int read = s.read(input);
        int frameCount = s.getFrameCount();
        System.out.println(read);
        System.out.println(frameCount);
        for (int i = 0; i <= frameCount; i++) {
            BufferedImage frame = s.getFrame(i);
            File file1 = new File("image/"+i+".png");
            file1.createNewFile();
            ImageOutputStream imageout = new FileImageOutputStream(file1);
            boolean write = ImageIO.write(frame, "png", imageout);
            System.out.println(write);
        }

    }

    @Test
    public void test2() throws Exception {

        String s = "csfa";
        String c = "baa";
        String e = "cdda";

        String a = s.length() > c.length() ? s.length() > e.length() ? s : c.length() > e.length() ? c : e : c.length() > e.length() ? c : s.length() > e.length() ? s : e;
        System.out.println(a);
    }

}
