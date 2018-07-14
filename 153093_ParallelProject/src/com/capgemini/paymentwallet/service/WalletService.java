package com.capgemini.paymentwallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.capgemini.paymentwallet.bean.Customer;
import com.capgemini.paymentwallet.exception.InsufficientBalanceException;
import com.capgemini.paymentwallet.exception.InvalidInputException;

public interface WalletService {

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException;
	
	public Customer showBalance(String mobileNo) throws InvalidInputException;
	
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException, InvalidInputException;
	
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException;
	
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException, InvalidInputException;

	public List<String> showTransaction(String mobileNo) throws InvalidInputException;
	
	public boolean isValid(Customer customer) throws InvalidInputException;
	
}
