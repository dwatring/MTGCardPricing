package com.watring.derek.mtgcardpricing;

import java.text.DecimalFormat;

import com.alee.laf.label.WebLabel;

public class Arithmetic {
	
	public static String getTotal(GUI frame){
		double total = 0;
		for(int i=0;i<=GUI.count;i++){
			String labelName = i + " label";
			try{
			total +=  Double.parseDouble(((WebLabel) frame.getComponentByName(labelName)).getText().substring(1));
			}catch(StringIndexOutOfBoundsException e){
			}
		}
		return "Total: $"+round(total);
	}
	
	public static String round(double value) {
		DecimalFormat df = new DecimalFormat("#####0.00");
		return df.format(value);
	}
	
	public static void updatePriceForMultiplier(double multiplier, GUI frame, int i){
		if(i<0){
			frame.revalidate();
			frame.repaint();
		}
		else{
			String labelName = i + " label";
			double temp = Double.parseDouble(((WebLabel) frame.getComponentByName(labelName)).getText().substring(1));
			String output = round(temp * multiplier);
			((WebLabel) frame.getComponentByName(labelName)).setText(output);
			updatePriceForMultiplier(multiplier, frame, --i);
		}
	}
}
