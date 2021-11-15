package com.busience.common.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.LogApiDataDao;
import com.busience.common.dto.LogApiDataDto;

@Service
public class LogApiDataService {
	
	@Autowired
	LogApiDataDao logApiDataDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 로그 저장
	public int LogDataInsert(String UseSe, String sysUser) {
        
        try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {

			        LogApiDataDto logApiDataDto = new LogApiDataDto();        
			        
			        //API고유 인증키
			        String crfcKey = null;
			        
			        //시간
			        Date date = new Date();
			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(date);
			        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
			        String MilliSec = sdformat.format(date);
			        
			        //아이피
			        String ia = null;
			        
			        try {
						ia = InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
			        
			        logApiDataDto.setCrfcKey(crfcKey);
			        logApiDataDto.setLogDt(MilliSec);
			        logApiDataDto.setUseSe(UseSe);
			        logApiDataDto.setSysUser(sysUser);
			        logApiDataDto.setConectIp(ia);
			        logApiDataDto.setDataUsgqty(null);
			        
			        //접속했을때 최근 기록이 종료가 아닌경우
			        if(UseSe.equals("접속")) {
			        	List<LogApiDataDto> searchData = logApiDataDao.LogDataSelectDao(sysUser);
			        	if(searchData.size() >0 && searchData.get(0).getUseSe().equals("접속")) {
			        		//1초전에 종료 생성해줌
			        		calendar.add(Calendar.SECOND, -1);
			        		String oneSecAgo = sdformat.format(calendar.getTime());
			        		logApiDataDto.setLogDt(oneSecAgo);
			        		logApiDataDto.setUseSe("종료");
			        		logApiDataDao.LogDataInsertDao(logApiDataDto);
			        		
			        		//원상복귀
			        		logApiDataDto.setLogDt(MilliSec);
			        		logApiDataDto.setUseSe("접속");
			        	}
			        }
			        
					logApiDataDao.LogDataInsertDao(logApiDataDto);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
