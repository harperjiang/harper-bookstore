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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
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

import org.apache.commons.lang.Validate;
import org.harper.bookstore.ui.Controller;

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
		editorPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		splitPane.setRightComponent(editorPane);

		splitPane.setDividerLocation(300);

	}

	protected void createMenuBar(JMenuBar menuBar) {

	}

	protected void createToolBar(JToolBar toolBar) {

	}

	public void addView(JPowerWindowView newView) {
		// For views already opened, do not open again
		for (int i = 0; i < viewPane.getTabCount(); i++) {
			if (viewPane.getComponentAt(i).getClass()
					.equals(newView.getClass())) {
				viewPane.setSelectedIndex(i);
				return;
			}
		}
		viewPane.add(newView.getName(), newView);
		viewPane.setTabComponentAt(viewPane.getTabCount() - 1, new TabHeader(
				newView));
	}

	public void addEditor(JPowerWindowEditor editor) {
		editor.setManagerWindow(this);
		if (null != editorPane.getSelectedFrame()) {
			JInternalFrame currentTop = editorPane.getSelectedFrame();
			editor.setLocation(currentTop.getLocation().x + 30,
					currentTop.getLocation().y + 30);
		}
		editorPane.add(editor);
		editor.show();
	}

	public void addEditor(JComponent editor) {
		Validate.isTrue(editor instanceof JPowerWindowEditor);
		addEditor((JPowerWindowEditor) editor);
	}

	public static void main(String[] args) {
		new JPowerWindow().setVisible(true);
	}

	protected class NewEditorButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8729179498517353467L;

		public NewEditorButton(String tooltip, Icon icon, Class<?> clazz) {
			super();
			setHideActionText(true);
			setAction(new NewEditorAction(tooltip, clazz));
			setToolTipText(tooltip);
			setIcon(icon);
		}
	}

	protected class NewEditorAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8659271494811501097L;

		private Class<?> clazz;

		public NewEditorAction(String name, Class<?> clazz) {
			super(name);
			this.clazz = clazz;
		}

		public NewEditorAction(String name, Class<?> clazz, Icon image) {
			super(name, image);
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Object obj = clazz.newInstance();
				if (obj instanceof Controller)
					addEditor((JPowerWindowEditor) ((Controller) obj)
							.getComponent());
				if (obj instanceof JPowerWindowEditor)
					addEditor((JPowerWindowEditor) obj);
			} catch (Exception e1) {
				if (e1 instanceof RuntimeException)
					throw (RuntimeException) e1;
				throw new RuntimeException(e1);
			}
		}
	}

	protected class NewViewAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2534593865357040629L;

		private Class<? extends JPowerWindowView> clazz;

		public NewViewAction(String name,
				Class<? extends JPowerWindowView> clazz) {
			super(name);
			this.clazz = clazz;
		}

		public NewViewAction(String name,
				Class<? extends JPowerWindowView> clazz, Icon icon) {
			super(name, icon);
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				addView(clazz.newInstance());
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}

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
				private static final long serialVersionUID = -6365548926863190343L;

				public String getText() {
					return viewPane.getTitleAt(getIndex());
				}
			};
			add(label);
			button = new JButton() {
				private static final long serialVersionUID = 4975628531646118395L;

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
