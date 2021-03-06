package model;

import java.util.ArrayList;
import java.util.List;

public class SpreadObject {

	private Stock firstStock;
	private Stock secondStock;
	List<SpreadData> data = new ArrayList<SpreadData>();

	public void setBaseStock(Stock firstStock) {
		this.firstStock = firstStock;
	}

	public void setSecondStock(Stock secondStock) {
		this.secondStock = secondStock;
	}

	public void addData(SpreadData sData) {
		data.add(sData);
	}

	public Stock getBaseStock() {
		return firstStock;
	}
	
	public Stock getSecondStock() {
		return secondStock;
	}
	
	public List<SpreadData> getData() {
		return data;
		
	}

}
