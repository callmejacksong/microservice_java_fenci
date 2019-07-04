package com.melink.dialog.controller.matchingGIF;

import com.melink.dialog.controller.AbstractDataController;
import com.melink.dialog.service.MatchingGifService;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.dialog.mathingGIF.MathingGifsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Random;

@RestController
@RequestMapping("")
public class ShenpeituGifDataController extends AbstractDataController<MathingGifsInfo> {

    @Autowired
    private MatchingGifService matchingGifService;

    @GetMapping("matching")
    public OpenApiV2DataResponse<MathingGifsInfo> shenpeituURL(
            @RequestParam("guid") String guid,
            @RequestParam("text") String text,
            @RequestParam("hasText") Boolean hasText
    ) {

        return executeApiCall(() -> {
            Random r = new Random();
            String[] fonts = new String[]{"仿宋", "黑体", "楷体", "宋体", "微软雅黑"};
            String[] colors = new String[]{"green", "red", "blue", "turquoise blue", "navy blue"};
            String uri = "http://7xl6jm.com2.z0.glb.qiniucdn.com/";
            String url =  matchingGifService.getURLByGuid(guid);
            MathingGifsInfo info = new MathingGifsInfo();
            if (hasText) {
                info.setUrl(uri + url);
                return successResponse(info);
            }
            String encode = null;
            String font = null;
            String color = null;

            Base64.Encoder encoder = Base64.getEncoder();
           // String s = encoder.encodeToString(text.getBytes("UTF-8"));


            try {
                encode = encoder.encodeToString(text.getBytes("UTF-8"));
                font = encoder.encodeToString(fonts[r.nextInt(fonts.length)].getBytes("UTF-8"));
                color = encoder.encodeToString(colors[r.nextInt(colors.length)].getBytes("UTF-8"));
//                encode = URLEncoder.encode(text,"UTF-8");
//                font = URLEncoder.encode(fonts[r.nextInt(fonts.length)],"UTF-8");
//                color = URLEncoder.encode(colors[r.nextInt(fonts.length)],"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String params = "?watermark/2/text/" + encode + "/gravity/South/fontsize/1000/fill/" + color + "/font/" + font;
            info.setUrl(uri + url + params);
            return successResponse(info);
        });

    }
}
