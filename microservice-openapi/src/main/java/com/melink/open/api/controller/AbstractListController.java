package com.melink.open.api.controller;

import com.melink.microservice.exception.PlatformException;
import com.melink.microservice.json.JsonSerializer;
import com.melink.sop.api.models.ApiResponseObject;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public abstract class AbstractListController<T> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractListController.class);

	protected OpenApiV2ListResponse<T> executeApiCall(ApiCallback<T> callback) {
		OpenApiV2ListResponse<T> responseObj = null;
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

	protected OpenApiV2ListResponse<T> errorResponse(String message) {
		return new OpenApiV2ListResponse<T>(ErrorCode._SERVER_ERROR, message);
	}

	protected OpenApiV2ListResponse<T> errorResponse(Integer errorCode, String message) {
		return new OpenApiV2ListResponse<T>(errorCode, message);
	}

	protected String errorResponseToStr(Integer errorCode, String message) {
		ApiResponseObject<String> stringApiResponseObject = new ApiResponseObject<String>(errorCode, message);
		JsonSerializer jsonSerializer = JsonSerializer.newInstance();
		String s = jsonSerializer.toJson(stringApiResponseObject);
		return s;
	}

	public OpenApiV2ListResponse<T> successResponse(List<T> dataList, OpenApiPagination pagination) {
		OpenApiV2ListResponse<T> openApiResponseObject = new OpenApiV2ListResponse<T>(ErrorCode._SUCCESS, dataList, pagination);
		return openApiResponseObject;
	}

	protected OpenApiV2ListResponse<T> errorResponse(PlatformException ex) {
		return new OpenApiV2ListResponse<T>(ex.getCode(), ex.getMessage());
	}

	protected OpenApiV2ListResponse<T> voidResponse() {
		return new OpenApiV2ListResponse<T>(ErrorCode._SUCCESS);
	}

	protected OpenApiV2ListResponse<T> voidResponse(boolean status) {
		return new OpenApiV2ListResponse<T>(ErrorCode._SUCCESS, String.valueOf(status));
	}

	protected OpenApiV2ListResponse<T> successResponse(List<T> dataList) {
		return new OpenApiV2ListResponse<T>(ErrorCode._SUCCESS, dataList, null);
	}

	public interface ApiCallback<T> {
		OpenApiV2ListResponse<T> call();
	}

//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new DatePropertyEditor());
//	}

	//封闭分页信息：总页数，当前页，当前页数，每页总数
	public OpenApiPagination generatePagination(int total, int count, int page, int size) {
		OpenApiPagination pagination = new OpenApiPagination();
		pagination.setTotalCount(total);
		pagination.setCount(count);
		page--;
		int offset = page * size;
		pagination.setOffset(offset);
		return pagination;
	}

}
