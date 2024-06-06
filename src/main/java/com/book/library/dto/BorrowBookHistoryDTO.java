package com.book.library.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookHistoryDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7616339006208111523L;

	private Long id;

	private String invoiceNo;

	private Long bookId;

	private Long borrowerId;

	private String borrowStatus;
	
	public BorrowBookHistoryDTO(Long bookId, Long borrowerId) {
		this.bookId = bookId;
		this.borrowerId = borrowerId;
	}
}
