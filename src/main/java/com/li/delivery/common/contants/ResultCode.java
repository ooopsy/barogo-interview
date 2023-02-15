package com.li.delivery.common.contants;


/**
 * @author cheng.li@bespinglobal.com
 * @date  2020.10.21
 */
public enum ResultCode {
	//	success
	OK(0, "OK"),

	//	general error code
	BAD_REQUEST(9400, "badRequest"),
	NO_AUTHRORIZATION(9401, "noAuthrorization"),
	ACCESS_DENY(9403, "accessDeny"),
	NOT_FOUND(9404, "notFound"),
	NOT_ALLOW(9405, "now allow"),
	DUPLICATE(8000, "Dulicate create"),
	SERVER_ERROR(9500, "internalServerError"),
	ETC_ERROR(9999, "etcError");

	private final int code;
	private final String description;

	ResultCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	
}
