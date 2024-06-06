package com.book.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.book.library.constant.BorrowStatus;
import com.book.library.dto.BookDTO;
import com.book.library.dto.BorrowBookHistoryDTO;
import com.book.library.dto.BorrowBookReq;
import com.book.library.except.RdpException;
import com.book.library.model.Book;
import com.book.library.model.BorrowBookHistory;
import com.book.library.model.Borrower;
import com.book.library.repository.BookRepository;
import com.book.library.repository.BorrowBookHistoryRepository;
import com.book.library.repository.BorrowerRepository;
import com.book.library.service.BorrowBookHistoryService;
import com.book.library.service.impl.BookServiceImpl;
import com.book.library.vo.BookVo;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BorrowerRepository borrowerRepository;

	@Mock
	private BorrowBookHistoryRepository borrowBookHistoryRepository;

	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BorrowBookHistoryService borrowBookHistoryService;

	private Book book1;
	private Book book2;
	private BookDTO bookDTO;
	private Borrower borrower;
	private BorrowBookHistory borrowBookHistory;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		setBookList();
		setBorrower();
		setBorrowBookHistory();
	}

	private void setBorrowBookHistory() {
		borrowBookHistory = new BorrowBookHistory();
		borrowBookHistory.setBookId(1L);
		borrowBookHistory.setBorrowerId(1L);
		borrowBookHistory.setBorrowStatus(BorrowStatus.BORROWED);
		borrowBookHistory.setInvoiceNo("f66a642e8b37468494e946b062df197f");
	}

	private BorrowBookReq generateBorrowBookReq() {
		BorrowBookReq borrowBookReq = new BorrowBookReq();
		borrowBookReq.setBookId(1L);
		borrowBookReq.setBorrowerId(1L);
		return borrowBookReq;
	}

	private void setBorrower() {
		borrower = new Borrower();
		borrower.setId(1L);
		borrower.setName("Stephen");
		borrower.setEmail("stephen@gmail.com");
	}

	private void setBookList() {
		book1 = new Book();
		book1.setId(1L);
		book1.setIsbnNumber("f66a642e8b37468494e946b062df197f");
		book1.setTitle("Book One");
		book1.setAuthor("Author One");

		book2 = new Book();
		book2.setId(2L);
		book2.setIsbnNumber("419b11ac51ed44789c81da7de5d4daae");
		book2.setTitle("Book Two");
		book2.setAuthor("Author Two");

		bookDTO = new BookDTO();
		bookDTO.setIsbnNumber("f66a642e8b37468494e946b062df197f");
		bookDTO.setTitle("Book One");
		bookDTO.setAuthor("Author One");
	}

	@Test
	void testGetAllBooks() {
		List<Book> books = Arrays.asList(book1, book2);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
		when(bookRepository.findAll(pageable)).thenReturn(bookPage);

		List<BookVo> result = bookService.getAllBook(pageable).getContent();

		assertEquals(2, result.size());
		assertEquals("Book One", result.get(0).getTitle());
		assertEquals("Book Two", result.get(1).getTitle());
	}

	@Test
	void testRegisterBook() {
		when(bookRepository.save(any(Book.class))).thenReturn(book1);

		BookDTO result = bookService.register(bookDTO);

		assertNotNull(result);
		assertEquals("Book One", result.getTitle());
		assertEquals(bookDTO.getIsbnNumber(), result.getIsbnNumber());
	}

	@Test
	void testBorrow() {
		BorrowBookReq borrowBookReq = generateBorrowBookReq();
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
		when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));

		when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
		BorrowBookHistoryDTO dto = new BorrowBookHistoryDTO();
		dto.setBookId(borrowBookReq.getBookId());
		dto.setBorrowerId(borrowBookReq.getBorrowerId());

		// Simulate the behavior of borrowBookHistoryService.register() throwing
		// RdpException
		doThrow(new RdpException("Expected Exception")).when(borrowBookHistoryService).register(dto);

		// When & Then
		assertThrows(RdpException.class, () -> bookService.borrowBook(borrowBookReq));

		// Verify that the register method was called
		verify(borrowBookHistoryService, times(1)).register(dto);
	}

}