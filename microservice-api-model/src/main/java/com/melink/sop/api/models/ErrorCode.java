package com.melink.sop.api.models;

public final class ErrorCode {

	public static final Integer _SUCCESS = 0;

	/**
	 * 非法的参数
	 */
	public static final Integer _INVALID_PARAMETER = 10001;

	public static final Integer _INVALID_SECRET = 20001;

	public static final Integer _INVALID_ACCESS_TOKEN = 30001;

	public static final Integer _BAD_REQUEST = 40001;
	
	/**
	 * 特殊错误参数
	 */
	public static final Integer _BAD_PLATFORM_NKEY = 40002;

	/**
	 * 支付宝参数错误
	 */
	public static final Integer _ALIPAY_INVALID_PARAMETER = 1001;

	/**
	 * 所有未专门定义错误编码统一使用该错误编码
	 */
	public static final Integer _SERVER_ERROR = 500;
}
