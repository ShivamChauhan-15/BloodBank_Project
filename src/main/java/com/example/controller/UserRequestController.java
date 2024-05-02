package com.example.controller;

import com.example.dto.BloodStockDto;
import com.example.dto.UserRequestDto;
import com.example.dto.UserSignupDto;
import com.example.model.UserRequestModel;
import com.example.service.BloodStockService;
import com.example.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserRequestController {
    @Autowired
    private UserRequestService userRequestService;
    @Autowired
    private BloodStockService bloodStockService;

    // Handle user blood request
    @GetMapping("/userRequest")
    public String requestPage(){
        return "/requestDashboard/userRequest";
    }
    @PostMapping("/submitBloodForm")
    public String bloodRequest(@ModelAttribute @Validated UserRequestDto userRequestDto,
                               Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        if(userRequestDto.getType().equalsIgnoreCase("donor")){
            userRequestDto.setBloodGroup((String) session.getAttribute("bloodGroup"));
        }
        String message = userRequestService.handleUserRequest(userRequestDto, userName);
        model.addAttribute("message",message);
        return "/requestDashboard/userRequest";
    }

    //to show request of users
    @GetMapping("/userBloodRequest")
    public String bloodRequest(@RequestParam(required = false) String filterBy,@RequestParam(required = false) String username,
                               @RequestParam(required = false) String startDate,@RequestParam(required = false) String input,
                                @RequestParam(required = false) String endDate,@RequestParam(required = false)String status,
                               HttpSession session, Model model) throws ParseException {
        String userRole= (String) session.getAttribute("role");
        String userName=(String)session.getAttribute("userName");
        List<HashMap<String, Object>> allRequestedUsers = userRequestService.userRequestedList();
        switch(userRole){
            case "admin":
                List<HashMap<String,Object>> endUserRequest=userRequestService.userRequestedList();
                List<HashMap<String, Object>> filteredRequest = userRequestService.filterBloodRequest(endUserRequest, filterBy, input,status, startDate, endDate);
                model.addAttribute("requestedUserList",filteredRequest);
                return "/requestDashboard/requestStatus";
            case "agent":
                List<HashMap<String, Object>> agentUserRequest = userRequestService.agentUserRequest(allRequestedUsers, userName);
                List<HashMap<String, Object>> filteredAgentRequest = userRequestService.filterBloodRequest(agentUserRequest, filterBy, input,status, startDate, endDate);
                model.addAttribute("requestedUserList",filteredAgentRequest);
                return "/requestDashboard/agentUserRequest";
            case "enduser":
                List<HashMap<String, Object>> userRequest= userRequestService.findEnduserRequest(allRequestedUsers,userName);
                List<HashMap<String, Object>> filteredUserRequest = userRequestService.filterBloodRequest(userRequest, filterBy, input,status, startDate, endDate);
                model.addAttribute("requestedUserList",filteredUserRequest);
                return "/requestDashboard/endUserRequest";
            default:
                return "errorInLogin";
        }
    }

    //remark controller
    @GetMapping("/accept")
    public String requestAccept(@RequestParam String id,RedirectAttributes redirectAttributes){
        String progress=userRequestService.requestApproved(id);
        redirectAttributes.addFlashAttribute("progress",progress);
        return "redirect:/userBloodRequest";

    }
    //for reject
    @GetMapping("/reject")
    public String requestReject(@RequestParam String id,@RequestParam String userName, Model model){
        model.addAttribute("userName",userName);
        userRequestService.requestRejected(id,userName);
        model.addAttribute("id",id);
        return "/requestDashboard/requestRemark";
    }

    //for remark
    @PostMapping("/handleRemark")
    public String handleRemark(@RequestParam("id") String id,@RequestParam("remark") String remark){
        userRequestService.requestRejected(id,remark);
        return "redirect:/userBloodRequest";
    }

    //for showing available blood
    @GetMapping("/bloodStock")
    public String availableBlood(Model model){
        List<BloodStockDto> availableBlood = bloodStockService.getBloodStock();
        model.addAttribute("availableBloodList",availableBlood);
        return "bloodStock";
    }

}

