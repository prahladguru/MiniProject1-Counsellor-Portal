package com.sarkar.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sarkar.dto.DashBoardResponse;
import com.sarkar.entity.Counsellor;
import com.sarkar.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

    private CounsellorService counsellorService;

    public CounsellorController(CounsellorService counsellorService) {
        this.counsellorService = counsellorService;
    }

    // Home page (landing page)
    @GetMapping("/")
    public String index(Model model) {
        Counsellor obj = new Counsellor();
        model.addAttribute("counsellor", obj); 
        return "login";
    } 

    // Login handler
    @PostMapping("login")
    public String handleLogin(Counsellor counsellor, HttpServletRequest request, Model model) {
        Counsellor login = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

        if (login == null) {
            model.addAttribute("emsg", "Invalid Credentials");
            return "login";  // Return to the login page with error
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("counsellorId", login.getCounsellorId());

            // Fetch dashboard info
            DashBoardResponse dobj = counsellorService.getDashBoardInfo(login.getCounsellorId());
            model.addAttribute("dashboardInfo", dobj);  // Ensure dashboard info is passed
            return "dashboard";  // Redirect to the dashboard
        }
    }

    // Logout handler
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";  // Redirect to the home page after logout
    }

    // Registration page
    @GetMapping("/register")
    public String registerPage(Model model) {
        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor", cobj);
        return "register";
    }

    // Registration handler
    @PostMapping("/register")
    public String handleRegistration(Counsellor counsellor, Model model) {
        Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
        if (byEmail != null) {
            model.addAttribute("dmsg", "Duplicate Email");
            return "register";
        }

        boolean isRegistered = counsellorService.register(counsellor);
        if (isRegistered) {
            model.addAttribute("smsg", "Registration Success");
        } else {
            model.addAttribute("emsg", "Registration Failed");
        }

        return "register";  // Return to registration page with a message
    }

    // Dashboard page
    @GetMapping("/dashboard")
    public String displayDashboard(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");

        // Check if counsellorId is null to avoid NullPointerException
        if (counsellorId == null) {
            model.addAttribute("emsg", "Session expired, please log in again.");
            return "redirect:/";
        }

        // Fetch dashboard info for the logged-in counsellor
        DashBoardResponse dbInfo = counsellorService.getDashBoardInfo(counsellorId);
        model.addAttribute("dashboardInfo", dbInfo);  // Ensure dashboardInfo is not null
        return "dashboard";
    }
}
