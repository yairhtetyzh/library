package com.book.library.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.library.dto.BorrowerDTO;
import com.book.library.except.RdpException;
import com.book.library.model.Borrower;
import com.book.library.repository.BorrowerRepository;
import com.book.library.service.BorrowerService;
import com.book.library.utils.ErrorCode;

@Service
public class BorrowerServiceImpl implements BorrowerService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	BorrowerRepository borrowerRepository;

	@Override
	public BorrowerDTO register(BorrowerDTO borrowerDTO) throws RdpException {
		// TODO Auto-generated method stub
		validateRequest(borrowerDTO);
		Borrower borrower = prepareToModel(borrowerDTO);
		borrower = borrowerRepository.save(borrower);
		borrowerDTO.setId(borrower.getId());
		return borrowerDTO;
	}

	private void validateRequest(BorrowerDTO borrowerDTO) {
		// TODO Auto-generated method stub
		if(StringUtils.isAnyBlank(borrowerDTO.getEmail(), borrowerDTO.getName())) {
			logger.info("Request Parameter Abnormal .");
			throw new RdpException(ErrorCode.ERRORCODE_000211.getCode(), ErrorCode.ERRORCODE_000211.getDesc());
		}
	}

	private Borrower prepareToModel(BorrowerDTO borrowerDTO) {
		// TODO Auto-generated method stub
		Borrower borrower = new Borrower();
		borrower.setName(borrowerDTO.getName());
		borrower.setEmail(borrowerDTO.getEmail());
		borrower.setCreatedDate(LocalDateTime.now());
		borrower.setUpdatedDate(LocalDateTime.now());
		return borrower;
	}

}
