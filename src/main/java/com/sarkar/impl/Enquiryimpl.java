package com.sarkar.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.sarkar.dto.ViewEnquiryFilterRequest;
import com.sarkar.entity.Counsellor;
import com.sarkar.entity.Enquiry;
import com.sarkar.repo.CounsellorRepo;
import com.sarkar.repo.EnquiryRepo;
import com.sarkar.service.EnquiryService;

import io.micrometer.common.util.StringUtils;

@Service
public class Enquiryimpl implements EnquiryService {

	private EnquiryRepo enqRepo;
	private CounsellorRepo counsellorRepo;

	public Enquiryimpl(EnquiryRepo enqRepo, CounsellorRepo counsellorRepo) {
		super();
		this.enqRepo = enqRepo;
		this.counsellorRepo = counsellorRepo;
	}

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);

		if (counsellor == null) {
			throw new Exception("No counsellor found");

		}
		enq.setCounsellor(counsellor);

		Enquiry saved = enqRepo.save(enq);
		if (saved.getEnqId() != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiry(Integer counsellorId) {
		return enqRepo.getEnquiryByCounsellorId(counsellorId);
		
		
	}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnquiryFilterRequest filterReq, Integer CousellorId) {
		Enquiry enq = new Enquiry();
		if(StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}
		if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}
		if(StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		Example<Enquiry> of = Example.of(enq);
		List<Enquiry> enqList = enqRepo.findAll(of);
		
		return enqList;
	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return enqRepo.findById(enqId).orElse(null);
	}

}
