package com.vz.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 验证工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @since 2019-03-19 11:35:04
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidateUtil {

    private static final String REG_MOBILE = "^((\\+86)|(86))?(13|15|17|18)\\d{9}$";
    private static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 验证手机号码是否合法
     * @param mobile 手机号码
     * @return 是否合法
     */
    public static boolean isMobileNumber(final String mobile) {
        return StringUtils.isNotEmpty(mobile) && mobile.matches(REG_MOBILE);
    }

    /**
     * 验证邮箱地址是否合法
     * @param email 邮箱地址
     * @return 是否合法
     */
    public static boolean isEmail(final String email) {
        return StringUtils.isNotEmpty(email) && email.matches(REG_EMAIL);
    }
}
