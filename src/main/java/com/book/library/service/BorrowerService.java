package com.book.library.service;

import com.book.library.dto.BorrowerDTO;
import com.book.library.except.RdpException;

public interface BorrowerService {

	public BorrowerDTO register(BorrowerDTO borrowerDTO) throws RdpException;
}
