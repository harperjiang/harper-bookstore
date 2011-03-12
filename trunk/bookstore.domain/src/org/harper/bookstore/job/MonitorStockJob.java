package org.harper.bookstore.job;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.repo.StoreRepo;
import org.harper.bookstore.repo.TodoRepo;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.job.mediator.AbstractMediatorJob;
import org.springframework.util.CollectionUtils;

public class MonitorStockJob extends AbstractMediatorJob {

	public MonitorStockJob() {
		super(false);
	}

	static final String OUT_OF_STOCK_ALERT = "Book \"{0}\" Out of Stock";

	static final String OUT_OF_STOCK_CONTENT = "Book:{0}\nStore Site:{1}\nLevel:{2}\nThreshold:{3}\nCurrent:{4}";

	static final String KEY_SITE = "stock_alert_{0}_{1}";

	static final String KEY_GLOBAL = "stock_alert_{0}";

	private CommonRepo commonRepo;

	private StoreRepo storeRepo;

	private TodoRepo todoRepo;

	@Override
	protected Object executeInTransaction(JobMonitor monitor) {

		StoreEntry entry = (StoreEntry) getTriggerEvent().getSource();

		List<StockAlert> siteAlert = getStoreRepo().getStockAlerts(
				entry.getSite(), entry.getBook());
		List<StockAlert> globalAlert = getStoreRepo().getStockAlerts(null,
				entry.getBook());

		if (!CollectionUtils.isEmpty(siteAlert)) {
			StockAlert alert = siteAlert.get(0);
			TodoItem item = new TodoItem();
			item.setSubject(MessageFormat.format(OUT_OF_STOCK_ALERT, entry
					.getBook().getName()));
			item.setCreateDate(new Date());
			item.setDueDate(null);
			item.setKey(MessageFormat.format(KEY_SITE,
					entry.getSite().getOid(), entry.getBook().getOid()));

			TodoItem oldItem = getTodoRepo().getTodoItem(item.getKey());
			if (null != oldItem)
				getCommonRepo().remove(oldItem);

			if (alert.getErrorThreshold() > entry.getCount()) {
				item.setContent(MessageFormat.format(OUT_OF_STOCK_CONTENT,
						entry.getBook().getName(), entry.getSite().getName(),
						"Severe", alert.getErrorThreshold(), entry.getCount()));
				item.setPrivilege(Privilege.URGENT);
			} else if (alert.getWarnThreshold() > entry.getCount()) {
				item.setContent(MessageFormat.format(OUT_OF_STOCK_CONTENT,
						entry.getBook().getName(), entry.getSite().getName(),
						"Alert", alert.getWarnThreshold(), entry.getCount()));
				item.setPrivilege(Privilege.HIGH);
			} else {
				item = null;
			}
			if (null != item)
				getCommonRepo().store(item);
		}

		if (!CollectionUtils.isEmpty(globalAlert)) {
			StockAlert alert = globalAlert.get(0);
			TodoItem item = new TodoItem();
			item.setSubject(MessageFormat.format(OUT_OF_STOCK_ALERT, entry
					.getBook().getName()));
			item.setCreateDate(new Date());
			item.setDueDate(null);
			item.setKey(MessageFormat.format(KEY_GLOBAL, entry.getBook()
					.getOid()));

			TodoItem oldItem = getTodoRepo().getTodoItem(item.getKey());
			if (null != oldItem)
				getCommonRepo().remove(oldItem);

			List<StoreSite> sites = getStoreRepo().getStoreSiteWithBook(
					entry.getBook());
			int totalCount = 0;
			for (StoreSite site : sites)
				totalCount += site.getEntry(entry.getBook()).getCount();

			if (alert.getErrorThreshold() > totalCount) {
				item.setContent(MessageFormat.format(OUT_OF_STOCK_CONTENT,
						entry.getBook().getName(), "---", "Severe",
						alert.getErrorThreshold(), totalCount));
				item.setPrivilege(Privilege.URGENT);
			} else if (alert.getWarnThreshold() > totalCount) {
				item.setContent(MessageFormat.format(OUT_OF_STOCK_CONTENT,
						entry.getBook().getName(), "---", "Alert",
						alert.getWarnThreshold(), totalCount));
				item.setPrivilege(Privilege.HIGH);
			} else {
				item = null;
			}
			if (null != item)
				getCommonRepo().store(item);
		}

		return null;
	}

	public CommonRepo getCommonRepo() {
		if (null == commonRepo)
			commonRepo = RepoFactory.INSTANCE.getCommonRepo();
		return commonRepo;
	}

	public void setCommonRepo(CommonRepo commonRepo) {
		this.commonRepo = commonRepo;
	}

	public StoreRepo getStoreRepo() {
		if (null == storeRepo)
			storeRepo = RepoFactory.INSTANCE.getStoreRepo();
		return storeRepo;
	}

	public void setStoreRepo(StoreRepo storeRepo) {
		this.storeRepo = storeRepo;
	}

	public TodoRepo getTodoRepo() {
		if (null == todoRepo)
			todoRepo = RepoFactory.INSTANCE.getTodoRepo();
		return todoRepo;
	}

	public void setTodoRepo(TodoRepo todoRepo) {
		this.todoRepo = todoRepo;
	}

}
