package com.book.library.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class BookVo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6834220574597034073L;
	private Long id;

	private String isbnNumber;

	private String title;

	private String author;
}
