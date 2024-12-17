package com.sarkar.rest;

import com.sarkar.dto.ViewEnquiryFilterRequest;
import com.sarkar.entity.Enquiry;
import com.sarkar.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnquiryController {

	private final EnquiryService enquiryService;

	public EnquiryController(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Integer enqId,Model model) {
		
		Enquiry enquiryById = enquiryService.getEnquiryById(enqId);
		model.addAttribute("enq", enquiryById);
          return "enquiryform";
	}
	
	

	@PostMapping("/filter-enq")
	public String filterEnquiries(@ModelAttribute("viewEnqFilter") 
	ViewEnquiryFilterRequest viewEnqFilter,
			HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		List<Enquiry> enqList = enquiryService.getEnquiriesWithFilter(viewEnqFilter, counsellorId);
		model.addAttribute("enq", enqList);
		model.addAttribute("viewEnqFilter", viewEnqFilter);

		return "viewEnqsPage";
	}

	@GetMapping("/view-enquiries")
	public String getEnquiries(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		List<Enquiry> enqList = enquiryService.getAllEnquiry(counsellorId);

		model.addAttribute("enquiries", enqList);

		ViewEnquiryFilterRequest filterReq = new ViewEnquiryFilterRequest();
		model.addAttribute("viewEnqFilter", filterReq);

		return "viewEnqsPage"; 
	}

	@GetMapping("/enquiry")
	public String addEnquiryForm(Model model) {
		Enquiry enqobj = new Enquiry();
		model.addAttribute("enq", enqobj);
		return "enquiryform";
	}

	@PostMapping("/addEnq")
	public String handleAddEnquiry(@ModelAttribute("enq") 
	Enquiry enq, HttpServletRequest req, Model model)
			throws Exception {
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		boolean isSave = enquiryService.addEnquiry(enq, counsellorId);
		if (isSave) {
			model.addAttribute("smsg", "Enquiry Added Successfully!");
		} else {
			model.addAttribute("emsg", "Failed to Add Enquiry!");
		}

		return "enquiryform";
	}
}
