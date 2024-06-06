package com.book.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.library.config.ResponeModel;
import com.book.library.dto.BookDTO;
import com.book.library.dto.BorrowBookReq;
import com.book.library.dto.BorrowBookResp;
import com.book.library.dto.PageDTO;
import com.book.library.dto.ReturnBookReq;
import com.book.library.except.RdpException;
import com.book.library.service.BookService;
import com.book.library.utils.ErrorCode;
import com.book.library.vo.BookVo;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "book")
public class BookController {

	private final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	BookService bookService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponeModel register(@Valid @RequestBody BookDTO bookDTO) {
		logger.debug("Start book register request : [{}] ", bookDTO);
		try {
			bookService.register(bookDTO);

			logger.debug("success book register ... ");
		} catch (RdpException e) {
			// TODO Auto-generated catch block
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		logger.debug("End book register ... ");
		return ResponeModel.ok();
	}

	@RequestMapping(value = "getall", method = RequestMethod.GET)
	public ResponeModel getAllUser(@PageableDefault(size = 10, sort = "createdDate", direction = Direction.DESC) Pageable pageable) {
		logger.debug("Start get all book.....");
		PageDTO<BookVo> bookPage = null;
		try {
			bookPage = bookService.getAllBook(pageable);

			logger.debug("success book register ... ");
		} catch (RdpException e) {
			// TODO Auto-generated catch block
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		ResponeModel responeModel = new ResponeModel();
		responeModel.setCount(bookPage.getTotalElements());
		responeModel.setData(bookPage.getContent());

		logger.debug("End get all book.....");
		return responeModel;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "borrow", method = RequestMethod.POST)
	public ResponeModel borrow(@Valid @RequestBody BorrowBookReq req) {
		logger.debug("Start borrow book request : [{}] ", req);
		BorrowBookResp borrowBookResp = new BorrowBookResp();
		try {
			borrowBookResp = bookService.borrowBook(req);

			logger.debug("end borrow book ... resp [{}] ", borrowBookResp);
		} catch (RdpException e) {
			// TODO Auto-generated catch block
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}
		
		return ResponeModel.ok(borrowBookResp);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "return", method = RequestMethod.POST)
	public ResponeModel returnBook(@Valid @RequestBody ReturnBookReq req) {
		logger.debug("Start return book request : [{}] ", req);
		try {
			bookService.returnBorrowBook(req.getInvoiceNo());

		} catch (RdpException e) {
			// TODO Auto-generated catch block
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}
		
		return ResponeModel.ok();
	}
}
