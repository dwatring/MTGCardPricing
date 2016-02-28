package com.watring.derek.mtgcardpricing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.extended.image.WebImage;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;

public class GUI extends WebFrame implements KeyListener, ChangeListener, FocusListener{
	private static final long serialVersionUID = 1L;
	
	/**
	 * GUI Class Components
	 */
	private JPanel contentPane = new JPanel();
	private static JTextField textField;
	static WebImage img = new WebImage();
	static WebLabel total;
	static WebPanel panel;
	static WebSpinner multiplier;
	static int count = 0;
	protected HashMap<String, Component> componentMap;
	private JLabel lblMultiplier;

	/**
	 * Create the frame and class components
	 */
	public GUI() {
		this.setTitle("MTG Card Pricing (Derek-Watring.com)");
		initializeGUI();
		createComponentMap();
	}
	
	public void setQueryAmount(Query query){
		query.amount = Integer.parseInt(getCurrentTextField().getText().substring(0, 2).trim());
	}
	
	int getCurrentTextFieldName() {
		if(this.getFocusOwner().getName() != null)
			return Integer.parseInt(this.getFocusOwner().getName());
		else return -1;
	}
	
	WebTextField getCurrentTextField() {
		if(this.getFocusOwner().getName() != null)
			return (WebTextField) this.getFocusOwner();
		else return null;
	}
	
	WebLabel getCurrentWebLabel() {
		String name = this.getFocusOwner().getName();
		if(name != null)
			return (WebLabel) getComponentByName(name + " label");
		else return null;
	}
	
	void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = panel.getComponents();
        for (int i=0; i < components.length; i++) {
                componentMap.put(components[i].getName(), components[i]);
        }
	}
	
	Component getComponentByName(String name) {
	        if (componentMap.containsKey(name)) {
	                return (Component) componentMap.get(name);
	        }
	        else return null;
	}
	
	double getTotal(){
		double total = 0.00;
		for(int i=0; i <= count; i++){
			total += Double.parseDouble(((WebLabel) getComponentByName(i + " label")).getText().substring(1));
		}
		return total;
	}
	
	void clear(){
		panel.removeAll();
		count = 0;
		Main.primaryList = null;
		Main.primaryList = new QueryList();
		createNewRow();
	    total.setText("Total: $0.00");
	}
	
	static void setImage(Image imgInput){
		img.setImage(imgInput);
	}
	
	void update(){
		Query query = Main.primaryList.list[getCurrentTextFieldName()];
		setImage(query.img);
	}
	
	public void updatePriceForMultiplier(double multiplier, int i){
		if(i<0){
			this.revalidate();
			this.repaint();
		}
		else{
			String output = Rounding.round(Main.primaryList.list[i].cost * multiplier);
			((WebLabel) this.getComponentByName(i + " label")).setText("$"+output);
			updatePriceForMultiplier(multiplier, --i);
			GUI.total.setText("Total: $"+Rounding.roundDouble(this.getTotal()));
		}
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		updatePriceForMultiplier((double)multiplier.getValue(), count);
	}
	@Override
	public void focusGained(FocusEvent e) {
		update();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		WebTextField curr = getCurrentTextField();
		Query query = Main.primaryList.list[Integer.parseInt(curr.getName())];
		if(e.getKeyCode() == 9){
			if(query.pos == count){
		        count++;
		        createNewRow();
			    createComponentMap();
			    this.revalidate();
			}
		}
		if(e.getKeyCode() == 10){
			if(curr.getText().isEmpty() == false){
				query.cardQuery = curr.getText().substring(2).trim();
				CardData.getData(query, this);
			}
			else QueryList.resetQuery(query);
			img.setImage(query.img);
			total.setText("Total: $"+Rounding.roundDouble(getTotal()));
		}
	}
	
	void initializeGUI(){
		setResizable(false);
		try {
	    	WebLookAndFeel.install();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 500);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				panel = new WebPanel();
				panel.setWebColoredBackground(false);
				panel.setPaintBackground(false);
				panel.setPaintBottom(false);
				panel.setPaintTop(false);
				panel.setPaintLeft(false);
				panel.setPaintRight(false);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
				gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0};
				panel.setLayout(gbl_panel);
		
		WebScrollPane scrollPane = new WebScrollPane(panel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(18);
		scrollPane.setDrawFocus(false);
		scrollPane.setPaintButtons(true);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 402, 439);
		scrollPane.setViewportView(panel);
		contentPane.add(scrollPane);
		
				textField = new WebTextField();
				new Query(Main.primaryList);
				Dimension size = new Dimension(0, 27);
				textField.setPreferredSize(size);
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.gridwidth = 2;
				gbc_textField.insets = new Insets(5, 5, 5, 5);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 0;
				gbc_textField.gridy = 0;
				textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
				textField.setFocusTraversalKeysEnabled(false);
				textField.setName(Integer.toString(count));
				textField.addFocusListener(this);
				textField.addKeyListener(this);
				panel.add(textField, gbc_textField);
		
		WebLabel lbl = new WebLabel("$0.00");
		GridBagConstraints gbc_lbl = new GridBagConstraints();
		gbc_lbl.insets = new Insets(5, 5, 5, 5);
		gbc_lbl.gridx = 2;
		gbc_lbl.gridy = count;
		lbl.setName(Integer.toString(count) + " label");
		lbl.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		panel.add(lbl, gbc_lbl);
		
				WebLabel warning = new WebLabel("Always begin each line with the amount");
				warning.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
				warning.setForeground(Color.RED);
				warning.setBounds(10, 453, 300, 14);
				contentPane.add(warning);
		
		total = new WebLabel("Total: $0.00");
		total.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		total.setBounds(336, 453, 120, 14);
		contentPane.add(total);
		
				lblMultiplier = new JLabel("Cost Multiplier");
				lblMultiplier.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
				lblMultiplier.setBounds(500, 399, 84, 14);
				contentPane.add(lblMultiplier);
		
		WebButton clearButton = new WebButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		clearButton.setBounds(594, 427, 100, 23);
		contentPane.add(clearButton);
		
				WebButton readFromFileButton;
				readFromFileButton = new WebButton("Read");
				readFromFileButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//getInputFromFile();
					}
				});
				readFromFileButton.setBounds(490, 427, 100, 23);
				contentPane.add(readFromFileButton);
				
		multiplier = new WebSpinner();
		double min = 0.00, value = 1.00, max = 2.00, stepSize = 0.01;
		SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, stepSize);
		multiplier.setModel(model);
		multiplier.setBounds(594, 392, 100, 30);
		contentPane.add(multiplier);
		multiplier.addChangeListener(this);
		
				img.setBounds(425, 11, 269, 370);
				contentPane.add(img);
	}
	
	
	void createNewRow(){
        //CREATE NEW TEXTFIELD
        WebTextField textFieldNew = new WebTextField();
		Query query = new Query(Main.primaryList);
		Dimension size = new Dimension(0, 27);
		textFieldNew.setPreferredSize(size);
		GridBagConstraints gbc_textFieldNew = new GridBagConstraints();
		gbc_textFieldNew.gridwidth = 2;
		gbc_textFieldNew.insets = new Insets(5, 5, 5, 5);
		gbc_textFieldNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNew.gridx = 0;
		textFieldNew.setName(Integer.toString(count));
		gbc_textFieldNew.gridy = count;
		textFieldNew.addKeyListener(this);
		textFieldNew.addFocusListener(this);
		textFieldNew.setFocusTraversalKeysEnabled(false);
		textFieldNew.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		panel.add(textFieldNew, gbc_textFieldNew);
		textFieldNew.requestFocus();
		
		//CREATE NEW LABEL
		WebLabel lblNew = new WebLabel("$0.00");
		GridBagConstraints gbc_lblNew = new GridBagConstraints();
		gbc_lblNew.insets = new Insets(5, 5, 5, 5);
		gbc_lblNew.gridx = 2;			
		gbc_lblNew.gridy = count;
		lblNew.setName(Integer.toString(count) + " label");
		lblNew.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		panel.add(lblNew, gbc_lblNew);
	    createComponentMap();
	    img.setImage(query.img);
	    this.revalidate();
	    this.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void focusLost(FocusEvent e) {
	}
}