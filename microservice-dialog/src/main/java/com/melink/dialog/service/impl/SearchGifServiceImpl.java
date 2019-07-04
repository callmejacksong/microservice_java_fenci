package com.melink.dialog.service.impl;

import com.melink.dialog.feignClient.SearchApiClient;
import com.melink.dialog.feignClient.TrendingApiClient;
import com.melink.dialog.mapper.BasicNetPictureMapper;
import com.melink.dialog.model.BasicNetPicture;
import com.melink.dialog.nlp.NlpTextpolar;
import com.melink.dialog.service.SearchGifService;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.dialog.mathingGIF.MathingGifsInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchGifServiceImpl implements SearchGifService {

    @Autowired
    private TrendingApiClient trendingApiClient;
    @Autowired
    private BasicNetPictureMapper basicNetPictureMapper;
    @Autowired
    private NlpTextpolar nlpTextpolar;
    @Autowired
    private SearchApiClient searchApiClient;

    @Override

    public List<MathingGifsInfo> searchGifs() {
        List<MathingGifsInfo> result = new ArrayList<>();
        OpenApiV2ListResponse<OpenEmoticion> response = trendingApiClient.notTextInfoTrending(true, 1, 50);
        if (response != null) {
            List<OpenEmoticion> data = response.getData();
            for (OpenEmoticion openEmoticion : data) {
                MathingGifsInfo mathingGifsInfo = new MathingGifsInfo();
                mathingGifsInfo.setGuid(openEmoticion.getGuid());
                mathingGifsInfo.setUrl(openEmoticion.getMain());
                mathingGifsInfo.setHasText(false);
                result.add(mathingGifsInfo);
            }

        }
        return result;
    }

    @Override
    public List<MathingGifsInfo> searchGifsByText(String q) {
        final int size = 50;
        List<MathingGifsInfo> result = new ArrayList<>();

        List<BasicNetPicture> basicNetPictureList = basicNetPictureMapper.searchPictureDensionByText(q, size);
        if (!CollectionUtils.isEmpty(basicNetPictureList)) {
            for (BasicNetPicture basic : basicNetPictureList) {
                MathingGifsInfo info = new MathingGifsInfo();
                info.setHasText(true);
                info.setUrl(basic.getStoreUrl());
                info.setGuid(basic.getGuid());
                info.setText(basic.getName());
                info.setProcedure(1);
                info.setSfrom(basic.getSfrom());
                result.add(info);
            }
        }

        if (result.size() < size) {
            OpenApiV2ListResponse<OpenEmoticion> response = searchApiClient.searchEmojiNotTextInfo(q, size - result.size());
            if (response != null) {
                List<OpenEmoticion> data = response.getData();
                if (!CollectionUtils.isEmpty(data)) {
                    for (OpenEmoticion basic : data) {
                        MathingGifsInfo info = new MathingGifsInfo();
                        info.setHasText(false);
                        info.setUrl(basic.getThumb());
                        info.setGuid(basic.getGuid());
                        info.setText(basic.getText());
                        info.setProcedure(2);
                        info.setSfrom("");
                        result.add(info);
                    }
                }
            }
        }

        if (result.size() < size) {
            Integer polar = null;
            try {
                 polar = nlpTextpolar.textPolar(q);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (polar != null) {
                List<Integer> polars = new ArrayList<>();
                if (polar.equals(-1)) {
                    polars.add(2);
                    polars.add(3);
                    polars.add(4);
                }
                if (polar.equals(1)) {
                    polars.add(1);
                }
                if (polar.equals(0)) {
                    polars.add(5);
                }
                List<BasicNetPicture> basics = basicNetPictureMapper.searchPictureByMood(polars, size - result.size());
                if (!CollectionUtils.isEmpty(basics)) {
                    for (BasicNetPicture basic : basics) {
                        MathingGifsInfo info = new MathingGifsInfo();
                        info.setHasText(false);
                        info.setUrl(basic.getStoreUrl());
                        info.setGuid(basic.getGuid());
                        info.setText(basic.getName());
                        info.setProcedure(3);
                        info.setSfrom("");
                        result.add(info);
                    }
                }
            }
        }
        return result;
    }


}
