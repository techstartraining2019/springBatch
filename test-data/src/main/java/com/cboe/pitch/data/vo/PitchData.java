package com.cboe.pitch.data.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PitchData {
	
	private Date timestamp ;
	private String messageType;
	private String orderId;
	private String sideIndicator;
	private String shares;	
	private String StockSymbol;
	private BigDecimal price;
	private String display;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSideIndicator() {
		return sideIndicator;
	}
	public void setSideIndicator(String sideIndicator) {
		this.sideIndicator = sideIndicator;
	}
	public String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getStockSymbol() {
		return StockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		StockSymbol = stockSymbol;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	
	

}
