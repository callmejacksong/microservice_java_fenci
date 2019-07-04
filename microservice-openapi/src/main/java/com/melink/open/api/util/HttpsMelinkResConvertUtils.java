package com.melink.open.api.util;

import com.melink.sop.api.models.open.modelinfos.Emoticon;
import com.melink.sop.api.models.open.modelinfos.EmoticonPackage;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class HttpsMelinkResConvertUtils {

    public static EmoticonPackage packageConvert(EmoticonPackage emoticionPackage, boolean sslRes) {
        if (!sslRes || emoticionPackage == null) {
            return emoticionPackage;
        }
        emoticionPackage.setSslRes(sslRes);
        if (!CollectionUtils.isEmpty(emoticionPackage.getEmoticons())) {
            for (Emoticon emoticion : emoticionPackage.getEmoticons()) {
                emoticion.setSslRes(sslRes);
            }
        }
        return emoticionPackage;
    }

    public static List<EmoticonPackage> listPackageConvert(List<EmoticonPackage> emoticionPackages, boolean sslRes) {
        if (!sslRes || emoticionPackages == null || CollectionUtils.isEmpty(emoticionPackages)) {
            return emoticionPackages;
        }
        for (EmoticonPackage emoticionPackage : emoticionPackages) {
            packageConvert(emoticionPackage, sslRes);
        }
        return emoticionPackages;
    }

}
