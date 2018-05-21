package com.victorzhang.common.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 流操作工具类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-05-21 15:10:23
 */
public class StreamUtil {

    /**
     * 关闭输出流
     *
     * @param output
     */
    public static void closeStream(OutputStream... output) {
        for (OutputStream out : output) {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    System.err.printf("stream close error!");
                    e.printStackTrace();
                }
            }
        }
    }

}
