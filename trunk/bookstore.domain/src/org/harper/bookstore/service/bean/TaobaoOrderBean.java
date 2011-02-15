package org.harper.bookstore.service.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.harper.bookstore.service.TaobaoOrderStatus;

public class TaobaoOrderBean {

	private String uid;

	private String customerId;

	private BigDecimal totalAmount;

	private BigDecimal transFeeAmount;

	private TaobaoOrderStatus status;

	private String name;

	private String address;

	private String mobile;

	private String phone;

	// Buyer Memo
	private String remark1;

	// Seller Memo
	private String remark2;

	private Date createTime;

	private TaobaoItemBean items[];

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTransFeeAmount() {
		return transFeeAmount;
	}

	public void setTransFeeAmount(BigDecimal transFeeAmount) {
		this.transFeeAmount = transFeeAmount;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public TaobaoOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TaobaoOrderStatus status) {
		this.status = status;
	}

	public TaobaoItemBean[] getItems() {
		return items;
	}

	public void setItems(TaobaoItemBean[] items) {
		this.items = items;
	}

}
