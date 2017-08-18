package org.tcat.frame.exception.code;

/**
 * <pre>
 * 1.所有错误码由6位数字组成: 0x012031
 * 2.错误码分为3层: 0xA1B1C1
 * 		A1:模块
 * 			01：公共模块   common → C
 * 			02：字典库      dic → D
 * 			03：用户 	user → U
 * 			04：卖家	supplier → S
 * 			05：买家	buyer → B
 * 			06：产品	product → P
 * 			07：优惠券	cecoupon → Q
 * 			08：
 * 			09：订单	order → O
 * 		B1:该模块下具体的内容（或属性）
 * 		C1:该内容的各种状体信息 （为空[null]，错误[error],失败[fail]，已存在[had]等）,请使用简单的单词来表达
 * example：
 * 		邮箱属于公共的内容：A1=10
 * 		邮箱在公共内容中是:B1=02
 * 		邮箱为空：C1=01  ，完整来看：0x010201
 * 		邮箱错误：C1=02  ，完整来看：0x010202
 * 		邮箱存在：C1=03  ，完整来看：0x010203
 * </pre>
 *
 * @author shadow
 *
 */
public interface ErrorCode extends CommonCode, DictionaryCode, UserCode, SupplierCode, BuyerCode, ProductCode, OrderCode, CecouponCode{
	/** 操作成功 **/
	String success = "0";

	/** 操作失败 **/
	String fail = "1";

	/** 数据异常 **/
	String error = "2";

	/** 用户未登陆 **/
	String unlogin = "3";
}
