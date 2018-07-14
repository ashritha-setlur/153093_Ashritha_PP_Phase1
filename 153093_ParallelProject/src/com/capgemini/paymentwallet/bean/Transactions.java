package com.capgemini.paymentwallet.bean;

import java.util.ArrayList;
import java.util.List;


public class Transactions {
	
	List<String> messages;
	
	public Transactions()
	{
		
		messages=new ArrayList<>();
	}
	

	public Transactions(List<String> messages) {
		super();
		this.messages = messages;
	}


	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	

	@Override
	public String toString() {
		return "Transactions [messages=" + messages + "]";
	}
	
	
	
}
