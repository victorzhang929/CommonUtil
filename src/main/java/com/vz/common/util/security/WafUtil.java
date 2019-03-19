package com.vz.common.util.security;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Web防火墙工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-11-08 09:26:03
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WafUtil {

    private static Pattern SCRIPT_PATTERN = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern END_WITH_SCRIPT_PATTERN = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern END_WITH_SCRIPT_CHARACTER_PATTERN = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern EVAL_PATTERN = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern EXPRESSION_PATTERN = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern JAVASCRIPT_PATTERN = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static Pattern VB_SCRIPT_PATTERN = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static Pattern ON_LOAD_PATTERN = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);

    /**
     * 过滤xss攻击
     * @param value request值
     * @return 无xss脚本攻击值
     */
    static String stripXSS(String value) {
        String rlt = null;
        if (null != value) {
            //avoid null characters
            rlt = value.replaceAll("","");
            // Avoid anything between script tags
            rlt = SCRIPT_PATTERN.matcher(rlt).replaceAll("");
            // Remove any lonesome </script> tag
            rlt = END_WITH_SCRIPT_PATTERN.matcher(rlt).replaceAll("");
            // Remove any lonesome <script ...> tag
            rlt = END_WITH_SCRIPT_CHARACTER_PATTERN.matcher(rlt).replaceAll("");
            // Avoid eval(...) expressions
            rlt = EVAL_PATTERN.matcher(rlt).replaceAll("");
            // Avoid expression(...) expressions
            rlt = EXPRESSION_PATTERN.matcher(rlt).replaceAll("");
            // Avoid javascript:... expressions
            rlt = JAVASCRIPT_PATTERN.matcher(rlt).replaceAll("");
            // Avoid vbscript:... expressions
            rlt = VB_SCRIPT_PATTERN.matcher(rlt).replaceAll("");
            // Avoid onload= expressions
            rlt = ON_LOAD_PATTERN.matcher(rlt).replaceAll("");
        }
        return rlt;
    }

    /**
     * 过滤SQL注入
     * @param value request值
     * @return 无SQL注入值
     */
    static String stripSqlInjection(String value) {
        return (null == value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", "");
    }

    /**
     * 过滤SQL/XSS注入
     * @param value request值
     * @return 无sql-xss注入值
     */
    public static String stripSqlXSS(String value) {
        return stripXSS(stripSqlInjection(value));
    }
}
