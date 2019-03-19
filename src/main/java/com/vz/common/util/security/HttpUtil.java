package com.vz.common.util.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 网络请求工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-18 17:27:45
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpUtil {

    private static final String IP_UNKNOWN = "UNKNOWN";
    private static final String IP_LOCAL = "127.0.0.1";
    private static final String IP_LOCAL_16 = "0:0:0:0:0:0:0:1";
    private static final int IP_MAX = 15;
    private static final String REQUEST_BODY = "body";
    private static final String PROXY_IP_SPLIT_SYMBOL = ",";

    /**
     * 获取安全request请求
     * @param request 请求
     * @return 安全封装的请求
     */
    public static HttpServletRequest getRequest(ServletRequest request) {
        return new WafRequestWrapper((HttpServletRequest) request);
    }

    /**
     * 获取安全request，head请求头key-value
     * @param request 请求
     * @param key header请求头key
     * @return 安全header请求头对应的value
     */
    public static String getHeader(ServletRequest request, String key) {
        return getRequest(request).getHeader(key);
    }

    /**
     * 获取安全请求request，参数key对应的value
     * @param request 请求
     * @param key 键
     * @return 安全请求中key对应的value
     */
    public static String getParameter(ServletRequest request, String key) {
        return getRequest(request).getParameter(key);
    }

    /**
     * 获取请求body中的参数
     * @param request 请求
     * @return body的k-v集合
     * @throws IOException 流异常
     */
    public static Map<String, String> getRequestBody(ServletRequest request) throws IOException {
        Map<String, String> body = new HashMap<>(8);

        if (request.getAttribute(REQUEST_BODY) != null) {
            return (Map<String, String>) getRequest(request).getAttribute(REQUEST_BODY);
        } else {
            Map<String, String> data = JSON.parseObject(request.getInputStream(), Map.class);
            body.putAll(data);
            request.setAttribute(REQUEST_BODY, body);
        }

        return body;
    }

    /**
     * 封装response，以json格式返回
     * @param out 响应内容
     * @param response 响应
     * @throws IOException 流异常
     */
    public static void responseWrite(String out, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");

        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.write(out);
        }  finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 获取真实IP，防止代理伪造
     * @param request 请求
     * @return 真实IP
     * @throws UnknownHostException 无法识别客户端地址
     */
    public static String getIp(ServletRequest request) throws UnknownHostException {
        String ip = getHeader(request, "X-Real-IP");
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = getHeader(request, "X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = getHeader(request,"Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = getHeader(request,"WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = getHeader(request,"HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = getHeader(request,"HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (IP_LOCAL.equals(ip) || IP_LOCAL_16.equals(ip)) {
                // 根据网卡获取本机配置的ip
                InetAddress inet = null;
                inet = InetAddress.getLocalHost();
                assert inet != null;
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个ip为客户端真实ip，多个ip按照“,”分割
        if (ip != null && ip.length() > IP_MAX) {
            if (ip.indexOf(PROXY_IP_SPLIT_SYMBOL) > 0) {
                ip = ip.substring(0, ip.indexOf(PROXY_IP_SPLIT_SYMBOL));
            }
        }
        return ip;
    }

}
