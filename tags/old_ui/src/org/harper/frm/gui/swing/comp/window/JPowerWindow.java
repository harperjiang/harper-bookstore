package org.harper.frm.gui.swing.comp.window;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

public class JPowerWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4108750570766579528L;

	private JMenuBar menuBar;

	private JToolBar toolBar;

	private JSplitPane splitPane;

	private JDesktopPane editorPane;

	private JTabbedPane viewPane;

	public JPowerWindow() {
		super();

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		createMenuBar(menuBar);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		createToolBar(toolBar);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);

		viewPane = new JTabbedPane();
		splitPane.setLeftComponent(viewPane);

		editorPane = new JDesktopPane();
		splitPane.setRightComponent(editorPane);

		splitPane.setDividerLocation(300);
	}

	protected void createMenuBar(JMenuBar menuBar) {

	}

	protected void createToolBar(JToolBar toolBar) {

	}

	public void addView(String name, JPanel newView) {
		viewPane.add(name, newView);
		viewPane.setTabComponentAt(viewPane.getTabCount() - 1, new TabHeader(
				newView));
	}

	public void addEditor(JInternalFrame editor) {

	}

	public static void main(String[] args) {
		new JPowerWindow().setVisible(true);
	}

	private class TabHeader extends JPanel implements ChangeListener {

		private static final long serialVersionUID = 1194482555316730247L;

		private JComponent component;

		private JButton button;

		protected int getIndex() {
			return viewPane.indexOfComponent(component);
		}

		public TabHeader(JComponent comp) {
			this.component = comp;
			setLayout(new FlowLayout());
			setOpaque(false);

			JLabel label = new JLabel() {

				public String getText() {
					return viewPane.getTitleAt(getIndex());
				}
			};
			add(label);
			button = new JButton() {
				{
					int size = 17;
					setPreferredSize(new Dimension(size, size));
					setToolTipText("close this tab");
					// Make the button looks the same for all Laf's
					setUI(new BasicButtonUI());
					// Make it transparent
					setContentAreaFilled(false);
					// No need to be focusable
					setFocusable(false);
					setBorder(BorderFactory.createEtchedBorder());
					setBorderPainted(false);
					// Making nice rollover effect
					// we use the same listener for all buttons
					// addMouseListener(buttonMouseListener);
					setRolloverEnabled(true);
					// Close the proper tab by clicking the button
					addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							viewPane.remove(getIndex());
						}
					});
				}

				// paint the cross
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g.create();
					// shift the image for pressed buttons
					if (getModel().isPressed()) {
						g2.translate(1, 1);
					}
					g2.setStroke(new BasicStroke(2));
					g2.setColor(Color.BLACK);
					if (getModel().isRollover()) {
						g2.setColor(Color.MAGENTA);
					}
					int delta = 6;
					g2.drawLine(delta, delta, getWidth() - delta - 1,
							getHeight() - delta - 1);
					g2.drawLine(getWidth() - delta - 1, delta, delta,
							getHeight() - delta - 1);
					g2.dispose();
				}
			};
			button.setVisible(getIndex() == viewPane.getSelectedIndex());
			add(button);
			viewPane.addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			button.setVisible(getIndex() == viewPane.getSelectedIndex());
		}
	}
}
