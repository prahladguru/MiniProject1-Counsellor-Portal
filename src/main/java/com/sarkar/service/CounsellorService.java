package com.sarkar.service;

import org.springframework.stereotype.Service;

import com.sarkar.dto.DashBoardResponse;
import com.sarkar.entity.Counsellor;
@Service
public interface CounsellorService {

	public Counsellor login(String email, String pwd);

	public boolean register(Counsellor counsellor);

	public DashBoardResponse getDashBoardInfo(Integer CounsellorId);
	
	public Counsellor findByEmail(String email);
	
	

}
