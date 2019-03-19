package com.vz.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Cookie工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-18 17:20:08
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CookieUtil {

    /**
     * HTTPOnly设置cookie，需要response添加header形式，JavaEE并没有做支持
     * @param response 响应
     * @param name 名称
     * @param value 值
     * @param path 路径
     * @param maxAge 最长有效事件
     * @param isHttpOnly 是否httpOnly，防止xss
     */
    public static void add(HttpServletResponse response, String name, String value, String path, int maxAge, boolean isHttpOnly) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("=").append(value).append(";");
        if (maxAge > 0) {
            sb.append("Max-Age=").append(maxAge).append(";");
        }
        if (null != path) {
            sb.append("path=").append(path).append(";");
        } else {
            sb.append("path=").append("/").append(";");
        }
        if (isHttpOnly) {
            sb.append("HTTPOnly;");
        }

        response.addHeader("Set-Cookie", String.valueOf(sb));
    }

    /**
     * 添加Cookie信息
     * @param response 响应
     * @param name 名称
     * @param value 值
     * @param path 路径
     * @param maxAge 最长有效事件
     */
    public static void add(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (null != path) {
            cookie.setPath(path);
        } else {
            cookie.setPath("/");
        }
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    public static void add(HttpServletResponse response, String name, String value, String path) {
        add(response, name, value, path, 0);
    }

    public static void add(HttpServletResponse response, String name, String value, int maxAge) {
        add(response, name, value, "/", maxAge);
    }

    public static void add(HttpServletResponse response, String name, String value) {
        add(response, name, value, "/", 0);
    }

    /**
     * 删除Cookie
     * 设置Cookie有效期为0，值为null即可
     * @param response 响应
     * @param name 名称
     */
    public static void remove(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie
     * @param request 请求
     * @param name 名称
     * @return Cookie信息
     */
    public static String get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (StringUtils.equals(name, cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
