package com.sarkar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sarkar.dto.ViewEnquiryFilterRequest;
import com.sarkar.entity.Enquiry;
@Service
public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq,Integer counsellorId) throws Exception;
	public List<Enquiry> getAllEnquiry(Integer counsellorId);
	public List<Enquiry> getEnquiriesWithFilter(ViewEnquiryFilterRequest filterReq,
			Integer Cousellor_id);
	public Enquiry getEnquiryById(Integer enqId);

}
