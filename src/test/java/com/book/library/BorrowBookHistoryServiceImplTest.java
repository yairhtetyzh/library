package com.book.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.book.library.constant.BorrowStatus;
import com.book.library.dto.BorrowBookHistoryDTO;
import com.book.library.except.RdpException;
import com.book.library.model.BorrowBookHistory;
import com.book.library.repository.BorrowBookHistoryRepository;
import com.book.library.service.impl.BorrowBookHistoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BorrowBookHistoryServiceImplTest {

	@Mock
	private BorrowBookHistoryRepository borrowBookHistoryRepository;

	@InjectMocks
	private BorrowBookHistoryServiceImpl borrowBookHistoryService;

	private BorrowBookHistory borrowBookHistory;

	private BorrowBookHistoryDTO borrowBookHistoryDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		setBorrowBookHistory();
	}

	private void setBorrowBookHistory() {
		// TODO Auto-generated method stub
		borrowBookHistory = new BorrowBookHistory();
		borrowBookHistory.setId(1L);
		borrowBookHistory.setInvoiceNo("f66a642e8b37468494e946b062df197f");
		borrowBookHistory.setBookId(1L);
		borrowBookHistory.setBorrowerId(1L);
		borrowBookHistory.setBorrowStatus(BorrowStatus.BORROWED);

		borrowBookHistoryDTO = new BorrowBookHistoryDTO();
		borrowBookHistoryDTO.setBookId(1L);
		borrowBookHistoryDTO.setBorrowerId(1L);
	}

	@Test
	void testRegister() {
		when(borrowBookHistoryRepository.findByBookIdAndBorrowStatus(1L, BorrowStatus.BORROWED))
				.thenReturn(Optional.of(borrowBookHistory));

		// When & Then
		RdpException exception = assertThrows(RdpException.class,
				() -> borrowBookHistoryService.register(borrowBookHistoryDTO));
		assertEquals("020130", exception.getErrorCode());
		// Verify that the register method was called
		// verify(borrowBookHistoryService, times(1)).register(borrowBookHistoryDTO);
	}

}
