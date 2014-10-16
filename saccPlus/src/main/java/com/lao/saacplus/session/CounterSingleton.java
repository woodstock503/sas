package com.lao.saacplus.session;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;


@Lock(LockType.READ)
@Singleton
public class CounterSingleton {  
	
	@EJB
	private TransactionMasterFacade transactionMasterFacade;
	
	private static String certifyFormat = "%04d";
	private AtomicInteger atomicInteger;  

	@PostConstruct
	public void initCounter(){  
		atomicInteger = new AtomicInteger(transactionMasterFacade.maxCertifyTransactionMasterOnDay()+1);
	}  
	
	@Schedule(second="0", minute="0", hour="0", month="*", dayOfWeek="*", year="*")
	public void resetDay(){
		System.out.println("===== reset number =====");
		this.getCounter();
		this.atomicInteger = new AtomicInteger(1); 
	}

	public String getCounter() {  
		return String.format(certifyFormat, atomicInteger.getAndIncrement());  
	}     
}
