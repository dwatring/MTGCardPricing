package com.watring.derek.mtgcardpricing;

import java.awt.EventQueue;

public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					//CardData.getData("1 endbringer", GUI.lbl, frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
