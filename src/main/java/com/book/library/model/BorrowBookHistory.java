package com.book.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "borrow_book_history")
public class BorrowBookHistory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7595629945158221533L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "book_id")
	private Long bookId;
	
	@Column(name = "borrower_id")
	private Long borrowerId;
	
	@Column(name = "borrow_status")
	private String borrowStatus;
	
}
