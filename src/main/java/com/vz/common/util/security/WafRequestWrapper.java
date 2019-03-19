package com.vz.common.util.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 封装Http请求，安全过滤类包装类，装饰者
 * 预防xss攻击以及sql注入
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-13 11:37:59
 */
public class WafRequestWrapper extends HttpServletRequestWrapper {

    private boolean filterXSS;
    private boolean filterSQL;

    WafRequestWrapper(HttpServletRequest request) {
        this(request, true, true);
    }

    WafRequestWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
        super(request);
        this.filterXSS = filterXSS;
        this.filterSQL = filterSQL;
    }

    /**
     * 重写方法，请求参数过滤，预防xss攻击和sql注入
     * @param parameter 请求参数
     * @return 过滤后安全的请求参数值
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (null == values) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = filterParamString(values[i]);
        }
        return encodedValues;
    }

    /**
     * 过滤字符串不安全内容（预防xss攻击，sql注入）
     * @param rawValue 请求参数
     * @return 过滤后安全值
     */
    private String filterParamString(String rawValue) {
        if (null == rawValue) {
            return null;
        }
        String tmp = rawValue;
        if (this.filterXSS) {
            tmp = WafUtil.stripXSS(rawValue);
        }
        if (this.filterSQL) {
            tmp = WafUtil.stripSqlInjection(rawValue);
        }

        return tmp;
    }

    /**
     * 获取请求参数key-value安全内容
     * @return key-value安全内容
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> primary = super.getParameterMap();
        Map<String, String[]> result = new HashMap<>(primary.size());
        for (Map.Entry<String, String[]> entry : primary.entrySet()) {
            result.put(entry.getKey(), filterEntryString(entry.getValue()));
        }

        return result;
    }

    /**
     * 顾虑不安全请求参数数组
     * @param rawValues 参数数组
     * @return 安全参数数组
     */
    private String[] filterEntryString(String[] rawValues) {
        for (int i = 0, length = rawValues.length; i < length; i++) {
            rawValues[i] = filterParamString(rawValues[i]);
        }
        return rawValues;
    }

    @Override
    public String getParameter(String rawValue) {
        return filterParamString(super.getParameter(rawValue));
    }

    @Override
    public String getHeader(String rawValue) {
        return filterParamString(super.getHeader(rawValue));
    }

    @Override
    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(filterParamString(cookie.getValue()));
            }
        }

        return cookies;
    }
}
