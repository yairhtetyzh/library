package com.book.library.service;
import org.springframework.data.domain.Pageable;

import com.book.library.dto.BookDTO;
import com.book.library.dto.BorrowBookReq;
import com.book.library.dto.BorrowBookResp;
import com.book.library.dto.PageDTO;
import com.book.library.except.RdpException;
import com.book.library.vo.BookVo;

public interface BookService {
	
     public BookDTO register(BookDTO bookDTO) throws RdpException;
     
     public PageDTO<BookVo> getAllBook(Pageable pageable) throws RdpException;
     
     public BorrowBookResp borrowBook(BorrowBookReq req) throws RdpException;
     
     public void returnBorrowBook(String invoiceNo) throws RdpException;
	
}
