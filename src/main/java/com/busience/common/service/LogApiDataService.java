package com.busience.common.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dao.LogApiDataDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.LogApiDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LogApiDataService {
	
	@Autowired
	LogApiDataDao logApiDataDao;
	
	@Autowired
	DtlDao dtlDao;
	
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
			        String crtfcKey = null;
			        
			        List<DtlDto> dtlDtoList = dtlDao.findByCode(31);
			        for (DtlDto dtlDto : dtlDtoList) {
			        	if(dtlDto.getCHILD_TBL_TYPE().equals("crtfcKey")) {
			        		crtfcKey = dtlDto.getCHILD_TBL_RMARK();
			        	}
			        }
			        
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
			        
			        logApiDataDto.setCrtfcKey(crtfcKey);
			        logApiDataDto.setLogDt(MilliSec);
			        logApiDataDto.setUseSe(UseSe);
			        logApiDataDto.setSysUser(sysUser);
			        logApiDataDto.setConectIp(ia);
			        logApiDataDto.setDataUsgqty(0);
			        
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
			        		LogApiSend(logApiDataDto);
			        		
			        		//원상복귀
			        		logApiDataDto.setLogDt(MilliSec);
			        		logApiDataDto.setUseSe("접속");
			        	}
			        }
			        
					logApiDataDao.LogDataInsertDao(logApiDataDto);
					LogApiSend(logApiDataDto);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private JSONObject LogApiSend(LogApiDataDto data) {
		if(data.getCrtfcKey().length()==0) {
			return null;
		}
	    JSONObject response = null;
	    BufferedWriter bufferedWriter = null;
	    BufferedReader bufferedReader = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String tempData = objectMapper.writeValueAsString(data);
			
			URL url = new URL("https://log.smart-factory.kr/apisvc/sendLogDataJSON.do?logData="+URLEncoder.encode(tempData,"UTF-8"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Content-Type", "application/json;UTF-8");
	        connection.setDoOutput(true);
	        
	        bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
	        
	        bufferedWriter.write(tempData);
	        bufferedWriter.flush();
	        bufferedWriter.close();

	        bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));            
	        
	        JSONParser parser = new JSONParser();
	        
	        JSONObject json = (JSONObject) parser.parse(bufferedReader.readLine());
				        
	        response = json;
	        
	        bufferedReader.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			try {
				if(bufferedWriter != null) {bufferedWriter.close();}
				if(bufferedReader != null) {bufferedReader.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return response;
	}
}
