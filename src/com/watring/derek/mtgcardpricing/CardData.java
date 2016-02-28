package com.watring.derek.mtgcardpricing;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.alee.laf.label.WebLabel;

public class CardData {
	//this file will get all card data from JSON
	public static void getData(Query query, GUI frame){
		System.out.println("ACCESSING HTML");
		Document doc = null;
		final String userAgent = "Mozilla/5.0 (X11; U; Linux i586; en-US; rv:1.8.3) Gecko/20040924 Epiphany/1.4.4 (Ubuntu)";
		try {
			doc = Jsoup.connect("http://store.channelfireball.com/products/search?q="+query.cardQuery).userAgent(userAgent).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element dataHTML = doc.select("div.grid-item-info h3.grid-item-price").first();
		
		//SET THE IMAGE
		URL imgURL = null;
		Image imgNew = null;
		Element imgHTML = doc.select("div.grid-item-info img").first();
		String temp = imgHTML.attr("src");
		
		try {
			imgURL = new URL(temp);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			imgNew = ImageIO.read(imgURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		query.setImg(imgNew.getScaledInstance(265, 370, 1));
		
		//SET THE PRICE
		WebLabel lblNew = (WebLabel) frame.getComponentByName(query.pos + " label");
		frame.setQueryAmount(query);
		Double price = null;
		try{
			price = Double.parseDouble(dataHTML.text().substring(1));
			price = price * query.amount;
		}catch(NullPointerException e){
			System.out.println("Blank");
		}
		String output = "$"+Rounding.round(price);
		query.cost = Rounding.roundDouble(price);
		lblNew.setText(output);
	}
	
}
