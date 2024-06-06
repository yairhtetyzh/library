package com.book.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.library.config.ResponeModel;
import com.book.library.dto.BorrowerDTO;
import com.book.library.except.RdpException;
import com.book.library.service.BorrowerService;
import com.book.library.utils.ErrorCode;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "borrower")
public class BorrowerController {

	private final Logger logger = LoggerFactory.getLogger(BorrowerController.class);

	@Autowired
	BorrowerService borrowerService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponeModel register(@Valid @RequestBody BorrowerDTO borrowerDTO) {
		logger.debug("Start borrower register request : [{}] ", borrowerDTO);
		try {
			borrowerService.register(borrowerDTO);

			logger.debug("success borrower register ... ");
		} catch (RdpException e) {
			// TODO Auto-generated catch block
			logger.error("Error is {}", e);
			return ResponeModel.error(e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("System Abnormal Exception is {}", e);
			return ResponeModel.error(ErrorCode.ERRORCODE_009999.getCode(), ErrorCode.ERRORCODE_009999.getDesc());
		}

		logger.debug("end borrower register ...  ");
		return ResponeModel.ok();
	}
}
