package com.watring.derek.mtgcardpricing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.extended.image.WebImage;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class GUI extends WebFrame implements FocusListener, KeyListener{
	private static final long serialVersionUID = 1L;
	
	//Class Components
	private JPanel contentPane;
	private static JTextField textField;
	static WebImage img;
	static WebLabel lbl;
	static WebScrollPane scrollPane;
	static WebPanel panel;
	static WebSpinner multiplier;
	static WebLabel total;
	
	static int count = 0; //Number of textfields displayed
	@SuppressWarnings("rawtypes")
	private HashMap componentMap;

	/**
	 * Create the frame and class components
	 */
	public GUI() {
		setResizable(false);
		/*
		MenuBar mb = new MenuBar();
		this.setMenuBar(mb);
		Menu m = new Menu();
		mb.add(m);
		*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		multiplier = new WebSpinner();
		double min = 0.00;
        double value = 0.75;
        double max = 1.000;
        double stepSize = 0.05;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, stepSize);
        multiplier.setModel(model);
        multiplier.setBounds(506, 392, 100, 30);
		contentPane.add(multiplier);
		
		ImageIcon i1 = new ImageIcon( "Back.jpg" );
		img = new WebImage ( i1 );
		img.setBounds(425, 11, 269, 370);
		contentPane.add(img);
		
		
		panel = new WebPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		scrollPane = new WebScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 402, 439);
		scrollPane.setViewportView(panel);
		contentPane.add(scrollPane);
		
		textField = new WebTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		textField.setText("1 endbringer");
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		textField.setFocusTraversalKeysEnabled(false);
		textField.setName(Integer.toString(count));
		panel.add(textField, gbc_textField);
		textField.addKeyListener(this);
		textField.addFocusListener(this);
		
		lbl = new WebLabel("");
		GridBagConstraints gbc_lbl = new GridBagConstraints();
		gbc_lbl.insets = new Insets(5, 5, 5, 5);
		lbl.setName(Integer.toString(count) + " label");
		gbc_lbl.gridx = 2;
		gbc_lbl.gridy = count;
		panel.add(lbl, gbc_lbl);
		
		WebLabel warning = new WebLabel("Always begin each line with the amount");
		warning.setForeground(Color.RED);
		warning.setBounds(10, 453, 300, 14);
		contentPane.add(warning);
		
		total = new WebLabel("");
		total.setBounds(312, 453, 81, 14);
		contentPane.add(total);
		createComponentMap();
		total.setText(getTotal());
	}
	@Override
	public void focusLost(FocusEvent e) {
		//CardData.updatePrice(getCurrentWebLabel(), )
    }
	@Override
	public void focusGained(FocusEvent e) {
		CardData.getData(getCurrentTextField().getText().substring(2), getCurrentWebLabel(), this);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 9){
			if(getCurrentTextField().getName().equals(Integer.toString(count))){
		        count++;
		        //CREATE NEW TEXTFIELD
		        WebTextField textFieldNew = new WebTextField();
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
				panel.add(textFieldNew, gbc_textFieldNew);
				textFieldNew.requestFocus();
				
				//CREATE NEW LABEL
				WebLabel lblNew = new WebLabel("");
				GridBagConstraints gbc_lblNew = new GridBagConstraints();
				gbc_lblNew.insets = new Insets(5, 5, 5, 5);
				gbc_lblNew.gridx = 2;			
				lblNew.setName(Integer.toString(count) + " label");
				gbc_lblNew.gridy = count;
				panel.add(lblNew, gbc_lblNew);
			    createComponentMap();
			    this.revalidate();
			}
		}
		if(e.getKeyCode() == 10){
			CardData.getData(getCurrentTextField().getText().substring(2), getCurrentWebLabel(), this);
		}
	}
	
	public int getAmount(){
		return Integer.parseInt(getCurrentTextField().getText().substring(0, 1));
	}
	
	private WebTextField getCurrentTextField() {
		if(this.getFocusOwner().getName() != null)
			return (WebTextField) this.getFocusOwner();
		else return null;
	}
	
	private WebLabel getCurrentWebLabel() {
		String name = this.getFocusOwner().getName();
		if(name != null)
			return (WebLabel) getComponentByName(name + " label");
		else return null;
	}
	
	@SuppressWarnings("unchecked")
	private void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = panel.getComponents();
        for (int i=0; i < components.length; i++) {
                componentMap.put(components[i].getName(), components[i]);
        }
	}	

	public Component getComponentByName(String name) {
	        if (componentMap.containsKey(name)) {
	                return (Component) componentMap.get(name);
	        }
	        else return null;
	}
	
	public String getTotal(){
		double total = 0;
		for(int i=0;i<=count;i++){
			String labelName = i + " label";
			try{
			total +=  Double.parseDouble(((WebLabel) getComponentByName(labelName)).getText().substring(1));
			}catch(StringIndexOutOfBoundsException e){
			}
		}
		return "Total: $"+Double.toString(total);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
