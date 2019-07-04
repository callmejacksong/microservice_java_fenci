package com.melink.open.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by orlan on 2017/3/16.
 */
public class PeriodConsts {
    public static Map<Integer,String> dataMap = new HashMap<Integer,String>();
    public static Map<Integer,String> tantanMap = new HashMap<Integer,String>();
    static{
        dataMap.put(1,"流行表情时间关键词-早安");
        dataMap.put(2,"流行表情时间关键词-吃饭");
        dataMap.put(3,"流行表情时间关键词-下班");
        dataMap.put(4,"流行表情时间关键词-晚安");
        dataMap.put(5,"流行表情时间关键词-晚安");
        tantanMap.put(1,"探探流行表情时间关键词-早安");
        tantanMap.put(2,"探探流行表情时间关键词-中午");
        tantanMap.put(3,"探探流行表情时间关键词-下午");
        tantanMap.put(4,"探探流行表情时间关键词-晚上吃饭");
        tantanMap.put(5,"探探流行表情时间关键词-晚上睡觉");
    }
}
