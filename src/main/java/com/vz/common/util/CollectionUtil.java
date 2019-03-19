package com.vz.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义集合工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-19 11:48:28
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {

    /**
     * 以{@code symbol}为分隔符分割String，并放入Set中
     * @param value 待分割字符串
     * @param symbol 分隔符
     * @return set集合
     */
    public static Set<String> splitIntoSet(String value, String symbol) {
        Set<String> set = new HashSet<>();
        if (StringUtils.isBlank(value)) {
            return set;
        }
        set.addAll(Arrays.asList(value.split(symbol)));
        return set;
    }
}
