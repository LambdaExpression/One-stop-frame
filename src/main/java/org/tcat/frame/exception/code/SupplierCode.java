package org.tcat.frame.exception.code;
/**
 * <pre>
 * 1.所有错误码由6位数字组成: 0x012031
 * 2.错误码分为3层: 0xA1B1C1
 * 		A1:模块
 * 			40：卖家	supplier → S
 * 		B1:该模块下具体的内容（或属性）
 * 		C1:该内容的各种状体信息 （为空[null]，错误[error],失败[fail]，已存在[had]等）,请使用简单的单词来表达
 * </pre>
 * 
 * @author shadow
 *
 */
public interface SupplierCode { 
	
}
