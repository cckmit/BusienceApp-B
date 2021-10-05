package com.busience.standard.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class customerManageController {

	@Autowired
	DataSource dataSource;
	
	public int solution(int[] nums) {
        int answer = 0;
        
        int[] answers = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < answers.length; j++) {
				if(answers[j] != nums[i])
					answers[j] = nums[i];
			}
		}
        
        answer = answers.length;
        
        return answer;
    }
	
}
