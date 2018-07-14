package com.capgemini.paymentwallet.pl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import com.capgemini.paymentwallet.bean.Customer;
import com.capgemini.paymentwallet.exception.InsufficientBalanceException;
import com.capgemini.paymentwallet.exception.InvalidInputException;
import com.capgemini.paymentwallet.service.WalletService;
import com.capgemini.paymentwallet.service.WalletServiceImpl;

public class Client {


	private WalletService service;
	Customer cust;
	Scanner console = new Scanner(System.in);
	public Client() {
		service = new WalletServiceImpl();
		cust = new Customer();
	}

	public void menu()
	{
		System.out.println();
		System.out.println();
		System.out.println("----------------MENU-----------------");
		System.out.println("1. Register for Payment Wallet.");
		System.out.println("2. Show Balance.");
		System.out.println("3. Fund Transfer.");
		System.out.println("4. Deposit Amount.");
		System.out.println("5. Withdraw Amount.");
		System.out.println("6. Show Transactions");
		System.out.println("7. Exit.");
		System.out.print("\nEnter your choice:");
		int choice = console.nextInt();
		operation(choice);
	}

	public void operation(int choice) {

		switch (choice) {
		case 1:
			System.out.println("\nEnter your name: ");
			String name = console.next();

			System.out.println("\nEnter your number: ");
			String mobileNo = console.next();

			System.out.println("\nEnter your initial amount");
			BigDecimal amount = console.nextBigDecimal();
			try {
				cust = service.createAccount(name, mobileNo, amount);
				System.out.println("Customer Name: "+ cust.getName());
				System.out.println("Customer Mobile Number: "+ cust.getMobileNo());
				System.out.println("Thank you for registering with us.");
			} 
			catch (InvalidInputException e2) {
				System.out.println(e2.getMessage());
			}
			break;

		case 2:
			System.out.println("Please, enter your registered mobile number");
			String mobileNumber = console.next();
			try {
				cust = service.showBalance(mobileNumber);
				System.out.println("Customer Name: "+ cust.getName());
				System.out.println("Customer Mobile Number: "+ cust.getMobileNo());
				System.out.println("Customer Balance:" +cust.getWallet().getBalance());
			} catch (InvalidInputException e1) {
				System.out.println(e1.getMessage());
			}
			break;

		case 3:
			System.out.println("Please enter source mobile no: ");
			String source = console.next();
			System.out.println("Please, enter target mobile no: ");
			String target = console.next();
			System.out.println("Enter the amount to be tranferred");
			BigDecimal transferAmount = console.nextBigDecimal();
			try {
				cust = service.fundTransfer(source, target, transferAmount);
				System.out.println("Your current balance is: "+ cust.getWallet().getBalance());
			} catch (InsufficientBalanceException | InvalidInputException e) {
				System.out.println(e.getMessage());
			}
			break;	
		case 4:
			System.out.println("Please enter mobile no: ");
			String number = console.next();
			System.out.println("Enter amount to be deposited: ");
			BigDecimal depositAmount = console.nextBigDecimal();
			try {
				cust = service.depositAmount(number, depositAmount);
				System.out.println("Rs. " + depositAmount + " was deposited to your account.");
			} catch (InvalidInputException e1) {
				System.out.println(e1.getMessage());
			}
			break;	
		case 5:
			System.out.println("Please enter mobile no: ");
			String num = console.next();
			System.out.println("Enter amount to be withdraw: ");
			BigDecimal withdrawAmount = console.nextBigDecimal();
			try {
				cust = service.withdrawAmount(num, withdrawAmount);
				System.out.println("Rs. " + withdrawAmount + "was withdrawan from your account.");
			} catch (InsufficientBalanceException | InvalidInputException e) {
				System.out.println(e.getMessage());
			}

			break;
		case 6:
			System.out.println("Please enter mobile number: ");
			String mobileNo1 = console.next();
			List<String> messages;
			try {
				messages = service.showTransaction(mobileNo1);
				Iterator<String> it = messages.iterator();
				while(it.hasNext())
					System.out.println(it.next());
				
			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			}
			break;
			
		case 7:
			System.out.println("Thank you for using our application.Have a nice day!");
			System.exit(0);
			break;	
		default:
			System.out.println("Inavlid Option.");
			break;
		}

	}

	public static void main(String[] args) {
		Client client = new Client();

		while(true)
			client.menu();
	}

}
