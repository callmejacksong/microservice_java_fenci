package com.melink.sop.api.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by orlan on 2017/2/13.
 */
public class AppConsts {
    public static String TANTAN = "b19657b521f34b52bbc94d5f84995f64";
    public static String ALIPAY = "alipay";
    public static String ALIPAYID = "c2e0a093dfa544fc980cd388f335b1f2";
    public static String WANGWANG = "4f6896797d4045b3b453f554a60d275a";
    public static String DINGDING = "5613144ffde64d1d9be5d8b93998e90a";
    public static String MOMO = "1247d6b11ae949779e7a0c85f0cef4af";
    public static String WEB = "d3c73b7bd2f24e4abcfa689f4361b310";
    public static String QQZONE = "ec176e29e1cc46e6a0847986d95c5979";
    public static String DONG2 = "00577c6b75c244dd997a17ebea6509d5";
    public static String ZUJI = "7a36d34ca08341df834e65def1edaffc";
    public static String YIZHOUCP = "32902b123b0943ae9ffbb257913aaa20";
    public static String XIMALAYA = "38faeeb6d38c4307bc4d635439a42d9b";
    public static String XIAOENAI = "2701a32e83594cc284cf4ee4b61d5ba1";
    public static String DTYZMINI = "19b41d7a75e844958e7fe97874bc09f6";
    public static String QQINPUT = "04c25dfdb74d4b9b912862a2bde1d11d";

    public static Integer TEST_TYPE = 2;

    public static HashMap<String,List<String>> AppIdDomainUrlMap = new HashMap<String,List<String>>();

    static{
        ArrayList<String> qqZoneUrl = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_SOSO_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_QQZONE_HTTPS_BUCKET);

        ArrayList<String> tanTan = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_TANTAN_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_TANTAN_HTTPS_BUCKET);

        ArrayList<String> aliPay = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_SOSO_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_ALIPAY_HTTPS_BUCKET);

        ArrayList<String> wangwang = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_WANGWANG_HTTPS_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_WANGWANG_HTTPS_BUCKET);

        ArrayList<String> dong2 = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_SOSO_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET);

        ArrayList<String> momo = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_SOSO_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_MOMO_HTTPS_BUCKET);

        ArrayList<String> dingding = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_DINGDING_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_DINGDING_HTTPS_BUCKET);

        ArrayList<String> yiZhouCP = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_YIZHOUCP_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_YIZHOUCP_HTTPS_BUCKET);

        ArrayList<String> ximAlaya = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_XIMALAYA_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_XIMALAYA_HTTPS_BUCKET);

        ArrayList<String> xiaoEnAi = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_XIAOENAI_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_XIAOENAI_HTTPS_BUCKET);

        ArrayList<String> dtyzmini = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_SOSO_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_DTYZMINI_HTTPS_BUCKET);

        ArrayList<String> qqInput = new ArrayList<>();
        qqZoneUrl.add(QiNiuBucket.BQMM_QQINPUT_BUCKET);
        qqZoneUrl.add(QiNiuBucket.BQMM_QQINPUT_HTTPS_BUCKET);


        AppIdDomainUrlMap.put(QQZONE, qqZoneUrl);
        AppIdDomainUrlMap.put(TANTAN, tanTan);
        AppIdDomainUrlMap.put(ALIPAY, aliPay);
        AppIdDomainUrlMap.put(WANGWANG, wangwang);
        AppIdDomainUrlMap.put(DONG2, dong2);
        AppIdDomainUrlMap.put(MOMO, momo);
        AppIdDomainUrlMap.put(DINGDING, dingding);
        AppIdDomainUrlMap.put(YIZHOUCP, yiZhouCP);
        AppIdDomainUrlMap.put(XIMALAYA, ximAlaya);
        AppIdDomainUrlMap.put(XIAOENAI, xiaoEnAi);
        AppIdDomainUrlMap.put(DTYZMINI, dtyzmini);
        AppIdDomainUrlMap.put(QQINPUT, qqInput);

    }
}
