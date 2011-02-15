package org.harper.bookstore.ui.common;

import java.awt.GridLayout;

import javax.swing.JFrame;

import org.junit.Test;


public class DirectoryChooseFieldTest {

	@Test
	public void testCreateField() {
		JFrame frame = new JFrame() {
			{
				setSize(400,300);
				setLayout(new GridLayout(1,1));
				add(new DirectoryChooseField());
				setVisible(true);
			}
		};
		while(true) {
			
		}
	}
}
