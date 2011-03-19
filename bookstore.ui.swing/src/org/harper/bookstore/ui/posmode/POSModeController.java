package org.harper.bookstore.ui.posmode;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.ui.Controller;
import org.harper.bookstore.ui.posmode.print.POSPrintable;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;
import org.harper.frm.gui.swing.print.ComponentPrintable;

public class POSModeController extends Controller {

	private POSModeBean bean;

	private POSModeFrame frame;

	public POSModeController() {
		super();

		frame = new POSModeFrame();
		frame.setController(this);

		bean = new POSModeBean();

		initManager();
	}

	private void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(new JTextBinding(frame.getTradeIdField(), "tradeId"));
		manager.addBinding(new TableBinding(frame.getBookTable(), "items"));
		manager.loadAll();
	}

	public void print() {
		POSPrintable pp = new POSPrintable();
		pp.setSource(bean);
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(pp));
		if (job.printDialog())
			try {
				job.print();
				beanPrinted = true;
			} catch (Exception e) {
				LogManager.getInstance().getLogger(getClass())
						.error("Error while printing", e);
				JOptionPane.showMessageDialog(frame,
						"Failed to print. Check log for detail.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
	}

	private boolean beanPrinted;

	public void save() {
		// Save Bean
		if (StringUtils.isEmpty(bean.getTradeId())) {
			JOptionPane.showMessageDialog(frame, "未输入单号");
			return;
		}
		if (!beanPrinted
				&& JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
						frame, "尚未打印，是否打印?"))
			print();

		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				File dir = new File(
						new SimpleDateFormat("yyyyMMdd").format(new Date()));
				if (!(dir.exists() && dir.isDirectory())) {
					dir.mkdir();
				}

				FileOutputStream fos = new FileOutputStream(
						dir.getAbsolutePath() + File.separator
								+ bean.getTradeId());
				PrintWriter pw = new PrintWriter(fos);

				for (POSBookItem item : bean.getItems()) {
					pw.println(MessageFormat.format("{0},{1}", item.getBook()
							.getIsbn(), item.getCount()));
				}

				pw.close();
				fos.close();

				return null;
			}

			@Override
			protected void done() {
				try {
					get();
					JOptionPane.showMessageDialog(frame, "保存成功");
				} catch (Exception e) {
					LogManager.getInstance().getLogger(POSModeController.class)
							.error("Error while save bean", e);
					JOptionPane.showMessageDialog(frame, "保存失败,详细原因请查看日志",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();

		// Refresh Bean
		manager.setBean(new POSModeBean());
		manager.loadAll();
	}

	public POSModeBean getBean() {
		return bean;
	}

	public POSModeFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		new POSModeController().getFrame().setVisible(true);
	}
}
