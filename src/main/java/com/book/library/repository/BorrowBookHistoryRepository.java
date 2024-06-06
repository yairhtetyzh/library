package com.book.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.book.library.model.BorrowBookHistory;

@Repository
public interface BorrowBookHistoryRepository
		extends JpaRepository<BorrowBookHistory, Long>, JpaSpecificationExecutor<BorrowBookHistory> {

	Optional<BorrowBookHistory> findByBookIdAndBorrowStatus(Long bookId, String borrowed);

	Optional<BorrowBookHistory> findByInvoiceNo(String invoiceNo);

}
