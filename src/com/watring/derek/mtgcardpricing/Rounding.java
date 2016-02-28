package com.watring.derek.mtgcardpricing;

import java.text.DecimalFormat;

public class Rounding {
	
	public static String round(double value) {
		DecimalFormat df = new DecimalFormat("#####0.00");
		return df.format(value);
	}
	
	public static double roundDouble(double value) {
		DecimalFormat df = new DecimalFormat("#####0.00");
		return Double.parseDouble(df.format(value));
	}
}
