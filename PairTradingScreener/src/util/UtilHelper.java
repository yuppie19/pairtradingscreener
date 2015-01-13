package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.DailyData;
import model.Stock;

public class UtilHelper {

	/**
	 * �������� ������� �� 21 ����
	 **/
	public static BigDecimal getMaValue(List<BigDecimal> ma20) {
		BigDecimal prevVal = null;
		for(int i = 0; i < ma20.size(); i++) {
//			if(val != null) {
//				val = ma20.get(i).add(val);
//				continue;
//			}
//			val = ma20.get(i);
			
			if(prevVal == null) {
				prevVal = ma20.get(i);
				continue;
			}
				
			prevVal = ma20.get(i).add(prevVal);
			
		}
		prevVal = prevVal.divide(new BigDecimal(21), 7, RoundingMode.CEILING);
		return prevVal;
	}
	/***
	 * ������ ����� ������� ���������� ��� ������� ����������� ��� �� ����� 
	 * @param item 
	 */
	public static void loadMath(List<DailyData> data, Stock item) {
		double prevPrice = 0;
		List<BigDecimal> ma20 = new ArrayList<BigDecimal>(21); 
		boolean firstTime = true;
		BigDecimal maValue;
		BigDecimal prevMaValue = null;
		
		for(int i = 0; i < data.size(); i++) {
			DailyData dd = data.get(i);
			//��������� ���������� ���� �� ��������� � ��������� ����������� ���
			if(prevPrice == 0) {
				prevPrice = Double.valueOf(dd.getAdjClose());
				continue;
			}
			
			Double currPrice = Double.valueOf(dd.getAdjClose());
			double dailyChange = currPrice/prevPrice;
			BigDecimal dailyDeltaLog = new BigDecimal(Math.log(dailyChange));
			BigDecimal dailyDeltaLogAbs = dailyDeltaLog.abs();
			
			prevPrice = currPrice;
			
			//��������� � ���� ������ ��� ���������� �������� �������� �� 21 ����
			ma20.add(dailyDeltaLogAbs);
			if(ma20.size() == 21) {
				maValue = UtilHelper.getMaValue(ma20);
				ma20.remove(0);
				
				if(firstTime) {
					prevMaValue = maValue;
					firstTime = false;
					continue;
				}
				
				BigDecimal normXln;
				try {
					normXln = dailyDeltaLog.divide(prevMaValue, 7, RoundingMode.CEILING);
					dd.setVolatile(normXln);
					prevMaValue = maValue;
					
				} catch (ArithmeticException e) {
					System.out.println(item.getTicker());
				} 
				
				
			}
			
		}
	}
	
	/**
	 *������������ ���� � �������� ������ ����
	 * @throws ParseException 
	 */
	public static void deleteOldData(Stock stock, Date earliestDate) throws ParseException {
		Iterator<DailyData> it = stock.getData().iterator();
		while(it.hasNext()) {
			DailyData dd = it.next();
			
			java.util.Date tmp = new SimpleDateFormat("yyyy-MM-dd").parse(dd.getDate());
			int eYear = earliestDate.getYear();
			
			if(tmp.getYear() < eYear) {
				it.remove();
			} else {
				break;
			}
		}
		
		Iterator<DailyData> monthIt = stock.getData().iterator();
		while(monthIt.hasNext()) {
			DailyData dd = monthIt.next();
			
			java.util.Date tmp = new SimpleDateFormat("yyyy-MM-dd").parse(dd.getDate());
			int eMonth = earliestDate.getMonth();
			
			if(tmp.getMonth() < eMonth) {
				monthIt.remove();
			} else {
				break;
			}
		}
		
		Iterator<DailyData> dailyIt = stock.getData().iterator();
		while(dailyIt.hasNext()) {
			DailyData dd = dailyIt.next();
			java.util.Date tmp = new SimpleDateFormat("yyyy-MM-dd").parse(dd.getDate());
			
			if(tmp.before(earliestDate)) {
				dailyIt.remove();
			} else {
				break;
			}
		}
		
		
	}
	
//	/**
//	 *��������� �����
//	 * @return 
//	 */
//	public static SpreadObject spreadCounter(Stock firstStock, Stock secondStock) {
//		BigDecimal prevFirstSpread = null;
//		BigDecimal prevSecondSpread = null;
//		SpreadObject obj = new SpreadObject();
//		obj.setBaseStock(firstStock);
//		obj.setSecondStock(secondStock);
//		
//		//���� �������� ������������������ ����
//		for(int i = 0; i < firstStock.getData().size(); i++) {
//			BigDecimal firstVolat = firstStock.getData().get(i).getVolatile();
//			BigDecimal secondVolat = secondStock.getData().get(i).getVolatile();
//			
//			if(firstVolat != null && secondVolat != null) {
//				//��������� ����
//				String secondStDate = secondStock.getData().get(i).getDate();
//				String firstStDate = firstStock.getData().get(i).getDate();		
//				if(!firstStDate.equalsIgnoreCase(secondStDate))
//					break;
//						
//				if(prevFirstSpread == null) {
//					prevFirstSpread = firstVolat;
//					prevSecondSpread = secondVolat;
//				} else {
//					prevFirstSpread.add(firstVolat);
//					prevSecondSpread.add(secondVolat);
//					BigDecimal spread = prevFirstSpread.subtract(prevSecondSpread);
//					obj.addSpread(firstStDate, spread);
//				}
//			}
//			
////				String secondStDate = secondStock.getData().get(i).getDate();
////				String firstStDate = firstStock.getData().get(i).getDate();			
////				if(firstStDate.equalsIgnoreCase(secondStDate)) {
////					if(prevSpread == null) {
////						prevSpread = firstVolat.subtract(secondVolat);
////					} else {
////						BigDecimal currSpread = prevSpread.add(firstVolat);
////						obj.addSpread(firstStDate, currSpread);
////					}
//		}	
//		return obj;
//	}
}