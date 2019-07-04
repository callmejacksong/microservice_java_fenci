package com.melink.dialog.controller;

import com.melink.dialog.constant.DialogImageTypeConstant;
import com.melink.dialog.constant.DialogInfo;
import com.melink.dialog.feignClient.SearchApiClient;
import com.melink.dialog.service.InsideEmoticonDialogService;
import com.melink.dialog.service.OCRService;
import com.melink.dialog.service.RandomImageService;
import com.melink.dialog.tuling.Dialog;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.dialog.DialogImageInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
public class WithoutEmoticonDdialogController extends AbstractDataController<DialogImageInfo>{

    @Autowired
    private Dialog dialog;
    @Autowired
    private SearchApiClient searchApiClient;
    @Autowired
    private InsideEmoticonDialogService insideEmoticonDialogService;
    @Autowired
    private RandomImageService randomImageService;
    @Autowired
    private OCRService ocrService;

    @PostMapping("without")
    public OpenApiV2DataResponse<DialogImageInfo> getEmoticonOcr(@RequestParam("image") MultipartFile file) {
        return executeApiCall(() -> {

            String text = "";
            //对gif动画进行ocr识别
            if (file.getOriginalFilename().endsWith(".gif")) {
                text = ocrService.getTextByGif(file);
            }else {
                //对image图片弄丢ocry识别
                text = ocrService.getTextByImage(file);
            }
            if (StringUtils.isEmpty(text)) {
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoByOcr(text,null,emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            long start = System.currentTimeMillis();
            text = text.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5, .]+", "");//去掉特殊字符和数字
            text = text.replaceAll("([^a-zA-Z])[a-zA-Z]([^a-zA-Z])","$1$2");//去掉 单个出现的字母
            text = text.replaceAll("([^0-9])[0-9]([^0-9])","$1$2"); // 去掉 单个出现的数字
            long end = System.currentTimeMillis();
            System.out.println("字符串过滤TIME：" + (end - start));
            //text = text.replaceAll("[a-zA-Z]", "");//去掉单独出现的字符
            System.out.println("imageText:"+text);
            String resultText = null;
            try {
                resultText = dialog.say(text);
            } catch (Exception e) {
                // 图灵异常
                e.printStackTrace();
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoByOcr(text,resultText,emoticon.getRobotReq()));
                return successResponse(emoticon);

            }
            if (StringUtils.isEmpty(resultText)) {
                //图灵返回为null
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoByOcr(text,resultText,emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            Long searchTimestart = System.currentTimeMillis();
            OpenApiV2ListResponse<OpenEmoticion> openEmoticion = searchApiClient.onlySearch(resultText, true);
            Long searchTimeEnd = System.currentTimeMillis();
            System.out.println("search执行时间:"+(searchTimeEnd-searchTimestart));
            if (openEmoticion == null) {
                // 搜索返回图片为空
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoByOcr(text,resultText+"-搜索结果为空",emoticon.getRobotReq()));
                return successResponse(emoticon);
            }

            if (CollectionUtils.isEmpty(openEmoticion.getData())) {
                // 搜索结果为空
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoByOcr(text,resultText+"-搜索结果为空",emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            OpenEmoticion open = openEmoticion.getData().get(0);
            DialogImageInfo imageInfo = new DialogImageInfo();
            imageInfo.setUrl(open.getMain());
            imageInfo.setType(DialogImageTypeConstant.IMAGE_TYPE_BY_OCR);
            imageInfo.setRobotReq(DialogInfo.getInfoByOcr(text, resultText, open.getText()));
            return successResponse(imageInfo);
        });
    }

}
