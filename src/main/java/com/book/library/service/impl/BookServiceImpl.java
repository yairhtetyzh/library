package com.book.library.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.book.library.dto.BookDTO;
import com.book.library.dto.BorrowBookHistoryDTO;
import com.book.library.dto.BorrowBookReq;
import com.book.library.dto.BorrowBookResp;
import com.book.library.dto.PageDTO;
import com.book.library.except.RdpException;
import com.book.library.model.Book;
import com.book.library.model.BorrowBookHistory;
import com.book.library.model.Borrower;
import com.book.library.repository.BookRepository;
import com.book.library.repository.BorrowerRepository;
import com.book.library.service.BookService;
import com.book.library.service.BorrowBookHistoryService;
import com.book.library.utils.ErrorCode;
import com.book.library.vo.BookVo;

@Service
public class BookServiceImpl implements BookService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BorrowerRepository borrowerRepository;

	@Autowired
	BorrowBookHistoryService borrowBookHistoryService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BookDTO register(BookDTO bookDTO) throws RdpException {
		// TODO Auto-generated method stub
		validateReq(bookDTO);
		checkISBNNumberAlreadyExist(bookDTO);
		Book book = generateBookModel(bookDTO);
		book.setCreatedDate(LocalDateTime.now());
		book.setUpdatedDate(LocalDateTime.now());
		book = bookRepository.save(book);
		bookDTO.setId(book.getId());
		return bookDTO;
	}

	private void checkISBNNumberAlreadyExist(BookDTO bookDTO) {
		// TODO Auto-generated method stub
		List<Book> bookList = bookRepository.findAllBookByISBNNumber(bookDTO.getIsbnNumber());
		if (!CollectionUtils.isEmpty(bookList)) {
			if (!bookList.get(0).getTitle().equals(bookDTO.getTitle())) {
				logger.info("Multiple books with the same ISBN number must have same Title");
				throw new RdpException(ErrorCode.ERRORCODE_020125.getCode(), String
						.format(ErrorCode.ERRORCODE_020125.getDesc(), bookDTO.getIsbnNumber(), bookDTO.getTitle()));
			}
			if (!bookList.get(0).getAuthor().equals(bookDTO.getAuthor())) {
				logger.info("Multiple books with the same ISBN number must have same Author");
				throw new RdpException(ErrorCode.ERRORCODE_020126.getCode(), String
						.format(ErrorCode.ERRORCODE_020126.getDesc(), bookDTO.getIsbnNumber(), bookDTO.getAuthor()));
			}
		}

	}

	private Book generateBookModel(BookDTO bookDTO) {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setIsbnNumber(bookDTO.getIsbnNumber());
		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		return book;
	}

	private void validateReq(BookDTO bookDTO) {
		// TODO Auto-generated method stub
		if (StringUtils.isAnyBlank(bookDTO.getIsbnNumber(), bookDTO.getTitle(), bookDTO.getAuthor())) {
			logger.info("Request Parameter Abnormal ISBNNumber : {} , Title : {}, Author : {}", bookDTO.getIsbnNumber(),
					bookDTO.getTitle(), bookDTO.getAuthor());
			throw new RdpException(ErrorCode.ERRORCODE_000211.getCode(), ErrorCode.ERRORCODE_000211.getDesc());
		}

	}

	@Override
	public PageDTO<BookVo> getAllBook(Pageable pageable) throws RdpException {
		// TODO Auto-generated method stub
		PageDTO<BookVo> pageDTO = new PageDTO<>();
		Page<Book> page = bookRepository.findAll(pageable);
		List<Book> bookList = page.getContent();
		List<BookVo> bookVoList = new ArrayList<>();
		for (Book book : bookList) {
			bookVoList.add(convertToBookVo(book));
		}

		pageDTO.setContent(bookVoList);
		pageDTO.setNumberofElements(page.getNumberOfElements());
		pageDTO.setPage(page.getNumber());
		pageDTO.setSize(page.getSize());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		return pageDTO;
	}

	private BookVo convertToBookVo(Book book) {
		// TODO Auto-generated method stub
		BookVo bookVo = new BookVo();
		bookVo.setId(book.getId());
		bookVo.setIsbnNumber(book.getIsbnNumber());
		bookVo.setTitle(book.getTitle());
		bookVo.setAuthor(book.getAuthor());
		return bookVo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BorrowBookResp borrowBook(BorrowBookReq req) throws RdpException {
		// TODO Auto-generated method stub
		checkAndGetBook(req.getBookId());
		checkAndGetBorrower(req.getBorrowerId());
		BorrowBookHistoryDTO borrowBookHistoryDTO = new BorrowBookHistoryDTO(req.getBookId(), req.getBorrowerId());
		BorrowBookHistory borrowBookHistory = borrowBookHistoryService.register(borrowBookHistoryDTO);
		BorrowBookResp borrowBookResp = new BorrowBookResp(borrowBookHistory);

		return borrowBookResp;
	}

	private Borrower checkAndGetBorrower(Long borrowerId) {
		// TODO Auto-generated method stub
		Optional<Borrower> borrowerOptional = borrowerRepository.findById(borrowerId);
		if (!borrowerOptional.isPresent()) {
			logger.debug("Invalid Borrower id : {} ", borrowerId);
			throw new RdpException(ErrorCode.ERRORCODE_020127.getCode(),
					String.format(ErrorCode.ERRORCODE_020127.getDesc(), "Borrower"));
		}
		return borrowerOptional.get();
	}

	private Book checkAndGetBook(Long bookId) {
		// TODO Auto-generated method stub
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if (!bookOptional.isPresent()) {
			logger.debug("Invalid Book id : {} ", bookId);
			throw new RdpException(ErrorCode.ERRORCODE_020127.getCode(),
					String.format(ErrorCode.ERRORCODE_020127.getDesc(), "Book"));
		}
		return bookOptional.get();
	}

	@Override
	public void returnBorrowBook(String invoiceNo) {
		// TODO Auto-generated method stub
          borrowBookHistoryService.returnBorrowBook(invoiceNo);
	}

}
