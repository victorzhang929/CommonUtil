package com.vz.common.util;

import java.util.Random;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 序列号生成器工具
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-14 11:31:54
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenerationSequenceUtil {

    private static final int MAX_LENGTH = 50;
    private static final int DEFAULT_LENGTH = 6;
    private static final String RANDOM_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * uuid生成器
     * @return 过滤"-"后uuid字符串
     */
    public static String uuid() {
        return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
    }

    /**
     * 生成指定长度的随机数
     * @param length 长度
     * @return 随机数
     */
    public static String random(int length) {
        if (length <= 0) {
            length = DEFAULT_LENGTH;
        }
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        int randomCharacterLength = RANDOM_CHARACTERS.length();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(randomCharacterLength);
            sb.append(RANDOM_CHARACTERS.charAt(number));
        }

        return String.valueOf(sb);
    }

    /**
     * 附加{@code prefix}的uuid生成器
     * @param prefix 前缀
     * @return 附加prefix过滤"-"后uuid字符串
     */
    public static String uuid(String prefix) {
        String uuid = uuid();

        if (StringUtils.isNotEmpty(prefix)) {
            uuid = StringUtils.join(prefix, "-", uuid);
        }

        return uuid;
    }
}
