package com.watring.derek.mtgcardpricing;

import java.awt.Image;

public class Query {
	public double cost;
	public String name;
	public String cardQuery;
	public Image img;
	public int amount;
	public Query next;
	
	Query(QueryList list){
		list.addQuery(this);
	}
	
	void setAmount(int amountInput){
		amount = amountInput;
	}
	
	void setImg(Image imgInput){
		img = imgInput;
	}
	
	void setCost(double costInput){
		cost = costInput;
	}
	
	void setName(String nameInput){
		name = nameInput;
	}
}
