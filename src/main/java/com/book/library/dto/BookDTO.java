package com.book.library.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -169283352337062718L;

	private Long id;

	@NotEmpty(message = "ISBN Number must not be empty")
	private String isbnNumber;

	@NotEmpty(message = "Title must not be empty")
	private String title;

	@NotEmpty(message = "Author must not be empty")
	private String author;
}
