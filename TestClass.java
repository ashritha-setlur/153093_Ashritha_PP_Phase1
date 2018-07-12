package com.capgemini.paymentwallet.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.paymentwallet.bean.Customer;
import com.capgemini.paymentwallet.bean.Wallet;
import com.capgemini.paymentwallet.service.WalletService;
import com.capgemini.paymentwallet.service.WalletServiceImpl;
import com.capgemini.paymentwallet.exception.InsufficientBalanceException;
import com.capgemini.paymentwallet.exception.InvalidInputException;;


public class TestClass {

	static WalletService service;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=InvalidInputException.class)
	public void testCreateAccount1() throws InvalidInputException 
	{
		service.createAccount(null, "9942221102", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount2() throws InvalidInputException 
	{
		service.createAccount("", "9942221102", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3() throws InvalidInputException 
	{
		service.createAccount("john", "999", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4() throws InvalidInputException 
	{
		service.createAccount("john", "", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5() throws InvalidInputException 
	{
		service.createAccount("", "", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance11() throws InvalidInputException 
	{
		service.showBalance(null);		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance12() throws InvalidInputException 
	{
		service.showBalance("");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance13() throws InvalidInputException 
	{
		service.showBalance("12345");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance14() throws InvalidInputException 
	{
		service.showBalance("9900112218");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance15() throws InvalidInputException 
	{
		service.showBalance("99001122122");		
	}
	
	
	@Test
	public void testShowBalance16() throws InvalidInputException 
	{
		Customer customer=service.showBalance("9900112212");
		BigDecimal expectedResult=new BigDecimal(9000);
		BigDecimal obtainedResult=customer.getWallet().getBalance();
		
		assertEquals(expectedResult, obtainedResult);
		
	}

	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer17() throws InsufficientBalanceException 
	{
		service.fundTransfer("9948484810", "9922950519", new BigDecimal(5000));		
	}
	
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer18() throws InsufficientBalanceException 
	{
		service.fundTransfer("9922950519", "9922950519", new BigDecimal(5000));		
	}

	
	@Test(expected=InsufficientBalanceException.class)
	public void testFundTransfer19() throws InsufficientBalanceException 
	{
		service.fundTransfer("9900112212", "9922950519", new BigDecimal(12000));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer20() throws InsufficientBalanceException 
	{
		service.fundTransfer("9900112212", "9922950519", new BigDecimal(0));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer21() throws InsufficientBalanceException 
	{
		service.fundTransfer("9900112212", "", new BigDecimal(0));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer22() throws InsufficientBalanceException 
	{
		service.fundTransfer("", "9922950519", new BigDecimal(500));		
	}
	
	
	@Test
	public void testFundTransfer23() throws InsufficientBalanceException 
	{
		Customer customer=service.fundTransfer("9900112212", "9922950519", new BigDecimal(500));
		BigDecimal expected=customer.getWallet().getBalance();
		BigDecimal actual=new BigDecimal(8500);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFundTransfer24() throws InsufficientBalanceException 
	{
		Customer customer=service.fundTransfer("9900112212", "9922950519", new BigDecimal(550.50));
		BigDecimal expected=customer.getWallet().getBalance();
		BigDecimal actual=new BigDecimal(8449.50);
		
		assertEquals(expected, actual);
	}
	
	
	@Test(expected=InsufficientBalanceException.class)
	public void testFundTransfer25() throws InsufficientBalanceException 
	{
		Customer customer=service.fundTransfer("9900112212", "9922950519", new BigDecimal(15000));	
	}
	
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer28() throws InsufficientBalanceException 
	{
		service.fundTransfer(null, null, new BigDecimal(0));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer29() throws InsufficientBalanceException 
	{
		service.fundTransfer("9922950519", null, new BigDecimal(50));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer30() throws InsufficientBalanceException 
	{
		service.fundTransfer("9922950519", "9963242422", new BigDecimal(0));		
	}

	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount31() throws InvalidInputException 
	{
		service.depositAmount(null, new BigDecimal(500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount32() throws InvalidInputException 
	{
		service.depositAmount("", new BigDecimal(500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount33() throws InvalidInputException 
	{
		service.depositAmount("9942221102", new BigDecimal(500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount34() throws InvalidInputException 
	{
		service.depositAmount("9942221102", new BigDecimal(0));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount35() throws InvalidInputException 
	{
		service.depositAmount("9922950519", new BigDecimal(-1000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount36() throws InvalidInputException 
	{
		service.depositAmount("9922950519", new BigDecimal(200000));
	}
	

	
	@Test(expected=InsufficientBalanceException.class)
	public void testWithdrawAmount37() throws InsufficientBalanceException 
	{
		service.withdrawAmount("9900112212", new BigDecimal(15000));	
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testWithdrawAmount38() throws InsufficientBalanceException 
	{
		service.withdrawAmount("9900112212", new BigDecimal(0));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testWithdrawAmount39() throws InsufficientBalanceException 
	{
		service.withdrawAmount("8754922472", new BigDecimal(5000));	
	}

}
