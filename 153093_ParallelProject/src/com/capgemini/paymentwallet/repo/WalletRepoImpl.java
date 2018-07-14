package com.capgemini.paymentwallet.repo;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.paymentwallet.bean.Customer;


public class WalletRepoImpl implements WalletRepo {

	private Map<String, Customer> data;

	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data=data;
	}
	
	public WalletRepoImpl()
	{
		data = new HashMap<>();
	}
	
	@Override
	public boolean save(Customer customer) {
		data.put(customer.getMobileNo(), customer);
		return true;
	}

	@Override
	public Customer findOne(String mobileNo) {
		Customer customer = data.get(mobileNo);
		return customer;
	}

}
