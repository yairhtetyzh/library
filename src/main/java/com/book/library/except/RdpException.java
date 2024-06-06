package com.book.library.except;

public class RdpException extends RuntimeException {
	private String errorCode;
	private String errorMsg;
	private String paramNm;

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -7535002921247175833L;
	
	
	/**
	 * Construct with error message.
	 * @param message
	 */
	public RdpException(String message){
        super(message);
		this.errorMsg = message;
	}
	
	/**
	 * Construct with error and inner exception.
	 * @param message
	 * @param ex
	 */
	public RdpException(String message, Throwable ex){
		super(message, ex);
	}

	public RdpException(String errorCode, String message){
        super(message);
		this.errorMsg = message;
		this.errorCode = errorCode;
	}

    public RdpException(String errorCode, String message,String paramNm){
        super(message);
        this.errorMsg = message;
        this.errorCode = errorCode;
        this.paramNm = paramNm;
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getParamNm() {
		return paramNm;
	}

	public void setParamNm(String paramNm) {
		this.paramNm = paramNm;
	}
}
