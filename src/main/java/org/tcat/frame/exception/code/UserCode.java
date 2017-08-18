package org.tcat.frame.exception.code;

/**
 * <pre>
 * 1.所有错误码由6位数字组成: 0x012031
 * 2.错误码分为3层: 0xA1B1C1
 * 		A1:模块
 * 			30：用户 	user → U
 * 		B1:该模块下具体的内容（或属性）
 * 		C1:该内容的各种状体信息 （为空[null]，错误[error],失败[fail]，已存在[had]等）,请使用简单的单词来表达
 * </pre>
 *
 * @author shadow
 */
public interface UserCode {
    // ***********************************************
    /**
     * 账号和密码不匹配
     **/
    String U_login_fail = "0x030101";
    /**
     * 账号状态异常
     **/
    String U_login_deny = "0x030102";
    /**
     * 账号未登录
     **/
    String U_login_Un = "0x030103";

    /**
     * 账号不存在
     **/
    String U_account_null = "0x030201";
    /**
     * 账号已存在
     **/
    String U_account_had = "0x030202";
    /**
     * 邮箱已存在
     **/
    String U_email_had = "0x030203";

    /**
     * 密码为空
     **/
    String U_passwd_null = "0x030301";
    /**
     * 密码不正确
     **/
    String U_passwd_fail = "0x030302";

    /**用户类型为空**/
    String U_type_null = "0x030401";
    /**用户类型错误**/
    String U_type_error = "0x030402";

    /**账号余额不足*/
    String U_balance_little = "0x030501";
}
