package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.gui.swing.comp.table.ColumnDescBean;
import org.harper.frm.gui.swing.comp.table.data.AbstractTableData;
import org.harper.frm.gui.swing.freechart.CategoryData;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ProductProfitReportBean extends AbstractBean {

	private Book book;
	
	private Date fromDate;
	
	private Date toDate;
	
	private BigDecimal profitRate;
	
	private List<DailyProfitData> datas;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public List<DailyProfitData> getDatas() {
		return datas;
	}

	public void setDatas(List<DailyProfitData> datas) {
		this.datas = datas;
	}

	public static class DailyProfitData extends AbstractTableData implements CategoryData {

		public DailyProfitData() {
			descBeans.add(new ColumnDescBean(Date.class,"Date","date"));
			descBeans.add(new ColumnDescBean(Integer.TYPE,"Count","count"));
		}
		
		public DailyProfitData(Object bean) {
			this();
			setBean(bean);
		}
		
		@Override
		public String getCategory() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getX() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BigDecimal getY() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
