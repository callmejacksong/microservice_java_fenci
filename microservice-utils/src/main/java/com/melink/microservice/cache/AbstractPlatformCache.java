package com.melink.microservice.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;


public abstract class AbstractPlatformCache<K, V> implements PlatformCache<K, V> {

    private static Logger log = LoggerFactory.getLogger(AbstractPlatformCache.class);
    private static String _GLOBAL_GROUP_KEY = "all";

    public static final String KEY_FORMAT = "cgid-%s-k-%s";

    private final String name;

    //use threadlocal as sanity check
    private static final ThreadLocal<String> cacheGroupTL = new ThreadLocal<String>();

    protected AbstractPlatformCache(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static String regex = KEY_FORMAT.replaceAll("%s", ".*");
    private static Pattern p = Pattern.compile(regex);

    public String formatKey(String raw) {
        return formatKey(raw, _GLOBAL_GROUP_KEY);
    }

    private String formatKey(String raw, String specifiedGroup) {
        String group;
        group = specifiedGroup;

        return String.format(KEY_FORMAT, new Object[]{group, raw});
    }

    public String getPrefix() {
        return "cgid-" + _GLOBAL_GROUP_KEY + "-k-";
    }

}
