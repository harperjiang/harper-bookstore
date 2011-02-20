package org.harper.bookstore.ui.order;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;

import org.apache.commons.lang.Validate;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.PurchaseOrder.DeliveryStatus;
import org.harper.bookstore.domain.order.PurchaseOrder.Status;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.setting.express.ExpressOrderSettingBean;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.OrderService;
import org.harper.bookstore.service.PrintService;
import org.harper.bookstore.service.StoreSiteService;
import org.harper.bookstore.ui.Controller;
import org.harper.bookstore.ui.order.print.POContainer;
import org.harper.bookstore.ui.print.ExpressOrderLayout;
import org.harper.bookstore.ui.setting.ContactInfoBean;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.ComponentBinding;
import org.harper.frm.gui.swing.manager.IBinding;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JLabelBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;
import org.harper.frm.gui.swing.print.ComponentPrintable;

public class POController extends Controller {

	private POFrame orderFrame;

	private PurchaseOrder order;

	private BindingManager manager;

	public POController(PurchaseOrder order) {
		super();
		this.orderFrame = new POFrame(this);

		this.order = order;
		initManager();
		initLink();
		initOthers();
	}

	private void initOthers() {
		if (order.getOid() != 0)
			getOrderFrame().getHeaderPanel().getCustomerIdTextField()
					.setEditable(false);
	}

	public POController() {
		this(new PurchaseOrder());
	}

	public POFrame getOrderFrame() {
		return orderFrame;
	}

	public void setOrderFrame(POFrame orderFrame) {
		this.orderFrame = orderFrame;
	}

	public PurchaseOrder getOrder() {
		return order;
	}

	public void setOrder(PurchaseOrder order) {
		this.order = order;
		manager.setBean(order);
		if (order.getOid() != 0)
			getOrderFrame().getHeaderPanel().getCustomerIdTextField()
					.setEditable(false);
	}

	protected void initLink() {
		manager.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(IBinding.BINDING_VAL)) {
					String attr = ((ComponentBinding) evt.getSource())
							.getAttribute();
					if ("feeAmount".equals(attr) || "items".equals(attr))
						manager.propertyChange(new PropertyChangeEvent(order,
								"total", null, order.getTotal()));
				}
			}
		});
	}

	protected void initManager() {
		this.manager = new BindingManager(order);
		manager.addBinding(new JComboBinding(getOrderFrame().getHeaderPanel()
				.getSystemCombo(), "customer.source"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getCustomerIdTextField(), "customer.id"));
		manager.addBinding(getOrderFrame().getHeaderPanel()
				.getCreateDateTextField().new DateTextBinding("createDate"));
		manager.addBinding(new TableBinding(getOrderFrame().getItemController()
				.getView().getItemTable(), "items") {
			@Override
			protected void itemChangeCallback(List<Object> vals,
					TableModelEvent e) {
				JTable table = (JTable) getComponent();
				CommonTableModel ctm = (CommonTableModel) table.getModel();

				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					OrderItem item = (OrderItem) vals.get(i);
					ctm.setCellEditable(i, 5, item.isAgent());
					if (!item.isAgent()) {
						item.setUnitCost(null);
						ctm.setValueAt(null, i, 5);
					}
				}
			}
		});
		manager.addBinding(new TableBinding(getOrderFrame().getDispItemTable(),
				"dispItems"));
		manager.addBinding(new JLabelBinding(getOrderFrame()
				.getSubtotalAmountLabel(), "total"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getNumberTextField(), "number"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getStatusTextField(), "orderStatus"));
		manager.addBinding(new JTextBinding(getOrderFrame().getRemarkArea(),
				"remark"));
		manager.addBinding(new JTextBinding(getOrderFrame().getMemoArea(),
				"memo"));
		manager.addBinding(getOrderFrame().getFeeAmountField().new NumTextBinding(
				"feeAmount"));
		manager.addBinding(new JComboBinding(getOrderFrame().getHeaderPanel()
				.getSiteCombo(), "site"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getRefnoTextField(), "refno"));
		manager.addBinding(new JTextBinding(getOrderFrame().getHeaderPanel()
				.getRefStatusTextField(), "refStatus"));

		manager.addBinding(new JComboBinding(getOrderFrame().getDeliveryPanel()
				.getCompanyCombo(), "delivery.company"));
		manager.addBinding(new JTextBinding(getOrderFrame().getDeliveryPanel()
				.getOrderNumberField(), "delivery.number"));
		manager.addBinding(new JTextBinding(getOrderFrame().getDeliveryPanel()
				.getNameField(), "contact.name"));
		manager.addBinding(new JTextBinding(getOrderFrame().getDeliveryPanel()
				.getAddressArea(), "contact.address"));
		manager.addBinding(new JTextBinding(getOrderFrame().getDeliveryPanel()
				.getEmailField(), "contact.email"));
		manager.addBinding(new JTextBinding(getOrderFrame().getDeliveryPanel()
				.getPhoneField(), "contact.phone"));
		manager.addBinding(new JTextBinding(getOrderFrame().getDeliveryPanel()
				.getMobileField(), "contact.mobile"));

		manager.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				return;
			}
		});
		manager.loadAll();

	}

	public List<StoreSite> getAvailableSite() {
		return new StoreSiteService().getAvailableSite(true);
	}

	public void save() {
		Validate.notEmpty(order.getCustomer().getId(),
				"Customer Id should not be empty");
		if (null == order.getSite())
			order.setSite((StoreSite) getOrderFrame().getHeaderPanel()
					.getSiteCombo().getSelectedItem());
		PurchaseOrder newOrder = new OrderService().savePurchaseOrder(order);
		setOrder(newOrder);
	}

	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new POContainer(order));
		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	public void confirm() {
		Validate.isTrue(order.getOrderStatus() == Status.DRAFT,
				"Order cannot be confirmed");
		setOrder(new OrderService().operateOrder(order, Status.CONFIRM));
	}

	public BigDecimal getListPrice(Book book) {
		return new OrderService().getListPrice(book);
	}

	public void cancel() {
		Validate.isTrue(
				order.getOrderStatus() == Status.DRAFT
						|| order.getOrderStatus() == Status.CONFIRM,
				"Order cannot be cancelled");
		Validate.isTrue(
				order.getDeliveryStatus() == DeliveryStatus.NOT_SENT.ordinal(),
				"Sent order cannot be cancelled");
		setOrder(new OrderService().operateOrder(order, Status.CANCEL));
	}

	public void printExpress() {
		// Get Config Bean According to Company
		ExpressOrderSettingBean printbean = new PrintService()
				.getPrintSetting(getOrder().getDelivery().getCompany());
		ExpressOrderLayout layout = new ExpressOrderLayout(printbean);

		// From Info;
		ContactInfoBean ciBean = new ContactInfoBean();
		ciBean.load();

		ContactInfo fromInfo = new ContactInfo();
		fromInfo.setAddress(ciBean.getAddress());
		fromInfo.setMobile(ciBean.getMobile());
		fromInfo.setName(ciBean.getName());
		layout.setFromInfo(fromInfo);

		// To Info
		ContactInfo toInfo = order.getContact();
		layout.setToInfo(toInfo);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(layout));
		if (job.printDialog())
			try {
				job.print();
			} catch (PrinterException e) {
				throw new RuntimeException(e);
			}
	}

	public static void main(String[] args) {
		new POController();
	}

	public void send() {
		Validate.isTrue(
				PurchaseOrder.Status.CONFIRM.ordinal() == order.getStatus(),
				"Order must first be approved");
		Validate.isTrue(
				PurchaseOrder.DeliveryStatus.NOT_SENT.ordinal() == order
						.getDeliveryStatus(), "Order must be in unsent status");
		setOrder(new OrderService().sendOrder(order));
	}

	public void partialSend() {
		Validate.isTrue(
				PurchaseOrder.Status.CONFIRM.ordinal() == order.getStatus(),
				"Order must first be approved");
		Validate.isTrue(
				PurchaseOrder.DeliveryStatus.NOT_SENT.ordinal() == order
						.getDeliveryStatus(), "Order must be in unsent status");
		setOrder(new OrderService().sendOrder(order, true));
	}
}
