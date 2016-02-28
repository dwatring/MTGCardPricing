package com.watring.derek.mtgcardpricing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class QueryList {
	int size = 0;
	Query[] list = new Query[150];
	
	void addQuery(Query newQuery){
		newQuery.pos = size;
		list[size] = newQuery;
		size++;
	}
	
	double getTotal(){
		double total = 0.00;
		for(int i = 0; i < size; i++){
			total += list[i].cost;
		}
		return Rounding.roundDouble(total);
	}
	
	static void resetQuery(Query query){
		File location = new File("Back.jpg");
		try {
			BufferedImage temp = ImageIO.read(location);
			query.setImg(temp.getScaledInstance(265, 370, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		query.cardQuery = "";
		query.amount = 0;
		query.cost = 0.00;
	}
}
