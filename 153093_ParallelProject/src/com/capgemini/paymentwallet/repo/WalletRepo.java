package com.capgemini.paymentwallet.repo;

import com.capgemini.paymentwallet.bean.Customer;

public interface WalletRepo {

	public boolean save(Customer customer);
	
	public Customer findOne(String mobileNo);
	
}
