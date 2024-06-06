package com.book.library.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.library.constant.BorrowStatus;
import com.book.library.constant.CommonConstant;
import com.book.library.dto.BorrowBookHistoryDTO;
import com.book.library.except.RdpException;
import com.book.library.model.BorrowBookHistory;
import com.book.library.repository.BorrowBookHistoryRepository;
import com.book.library.service.BorrowBookHistoryService;
import com.book.library.utils.ErrorCode;

@Service
public class BorrowBookHistoryServiceImpl implements BorrowBookHistoryService{
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	BorrowBookHistoryRepository borrowBookHistoryRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BorrowBookHistory register(BorrowBookHistoryDTO borrowBookHistoryDTO) throws RdpException {
		// TODO Auto-generated method stub
		validate(borrowBookHistoryDTO);
		BorrowBookHistory borrowBookHistory = prepareToModel(borrowBookHistoryDTO);
		borrowBookHistory = borrowBookHistoryRepository.save(borrowBookHistory);
		return borrowBookHistory;
	}

	private void validate(BorrowBookHistoryDTO borrowBookHistoryDTO) {
		// TODO Auto-generated method stub
		if(Objects.isNull(borrowBookHistoryDTO.getBookId()) || Objects.isNull(borrowBookHistoryDTO.getBorrowerId())) {
			logger.info("Parameter Abnormal , request : [{}] ", borrowBookHistoryDTO);
			throw new RdpException(ErrorCode.ERRORCODE_000211.getCode(), ErrorCode.ERRORCODE_000211.getDesc());
		}
		
		Optional<BorrowBookHistory> borrowOptional = borrowBookHistoryRepository.findByBookIdAndBorrowStatus(borrowBookHistoryDTO.getBookId(), BorrowStatus.BORROWED);
		if(borrowOptional.isPresent()) {
			logger.info("Book Id : [{}] is Already Borrowed", borrowBookHistoryDTO.getBookId());
			throw new RdpException(ErrorCode.ERRORCODE_020130.getCode(), String.format(ErrorCode.ERRORCODE_020130.getDesc(), borrowBookHistoryDTO.getBookId()));
		}
	}

	private BorrowBookHistory prepareToModel(BorrowBookHistoryDTO borrowBookHistoryDTO) {
		// TODO Auto-generated method stub
		BorrowBookHistory borrowBookHistory = new BorrowBookHistory();
		borrowBookHistory.setInvoiceNo(UUID.randomUUID().toString().replaceAll(CommonConstant.HYPHEN_SYMBOL,""));
		borrowBookHistory.setBookId(borrowBookHistoryDTO.getBookId());
		borrowBookHistory.setBorrowerId(borrowBookHistoryDTO.getBorrowerId());
		borrowBookHistory.setBorrowStatus(BorrowStatus.BORROWED);
		borrowBookHistory.setCreatedDate(LocalDateTime.now());
		borrowBookHistory.setUpdatedDate(LocalDateTime.now());
		return borrowBookHistory;
	}

	@Override
	public void returnBorrowBook(String invoiceNo) throws RdpException {
		// TODO Auto-generated method stub
		BorrowBookHistory borrowBookHistory = validateInvoice(invoiceNo);
		checkAlreadyReturn(borrowBookHistory);
		updateBorrowBookStatus(borrowBookHistory);
	}

	private void updateBorrowBookStatus(BorrowBookHistory borrowBookHistory) {
		// TODO Auto-generated method stub
		borrowBookHistory.setBorrowStatus(BorrowStatus.RETURNED);
		borrowBookHistory.setUpdatedDate(LocalDateTime.now());
		borrowBookHistoryRepository.save(borrowBookHistory);
	}

	private void checkAlreadyReturn(BorrowBookHistory borrowBookHistory) {
		// TODO Auto-generated method stub
		if(borrowBookHistory.getBorrowStatus().equals(BorrowStatus.RETURNED)) {
			logger.info("Already Return Book.");
			throw new RdpException(ErrorCode.ERRORCODE_020129.getCode(), String.format(ErrorCode.ERRORCODE_020129.getDesc(), borrowBookHistory.getInvoiceNo()));
		}
	}

	private BorrowBookHistory validateInvoice(String invoiceNo) {
		// TODO Auto-generated method stub
		Optional<BorrowBookHistory> borrowOptional = borrowBookHistoryRepository.findByInvoiceNo(invoiceNo);
	    if(!borrowOptional.isPresent()) {
	    	logger.info("Invalid InvoiceNo : {} ", invoiceNo);
	    	throw new RdpException(ErrorCode.ERRORCODE_020128.getCode(), ErrorCode.ERRORCODE_020128.getDesc());
	    }
	    
	    return borrowOptional.get();
	}

}
