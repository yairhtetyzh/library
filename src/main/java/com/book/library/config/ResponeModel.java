package com.book.library.config;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;

import com.book.library.utils.ErrorCode;

public class ResponeModel implements Serializable {



	private final static String GLOBAL_DEFAULT_SUCCESS = "0";
	private final static String GLOBAL_DEFAULT_ERROR = "-1";
	private final static String GLOBAL_DEFAULT_SUCCESS_MSG = "Operation is successful";
	private final static String GLOBAL_DEFAULT_ERROR_MSG = "The operation failure";
	private static final long serialVersionUID = 6119536128765881985L;

	private String code = GLOBAL_DEFAULT_SUCCESS;
	private String msg = GLOBAL_DEFAULT_SUCCESS_MSG;
	private String requestId;
	private long count;

	private Object data;

	public ResponeModel() {
//		requestId = MdcUtil.peek();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public static ResponeModel ok() {
		return ok(GLOBAL_DEFAULT_SUCCESS_MSG);
	}

	public static ResponeModel ok(Object data) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setData(data);
		return responeModel;
	}

	public static ResponeModel ok(String msg) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setMsg(msg);
		return responeModel;
	}

	public static ResponeModel ok(String msg,Object data) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setMsg(msg);
		responeModel.setData(data);
		return responeModel;
	}

	/*
	 * public static ResponeModel ok(PageInfo<?> page) { ResponeModel responeModel =
	 * new ResponeModel(); if(page != null) {
	 * responeModel.setCount(page.getTotal()); responeModel.setData(page.getList());
	 * } return responeModel; }
	 */

	public static ResponeModel error() {
		return error(GLOBAL_DEFAULT_ERROR_MSG);
	}

	public static ResponeModel error(String code, String msg) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setCode(code);
		responeModel.setMsg(msg);
		return responeModel;
	}

	public static ResponeModel error(ErrorCode errorCode) {
		if(null == errorCode){
			return error();
		}
		ResponeModel responeModel = new ResponeModel();
		responeModel.setCode(errorCode.getCode());
		responeModel.setMsg(errorCode.getDesc());
		return responeModel;
	}

	public static ResponeModel error(String code, String msg,Object... params) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setCode(code);
		if (null != params && Strings.isNotEmpty(msg)) {
			MessageFormat mf = new MessageFormat(msg);
			msg = mf.format(params);
		}
		responeModel.setMsg(msg);
		return responeModel;
	}

	public static ResponeModel error(String msg) {
		ResponeModel responeModel = new ResponeModel();
		responeModel.setCode(GLOBAL_DEFAULT_ERROR);
		responeModel.setMsg(msg);
		return responeModel;
	}

	public boolean successFlag(){
		return GLOBAL_DEFAULT_SUCCESS.equals(code);
	}

	/**
	 * 将data设置成为map
	 *
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, String value) {
		if (null == data || (!(data instanceof Map))) {
			Map<String, Object> propertyMap = new HashMap<>();
			propertyMap.put(key, value);
			this.setData(propertyMap);
		} else {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)data;
			map.put(key, value);
		}
	}
}
