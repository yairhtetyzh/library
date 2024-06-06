package com.book.library.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BorrowerDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -221792008195529127L;

	private Long id;

	@NotEmpty(message = "Name cannot be empty")
	private String name;

	@NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
	private String email;
}
