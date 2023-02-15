package com.li.delivery.common.model;

import java.util.Date;

import com.li.delivery.common.contants.ResultCode;

public class ResponseModel<T> {
	private Date timestamp = new Date();

    private Integer code;

    private String codeName;

    private String desc;

    private T resultData;

    public ResponseModel(ResultCode resultCode) {
        init(resultCode);
    }

    public ResponseModel(ResultCode resultCode, T resultData) {
        init(resultCode);
        this.resultData = resultData;
    }

    private void init(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.codeName = resultCode.name();
        this.desc = resultCode.getDescription();
    }

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}
    
    
}
