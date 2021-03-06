package model;

import java.math.BigDecimal;
import java.util.List;

public class DailyData {
	private String date;
	private String adjClose;
	private BigDecimal volatIndex;
	private BigDecimal maValue;

	public String getAdjClose() {
		return adjClose;
	}
	public void setAdjClose(String adjClose) {
		this.adjClose = adjClose;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getVolatile() {
		return volatIndex;
	}
	public void setVolatile(BigDecimal maValue) {
		this.volatIndex = maValue;
	}
	public void setAverVolatile(BigDecimal maValue) {
		this.maValue = maValue;
	}
	public BigDecimal getAverVolatile() {
		return maValue;
	}
}
