package com.watring.derek.mtgcardpricing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Query {
	public int pos;
	public double cost;
	public int amount;
	public String cardQuery;
	public Image img;
	
	Query(QueryList list){
		File location = new File("Back.jpg");
		try {
			BufferedImage temp = ImageIO.read(location);
			setImg(temp.getScaledInstance(265, 370, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.addQuery(this);
	}
	
	void setImg(Image imgInput){
		img = imgInput;
	}
	
	void setCost(double costInput){
		cost = costInput;
	}
}
