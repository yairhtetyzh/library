package com.book.library.service;

import com.book.library.dto.BorrowBookHistoryDTO;
import com.book.library.except.RdpException;
import com.book.library.model.BorrowBookHistory;

public interface BorrowBookHistoryService {
	
	public BorrowBookHistory register(BorrowBookHistoryDTO borrowBookHistoryDTO) throws RdpException;

	public void returnBorrowBook(String invoiceNo) throws RdpException;

}
