package org.tcat.frame.exception.code;

/**
 * <pre>
 * 1.所有错误码由6位数字组成: 0x012031
 * 2.错误码分为3层: 0xA1B1C1
 * 		A1:模块
 * 			1x：公共模块   common → C
 * 		B1:该模块下具体的内容（或属性）
 * 			10：基础模块
 * 			11：用户信息相关模块
 * 		C1:该内容的各种状体信息 （为空[null]，错误[error],失败[fail]，已存在[had]等）,请使用简单的单词来表达
 * example：
 * 		邮箱属于公共的内容：A1=10
 * 		邮箱在公共内容中是:B1=02
 * 		邮箱为空：C1=01  ，完整来看：110201
 * 		邮箱错误：C1=02  ，完整来看：110202
 * 		邮箱存在：C1=03  ，完整来看：110203
 * </pre>
 * 
 * @author shadow
 *
 */
public interface CommonCode {
	/** 参数错误 **/
	String C_param_illegal = "0x010101";
	/** 日期已失效 **/
	String C_date_expiredd = "0x010102";
	/** 参数缺失 **/
	String C_param_missing = "0x010103";
	/** 验证码错误 **/
	String C_verifycode_fail = "0x010104";

	/** 邮箱为空 **/
	String C_email_null = "0x010201";
	/** 邮箱格式不正确 **/
	String C_email_error = "0x010202";
	/** 邮箱已存在 **/
	String C_email_had = "0x010203";
	/** 邮箱已激活 **/
	String C_email_acted = "0x010204";
	/** 邮箱不存在 **/
	String C_email_noexist = "0x010205";
	/** 邮箱不匹配 **/
	String C_email_nomatch = "0x010206";
	/** 邮箱未验证 **/
	String C_email_novalid = "0x010207";

	/** 手机号码为空 **/
	String C_phone_null = "0x010301";
	/** 手机号码格式不正确 **/
	String C_phone_error = "0x010302";
	/** 手机号码已存在 **/
	String C_phone_had = "0x010303";
	/** 手机号码已激活 **/
	String C_phone_acted = "0x010304";
	/** 手机号码不存在 **/
	String C_phone_noexist = "0x010305";
	/** 手机号码不匹配 **/
	String C_phone_nomatch = "0x010306";
	/** 手机号码未验证 **/
	String C_phone_novalid = "0x010307";

}
