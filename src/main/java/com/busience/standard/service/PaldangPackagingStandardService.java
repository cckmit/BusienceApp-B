package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.standard.dao.paldangPackagingStandardDao;
import com.busience.standard.dto.PaldangPackagingStandardDto;

@Service
public class PaldangPackagingStandardService {
	
	@Autowired
	paldangPackagingStandardDao paldangPackagingDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;

	public List<PaldangPackagingStandardDto> paldangPackagingListDao() {
		return paldangPackagingDao.paldangPackagingListDao();
	}
	
	public List<PaldangPackagingStandardDto> paldangPackagingCheckNo(PaldangPackagingStandardDto paldangPackagingStandardDto) {
		return paldangPackagingDao.paldangPackagingCheckNo(paldangPackagingStandardDto);
	}
	
	public int paldangPackaingInsertDao(List<PaldangPackagingStandardDto> dataList, PaldangPackagingStandardDto paldangPackagingStandardDto) {
		
		try {

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub

					for (int i = 0; i < dataList.size(); i++) {
						// System.out.println(dataList.get(i));
						paldangPackagingDao.paldangPackaingInsertDao(dataList.get(i));
					}
				}
			});

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public int paldangPackaingDeleteDao(List<PaldangPackagingStandardDto> dataList, PaldangPackagingStandardDto paldangPackagingStandardDto) {
		
		try {

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// TODO Auto-generated method stub

					for (int i = 0; i < dataList.size(); i++) {
						// System.out.println(dataList.get(i));
						paldangPackagingDao.paldangPackaingDeleteDao(dataList.get(i));
					}
				}
			});

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
