package com.book.library.utils;

public enum ErrorCode {

	CODE_0000("0000", "successful"),
	ERRORCODE_000211("000211", "Parameter Abnormal"),
	ERRORCODE_009999("009999", "System error, please try again later"),
	ERRORCODE_020124("020124", "Please enter a valid value"),
	ERRORCODE_020125("020125", "Multiple books with the same ISBN number must have same title. There is already ISBN Number(%s) with title (%s)."),
	ERRORCODE_020126("020126", "Multiple books with the same ISBN number must have same author. There is already ISBN Number(%s) with author (%s)."),
	ERRORCODE_020127("020127", "Invalid %s Id."),
	ERRORCODE_020128("020128", "Invalid Invoice No."),
	ERRORCODE_020129("020129", "Already Returned Invoice : (%s)."),
	ERRORCODE_020130("020130", "Book Id (%s) is borrowed by other borrower."),
	ERRORCODE_020111("020111", "No permission!");
	
	private String code;

    private String desc;

    private ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
