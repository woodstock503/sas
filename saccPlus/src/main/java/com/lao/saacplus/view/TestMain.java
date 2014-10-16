package com.lao.saacplus.view;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		for(int i=1;i<10;i++){
			List<BigDecimal> list = new ArrayList<BigDecimal>();
			list.add(new BigDecimal(i));
			
			BigDecimal amountDebitLo = new BigDecimal("0");
			BigDecimal amountCreditLo = new BigDecimal("0");
			
			for(BigDecimal b:list){
				amountDebitLo = amountDebitLo.add(b);
				amountCreditLo = amountCreditLo.add(b);
			}
			
			System.out.println(amountDebitLo);
			System.out.println(amountCreditLo);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//		int periodNo = 12;
//		int addNo = 12/periodNo;
//		int periodCount = 0;
////		Date billDate = new Date();
//		Calendar billDate = Calendar.getInstance();
//		billDate.set(Calendar.DAY_OF_MONTH, 1);
//		
//		for(int i = 0;i<periodNo*addNo;i+=addNo){
//			periodCount++;
//			Calendar startCalendar = Calendar.getInstance();
//			startCalendar.set(Calendar.MONTH, 0);
//			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
//			startCalendar.add(Calendar.MONTH, i);
//			
//			Calendar endCalendar = Calendar.getInstance();
//			endCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
//			endCalendar.add(Calendar.MONTH, addNo-1);
//			endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//			
//			if(startCalendar.compareTo(billDate)<=0&&endCalendar.compareTo(billDate)>=0){
//				System.out.println(i+"-"+"period "+periodCount+" : "+sdf.format(startCalendar.getTime())+"-"+sdf.format(endCalendar.getTime()));
//			}
//			
//		}





		//		BeanUtil util = new BeanUtil();
		//		try {
		//			String param = util.encodeMD5("1234");
		////			PackageType packageType = PackageType.findByKey("5fb1f955b45e38e31789286a1790398d");
		//			System.out.println("code:"+param);
		//		} catch (IllegalArgumentException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (NoSuchAlgorithmException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} 
	}

}
