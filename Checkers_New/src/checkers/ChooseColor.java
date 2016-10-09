package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ChooseColor extends JPanel {
	Graphics2D g2;
	Color color1 = Color.RED; 
	Color color2 = Color.BLUE;
	
	
	public /*Color*/ ChooseColor() {
		String[] colors = {"Red","Orange","Yellow","Green","Blue","Purple","Pink"};
		
		JFrame frame2 = new JFrame("Colors");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(600, 150);
		frame2.setLayout(new FlowLayout());
		frame2.setVisible(true);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		frame2.add(panel2);
		
		JTextField textField = new JTextField(15);
		JComboBox<String> comboBox = new JComboBox<String>(colors);
		JComboBox<String> comboBox2 = new JComboBox<String>(colors);
		
		comboBox.setMaximumSize(comboBox.getPreferredSize());
		comboBox.setVisible(true);
		comboBox2.setMaximumSize(comboBox.getPreferredSize());
		comboBox2.setVisible(true);
		
		JLabel player1Label = new JLabel("Select Color for Player 1");
		JLabel player2Label = new JLabel("Select Color for Player 2");
		JButton button = new JButton("OK");
		
		panel2.add(player1Label);
		panel2.add(comboBox);
		panel2.add(player2Label);
		panel2.add(comboBox2);
		panel2.add(button);
		frame2.setVisible(true);
		
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 0) 
					color1 = Color.RED;
				if(comboBox.getSelectedIndex() == 1) 
					color1 = Color.ORANGE;
				if(comboBox.getSelectedIndex() == 2) 
					color1 = Color.YELLOW;
				if(comboBox.getSelectedIndex() == 3)
					color1 = Color.GREEN;
				if(comboBox.getSelectedIndex() == 4)
					color1 = Color.BLUE;
				if(comboBox.getSelectedIndex() == 5)
					color1 = Color.MAGENTA;
				if(comboBox.getSelectedIndex() == 6)
					color1 = Color.PINK;
				
				if(comboBox2.getSelectedIndex() == 0) 
					color2 = Color.RED;
				if(comboBox2.getSelectedIndex() == 1) 
					color2 = Color.ORANGE;
				if(comboBox2.getSelectedIndex() == 2) 
					color2 = Color.YELLOW;
				if(comboBox2.getSelectedIndex() == 3)
					color2 = Color.GREEN;
				if(comboBox2.getSelectedIndex() == 4)
					color2 = Color.BLUE;
				if(comboBox2.getSelectedIndex() == 5)
					color2 = Color.MAGENTA;
				if(comboBox2.getSelectedIndex() == 6)
					color2 = Color.PINK;
				
				//new GUI();
				GUI.change(color1,color2);
			}
		});		
		//return color1;
	}
	
}
