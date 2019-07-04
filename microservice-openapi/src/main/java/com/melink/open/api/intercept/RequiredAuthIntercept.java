package com.melink.open.api.intercept;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.utils.MD5;
import com.melink.microservice.utils.PlatformUtils;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.model.OpenApp;
import com.melink.open.api.service.OpenAppService;
import com.melink.sop.api.models.RedisPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;

@Component
public class RequiredAuthIntercept extends HandlerInterceptorAdapter {
    @Autowired
    private OpenAppService openAppService;

    @Autowired
    private PlatformCache<String, Object> platformCache;

    private Logger logger = LoggerFactory.getLogger(RequiredAuthIntercept.class);

    @Value("${test.isdebug}")
    private boolean isDebug;

    private List<String> sslValids = Arrays.asList("0", "1", "true", "false");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");

        String uri = request.getRequestURI().toString();
        if (isDebug || isStaticResource(uri, request)) {
            return true;
        }

        logger.warn(getBaseUrl(request));

        HandlerMethod handlerMethod;
        if(handler instanceof HandlerMethod) {
            handlerMethod = (HandlerMethod) handler;
        }else{
            logger.warn("resource--------" + getBaseUrl(request));
            return true;
        }
        Method method = handlerMethod.getMethod();
        NewRequiredAuth annotation = method.getAnnotation(NewRequiredAuth.class);

        if (annotation != null) {
            String t = request.getParameter(annotation.timestampKey());
            if (!StringUtils.hasText(t)) {
                request.getRequestDispatcher("/error/invalid_param/timestamp").forward(request, response);
                return false;
            }
            Long timestamp = PlatformUtils.toLongSafely(t);
            if (timestamp == null) {
                request.getRequestDispatcher("/error/invalid_param/timestamp").forward(request, response);
                return false;
            }
            String sslResStr = request.getParameter(annotation.sslRes());
            if (sslResStr != null) {
                if (!sslValids.contains(sslResStr)) {
                    request.getRequestDispatcher("/error/invalid_param/ssl_res").forward(request, response);
                    return false;
                }
            }
            String appId = request.getParameter(annotation.appIdKey());
            if ((appId == null || !appId.equals(AppConsts.ALIPAYID)) && (System.currentTimeMillis() - timestamp > 300000 || timestamp - System.currentTimeMillis() > 300000)) { // 只处理5分钟内的请求
                request.getRequestDispatcher("/error/bad_request/timestamp").forward(request, response);
                return false;
            }
            OpenApp app = openAppService.getAppByCache(appId);
            if (app == null) {
                request.getRequestDispatcher("/error/invalid_param/app_id").forward(request, response);
                return false;
            }
            if (!app.getIsactive()) {
                String messge = "app is disabled";
                messge = messge.replaceAll(" ", "%20");
                request.getRequestDispatcher("/error/bad_request_message/" + messge).forward(request, response);
                return false;
            }
            if (app.getExpiretime() != null && app.getExpiretime().before(new Date())) {
                String messge = "app is expired";
                messge = messge.replaceAll(" ", "%20");
                request.getRequestDispatcher("/error/bad_request_message/" + messge).forward(request, response);
                return false;
            }

            String fillingStr = app.getAppsecret();

            String sdkVersion = request.getParameter(annotation.sdkVersion());
            if (StringUtils.hasText(sdkVersion) && sdkVersion.contains("jssdk")) {
                String token = request.getParameter(annotation.tokenKey());
                if (!StringUtils.hasText(token)) {
                    request.getRequestDispatcher("/error/expired_token").forward(request, response);
                    return false;
                }

                String jsToken = (String) platformCache.get(RedisPrefix._OPEN_JSSDK_TOKEN + appId, String.class);
                if (!StringUtils.hasText(jsToken) || !jsToken.equals(token)) {
                    request.getRequestDispatcher("/error/expired_token").forward(request, response);
                    return false;
                }
                fillingStr = token;
            }

            String m = request.getParameter(annotation.signature());
            if (!StringUtils.hasText(m)) {
                request.getRequestDispatcher("/error/invalid_param/signature").forward(request, response);
                return false;
            }
            Map parameterMap = request.getParameterMap();
            String signStr = getBaseUrl(request) + fillingStr + constructQueryString(parameterMap, annotation);
            logger.warn(signStr);
            String computedToken = MD5.GetMD5Code(signStr).toUpperCase();
            if (!computedToken.equals(m)) {
                request.getRequestDispatcher("/error/bad_request/signature").forward(request, response);
                return false;
            }
        }

        return true;
    }

    private String constructQueryString(Map<String, String[]> paramMap, NewRequiredAuth annotation) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, String[]> entity : paramMap.entrySet()) {
            if (!entity.getKey().equalsIgnoreCase(annotation.signature())) {
                map.put(entity.getKey(), entity.getValue()[0]);
            }
        }
        return PlatformUtils.buildQueryString(map, true);
    }

    private boolean isStaticResource(String uri, HttpServletRequest request) {
        String path = request.getContextPath();
        return uri.toLowerCase().startsWith(path + "/js") || uri.toLowerCase().startsWith(path + "/fonts") || uri.toLowerCase().startsWith(path + "/images") || uri.toLowerCase().startsWith(path + "/css") || uri.toLowerCase().startsWith(path + "/search") || uri.toLowerCase().startsWith(path + "/img-deal") || uri.toLowerCase().startsWith(path + "/search-test");
    }

    private String getBaseUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String url = URLDecoder.decode(request.getRequestURI().toString(), "utf-8");
        return url;
    }
}
