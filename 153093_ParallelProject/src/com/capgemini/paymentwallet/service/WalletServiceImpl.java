package com.capgemini.paymentwallet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.capgemini.paymentwallet.bean.Customer;
import com.capgemini.paymentwallet.bean.Transactions;
import com.capgemini.paymentwallet.bean.Wallet;
import com.capgemini.paymentwallet.exception.InsufficientBalanceException;
import com.capgemini.paymentwallet.exception.InvalidInputException;
import com.capgemini.paymentwallet.repo.WalletRepo;
import com.capgemini.paymentwallet.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {


	private WalletRepo repo;
	Customer customer;
	public WalletServiceImpl(Map<String,Customer> data)
	{
		repo = new WalletRepoImpl(data);
	}

	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}
	public  WalletServiceImpl()
	{
		repo = new WalletRepoImpl();
	}

	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {

		boolean check = false;
		customer = new Customer(name,mobileNo,new Wallet(amount), new Transactions());
		if(isValid(customer))
		{
			check = repo.save(customer);}
		if(check)
			return customer;
		else
			throw new InvalidInputException("Sorry, please register.");
	}

	@Override
	public Customer showBalance(String mobileNo) throws InvalidInputException {
		customer = repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException, InvalidInputException {

		if(amount.compareTo(BigDecimal.ZERO)<=0||sourceMobileNo==null||sourceMobileNo==""||targetMobileNo==""||targetMobileNo==null)
			throw new InvalidInputException("Inputs are empty.");
		Customer source = repo.findOne(sourceMobileNo);
		Customer target = repo.findOne(targetMobileNo);
		if(source==null)
			throw new InvalidInputException("Your details were not found, Please re-enter your details or register to avail our services.");
		if(target==null)
			throw new InvalidInputException("The target number is wrong or has not been registered. Please, register to avail our services.");
		if(source.getWallet().getBalance().compareTo(BigDecimal.ZERO)<0)
			throw new InsufficientBalanceException("Sorry, your balance is insufficient");
		else
		{
			source.getWallet().setBalance(source.getWallet().getBalance().subtract(amount));
			target.getWallet().setBalance(target.getWallet().getBalance().add(amount));

			source.getTransaction().getMessages().add("Rs"+amount+" was tranfered from your account to " + targetMobileNo);
			target.getTransaction().getMessages().add("Rs"+amount+" was transfered to your account from "+sourceMobileNo);

		}

		return source;
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		if(amount.compareTo(BigDecimal.ZERO)<=0||mobileNo==null||mobileNo=="")
			throw new InvalidInputException("Inputs are empty.");
		customer = repo.findOne(mobileNo);
		
		if(customer==null)
			throw new InvalidInputException("Your details were not found, Please re-enter your details or register to avail our services.");
		
		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));
		customer.getTransaction().getMessages().add("An amount of Rs " +amount+" was deposited into your wallet.");
		return customer;


	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException, InvalidInputException{
		if(amount.compareTo(BigDecimal.ZERO)<=0||mobileNo==null||mobileNo=="")
			throw new InvalidInputException("Inputs are empty.");
		
		customer = repo.findOne(mobileNo);

		if(customer==null)
			throw new InvalidInputException("Your details were not found, Please re-enter your details or register to avail our services.");
		
		if(customer.getWallet().getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0)
			throw new InsufficientBalanceException("Insufficient Balance to withdraw");
		customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
		customer.getTransaction().getMessages().add("An amount of Rs " +amount+" was withdrawan from your wallet.");
		return customer;
	}

	public List<String> showTransaction(String mobileNo) throws InvalidInputException {
		if(customer==null)
			throw new InvalidInputException("Your details were not found, Please re-enter your details or register to avail our services.");
		customer = repo.findOne(mobileNo);
		if(customer.getTransaction().getMessages().isEmpty())
			throw new InvalidInputException("No transactions performed.");
		return customer.getTransaction().getMessages();
	}

	public boolean isValid(Customer customer) throws InvalidInputException
	{
		if(customer==null)
			throw new InvalidInputException("Customer details are null.");
		if(customer.getName()==null||isValidName(customer.getName()))
			throw new InvalidInputException("Name cannot be empty.");
		if(customer.getMobileNo()==null||isValidNumber(customer.getMobileNo()))
			throw new InvalidInputException("Mobile Number cannot be empty.");
		return true;
	}

	private boolean isValidNumber(String mobileNo) {
		if(mobileNo.matches("^[7-9]{1}[0-9]{9}$"))
			return false;
		return true;
	}

	private boolean isValidName(String name) {
		if(name.matches("^([A-Z]{1}\\w+)$"))
			return false;
		return true;
	}


}
