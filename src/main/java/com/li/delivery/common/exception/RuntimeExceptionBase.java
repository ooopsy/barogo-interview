package com.li.delivery.common.exception;

import com.li.delivery.common.contants.ResultCode;

public class RuntimeExceptionBase extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final ResultCode resultCode;
	
	public RuntimeExceptionBase(ResultCode resultCode) {
		super();
		this.resultCode = resultCode;
	}
	
	public RuntimeExceptionBase(ResultCode resultCode, String msg) {
		super(msg);
		this.resultCode = resultCode;
	}

	public RuntimeExceptionBase(ResultCode resultCode, String msg, Throwable t) {
		super(msg, t);
		this.resultCode = resultCode;
	}

	public ResultCode getErrorCode() {
		return resultCode;
	}
}
