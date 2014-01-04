package com.example.bleedred;

import java.util.Calendar;

public class User {
	private String username;//Unique
	private String name;
	private String password;
	private String phNo;
	private int age;
	private String group;
	private char sign;
	private Calendar lastDonation;
	
	public User(String uname,String nm,String pword,String pN, int ag, String gr, char sg){
		username = ""+uname;
		name = ""+nm;
		password = ""+pword;
		phNo = ""+pN;
		age = ag;
		group = ""+gr;
		sign = sg;
	}
	
	public boolean canDonate(){
		Calendar current = Calendar.getInstance();
		if(Math.abs(current.MONTH-lastDonation.MONTH)>=4)
			return false;
		return true;
		
	}
	
	public void hasDonated(){
		//Calendar current = Calendar.getInstance();
		lastDonation = Calendar.getInstance();
	}
}
