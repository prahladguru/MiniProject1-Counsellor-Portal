package com.sarkar.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarkar.dto.DashBoardResponse;
import com.sarkar.entity.Counsellor;
import com.sarkar.entity.Enquiry;
import com.sarkar.repo.CounsellorRepo;
import com.sarkar.repo.EnquiryRepo;
import com.sarkar.service.CounsellorService;
@Service
public class CounsellorImpl implements CounsellorService {
	
	private CounsellorRepo counsellorRepo;

	
	private EnquiryRepo enqRepo;

	public CounsellorImpl(CounsellorRepo counsellorRepo, EnquiryRepo enqRepo) {
		super();
		this.counsellorRepo = counsellorRepo;
		this.enqRepo = enqRepo;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		return counsellorRepo.findByEmailAndPwd(email, pwd);
		 
	}

	@Override
	public boolean register(Counsellor counsellor) {

		Counsellor savedCounsellor = counsellorRepo.save(counsellor);
		if (null != savedCounsellor.getCounsellorId()) {
			return true;
		}
		return false;
	}

	@Override
	public DashBoardResponse getDashBoardInfo(Integer CounsellorId) {
		DashBoardResponse response = new DashBoardResponse();

		List<Enquiry> enqList = enqRepo.getEnquiryByCounsellorId(CounsellorId);
		int totalEnq = enqList.size();
		int enrolledEnq = enqList.stream().filter(e -> e.getEnqStatus()
				.equals("Enrolled"))
		        .collect(Collectors.toList())
		        .size();
		
		int lostEnq = enqList.stream().filter(e -> e.getEnqStatus().
				equals("Lost"))
		        .collect(Collectors.toList())
		        .size();
		
		int openEnq = enqList.stream().filter(e -> e.getEnqStatus().
				equals("Open"))
		        .collect(Collectors.toList())
		        .size();
		
		response.setTotalEnqs(totalEnq);
	    response.setEnrolledEnqs(enrolledEnq);
	    response.setLostEnqs(lostEnq);
	    response.setOpenEnqs(openEnq);
		
		return response;
	}

	@Override
	public Counsellor findByEmail(String email) {
		return counsellorRepo.findByEmail(email);
		
		
	}

}
