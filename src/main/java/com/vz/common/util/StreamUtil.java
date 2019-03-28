package com.vz.common.util;

import java.io.IOException;
import java.io.OutputStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 流操作工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-28 19:07:16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StreamUtil {

    /**
     * 关闭输出流
     * @param outputs 输出流集
     */
    public static void close(OutputStream... outputs) {
        for (OutputStream output : outputs) {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    System.err.println("stream close error");
                    e.printStackTrace();
                }
            }
        }

    }
}
