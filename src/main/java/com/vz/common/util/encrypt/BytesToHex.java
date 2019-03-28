package com.vz.common.util.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 字节码转换为十六进制
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-28 19:39:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BytesToHex {

    /**
     * 字节码转换为十六进制
     * @param bytes 字节码
     * @return 十六进制数
     */
    public static String fromBytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            if (Integer.toHexString(0xFF & b).length() == 1) {
                builder.append("0").append(Integer.toHexString(0xFF & b));
            } else {
                builder.append(Integer.toHexString(0xFF & b));
            }
        }

        return String.valueOf(builder);
    }
}
