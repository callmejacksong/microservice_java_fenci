package com.melink.microservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class for holding utility methods
 *
 *
 */
public class PlatformUtils {

	private static final Logger log = LoggerFactory.getLogger(PlatformUtils.class);

	private static String LOCALHOST_NAME;
	static {
		try {
			InetAddress add = InetAddress.getLocalHost();
			LOCALHOST_NAME = add.getHostAddress();
		} catch (UnknownHostException ignored) {

		}
	}

	public static Long toLongSafely(String s) {
		try {
			return Long.valueOf(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer toIntegerSafely(String s) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static int toIntSafely(String s,int def) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}


	public static String getLocalHostName() {
		return LOCALHOST_NAME;
	}

	public static String buildQueryString(Map<String, String> parameters) {
		return buildQueryString(parameters, false);
	}

	public static String buildQueryString(Map<String, String> parameters, boolean sortKey) {
		if (parameters == null || parameters.isEmpty()) {
			return "";
		}
		StringBuilder queryString = new StringBuilder();
		if (sortKey) {
			List<String> sortedKeyList = new ArrayList<String>();
			sortedKeyList.addAll(parameters.keySet());
			Collections.sort(sortedKeyList);
			for (String key : sortedKeyList) {
				queryString.append(key).append("=").append(parameters.get(key)).append("&");
			}
		} else {
			for (String key : parameters.keySet()) {
				queryString.append(key).append("=").append(parameters.get(key)).append("&");
			}
		}
		queryString.delete(queryString.length() - 1, queryString.length());
		return queryString.toString();
	}
}
