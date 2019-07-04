package com.melink.dialog.controller;


import com.melink.dialog.constant.DialogImageTypeConstant;
import com.melink.dialog.constant.DialogInfo;
import com.melink.dialog.feignClient.SearchApiClient;
import com.melink.dialog.service.InsideEmoticonDialogService;
import com.melink.dialog.service.RandomImageService;
import com.melink.dialog.tuling.Dialog;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.dialog.DialogImageInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
public class InsideEmoticonDialogController extends AbstractDataController<DialogImageInfo> {

    @Autowired
    private RandomImageService randomImageService;
    @Autowired
    private InsideEmoticonDialogService insideEmoticonDialogService;
    @Autowired
    private Dialog dialog;
    @Autowired
    private SearchApiClient searchApiClient;

    @ApiOperation(value = "内部斗图")
    @GetMapping("inside")
    public OpenApiV2DataResponse<DialogImageInfo> getEmoticonById(@RequestParam("guid")String guid){
        return executeApiCall(()->{
            //通过id查到图片的描述
            String text = insideEmoticonDialogService.getEmoticonTextById(guid);
            if (text == null) {
                //上传id 没有text 返回随意搞笑图片
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoById(text, null, emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            System.out.println("内部id查询文案："+text);
            //进行图灵对话，得到返回信息
            String result = null;
            try {
                result = dialog.say(text);
            } catch (Exception e) {
                // 图灵对话返回异常，返回随意搞笑图片
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoById(text, result, emoticon.getRobotReq()));
                return successResponse(emoticon);

            }
            if (StringUtils.isEmpty(result)) {
                // 图灵未返回，回复信息，返回随意搞笑图片
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoById(text, result, emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            //搜索图灵返回对话信息，返回图片
            Long searchTimestart = System.currentTimeMillis();
            OpenApiV2ListResponse<OpenEmoticion> openemoticion = searchApiClient.onlySearch(result, true);
            Long searchTimeEnd = System.currentTimeMillis();
            System.out.println("search执行时间:"+(searchTimeEnd-searchTimestart));
            if (openemoticion == null) {
                // 未搜索到，回复信息，返回随意搞笑图片
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoById(text, result+"-搜索结果为空", emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            List<OpenEmoticion> data = openemoticion.getData();
            if (CollectionUtils.isEmpty(data)) {
                // 未搜索到，回复信息，返回随意搞笑图片
                DialogImageInfo emoticon = randomImageService.getEmoticonByClassify();
                emoticon.setRobotReq(DialogInfo.getInfoById(text, result, emoticon.getRobotReq()));
                return successResponse(emoticon);
            }
            DialogImageInfo imageInfo = new DialogImageInfo();
            imageInfo.setUrl(data.get(0).getMain());
            imageInfo.setType(DialogImageTypeConstant.IMAGE_TYPE_BY_ID);
            imageInfo.setRobotReq(DialogInfo.getInfoById(text, result, data.get(0).getText()));
            return successResponse(imageInfo);
        });

    }
}
