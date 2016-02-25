package com.watring.derek.mtgcardpricing;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alee.extended.image.WebImage;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextField;

public class CardData {
	//this file will get all card data from JSON
	public static void getData(String cardName, WebLabel lblNew, GUI frame){
		Document doc = null;
		final String userAgent = "Mozilla/5.0 (X11; U; Linux i586; en-US; rv:1.8.3) Gecko/20040924 Epiphany/1.4.4 (Ubuntu)";
		try {
			doc = Jsoup.connect("http://store.channelfireball.com/products/search?q="+cardName).userAgent(userAgent).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element dataHTML = doc.select("div.grid-item-info h3.grid-item-price").first();
		
		//SET THE IMAGE
		URL imgURL = null;
		Image imgNew = null;
		Element imgHTML = doc.select("div.grid-item-info img").first();
		try{
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
		}catch(NullPointerException e){
			File location = new File("Back.jpg");
			try {
				imgNew = ImageIO.read(location);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		GUI.img.setImage(imgNew.getScaledInstance(265, 370, 1));
		
		//SET THE PRICE
		updatePrice(lblNew, dataHTML);
		GUI.total.setText(frame.getTotal());
	}
	
	static void updatePrice(WebLabel lblNew, Element dataHTML){
		try{
		lblNew.setText(dataHTML.text());
		}catch(NullPointerException e){
			System.out.println("NO PRICE");
		}
	}
}
