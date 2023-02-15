package com.li.delivery.common.advice;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.li.delivery.common.contants.ResultCode;
import com.li.delivery.common.exception.RuntimeExceptionBase;
import com.li.delivery.common.model.ResponseModel;

@RestControllerAdvice
public class ControllerExceptionAdvice {

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({RuntimeExceptionBase.class})
	public ResponseModel<String> handleRuntimeExceptionBase(WebRequest request, RuntimeExceptionBase exception) {
		return new ResponseModel<>(exception.getErrorCode(), exception.getMessage());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseModel<String> handleMethodArgumentNotValidException(WebRequest request, MethodArgumentNotValidException exception) {
		return new ResponseModel<>(ResultCode.BAD_REQUEST, exception.getMessage());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseModel<String> handleMethodArgumentNotValidException(WebRequest request, BadCredentialsException exception) {
		return new ResponseModel<>(ResultCode.NO_AUTHRORIZATION, exception.getMessage());
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ConversionFailedException.class})
	public ResponseModel<String> conversionFailedException(WebRequest request, ConversionFailedException exception) {
		return new ResponseModel<>(ResultCode.BAD_REQUEST, exception.getMessage());
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseModel<String> handleException(WebRequest request, Exception exception) {
		return new ResponseModel<>(ResultCode.SERVER_ERROR, exception.getMessage());
	}
	

}
