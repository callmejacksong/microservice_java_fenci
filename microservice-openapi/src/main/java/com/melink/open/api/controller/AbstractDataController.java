package com.melink.open.api.controller;

import com.melink.microservice.exception.PlatformException;
import com.melink.microservice.propertyeditor.DatePropertyEditor;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

@Controller
public abstract class AbstractDataController<T> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDataController.class);

	protected OpenApiV2DataResponse<T> executeApiCall(ApiCallback<T> callback) {
		OpenApiV2DataResponse<T> responseObj = null;
		try {
			responseObj = callback.call();
		} catch (Exception ex) {
			String message = ex.getMessage();
			if(message == null){
				responseObj = errorResponse("服务器内部错误");
			}else{
				responseObj = errorResponse(message);
			}
			logger.error(ex.getMessage(), ex);
		}
		return responseObj;
	}

	protected OpenApiV2DataResponse<T> errorResponse(String message) {
		return new OpenApiV2DataResponse<T>(ErrorCode._SERVER_ERROR, message);
	}

	protected OpenApiV2DataResponse<T> errorResponse(Integer errorCode, String message) {
		return new OpenApiV2DataResponse<T>(errorCode, message);
	}

	protected OpenApiV2DataResponse<T> errorResponse(PlatformException ex) {
		return new OpenApiV2DataResponse<T>(ex.getCode(), ex.getMessage());
	}

	protected OpenApiV2DataResponse<T> successResponse(T data) {
		return new OpenApiV2DataResponse<T>(ErrorCode._SUCCESS, data, null);
	}

	protected OpenApiV2DataResponse<T> voidResponse() {
		return new OpenApiV2DataResponse<T>(ErrorCode._SUCCESS);
	}

	protected OpenApiV2DataResponse<T> voidResponse(int status) {
		return new OpenApiV2DataResponse<T>(ErrorCode._SUCCESS, String.valueOf(status));
	}

	public interface ApiCallback<T> {
		OpenApiV2DataResponse<T> call();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor());
	}

}
