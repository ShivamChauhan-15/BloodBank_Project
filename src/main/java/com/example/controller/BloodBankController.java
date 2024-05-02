package com.example.controller;

import com.example.dto.UserSignupDto;
import com.example.dto.UserLoginDto;
import com.example.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
public class BloodBankController {
    @Autowired
    private SignupService userSignupService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private SortAndFilterService sortAndFilterService;
    @Autowired
    private UserRequestService userRequestService;
    @Autowired
    private BloodReportService bloodReportService;

    @GetMapping("/")
    public String login(HttpServletRequest request) {
        if (request.getSession() != null)
            request.getSession().invalidate();
        return "login";
    }

    @GetMapping("/signup")
    public String signup(HttpServletRequest request, Model model) {
        model.addAttribute("role", request.getSession().getAttribute("role"));
        if(request.getSession()!=null)
            request.getSession().invalidate();
        return "signup";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute UserLoginDto userLoginDto,Model model) {
        //to update password on first login
        if(userLoginService.updatePassword(userLoginDto))
            return "updatedMessage";
        else {
            model.addAttribute("message","current password is wrong");
            return "updatePassword";
        }
    }

    //admin create agent
    @GetMapping("/createAgent")
    public String createAgent(HttpServletRequest request, Model model) {
        String role = (String) request.getSession().getAttribute("role");
        model.addAttribute("role", role);
        return "createAgent";
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        sortAndFilterService.updateActiveUsers(userName, "remove");
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/userLogin")
    public String userLogin(@ModelAttribute @Validated UserLoginDto userLoginDto, HttpServletRequest request, Model model) {
        UserSignupDto user = userLoginService.check(userLoginDto);
        if (user.getLoginAttempt()== 0) {
            HttpSession session = request.getSession();   //create session object
            session.setAttribute("userName", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("bloodGroup", user.getBloodGroup());

            //login user
            String userName = userLoginDto.getUsername();
            sortAndFilterService.updateActiveUsers(userName, "add");

            //if user login first time then show update password page
            if (user.isFirstLogin()) {
                model.addAttribute("userName",userName);
                return "updatePassword";
            }
            //if user exceed maximum limit of wrong attempt then locked page shown
            if (user.isLockStatus()) {
                System.out.println("locked");
                return "lock";
            }
            else {
                //check for user role to display their respective dashboard
                List<HashMap<String, Object>> allRequestedUsers = userRequestService.userRequestedList();
                switch (user.getRole().toLowerCase()) {
                    case "enduser":
                        model.addAttribute("dto", user);
                        return "user";
                    case "agent":
                        model.addAttribute("dto", user);
                        return "agent";
                    case "admin":
                        model.addAttribute("dto", user);
                        return "admin";
                    default:
                        return "errorInLogin";
                }
            }

        }
        else if (user.getLoginAttempt()==3) {
            return "lock";

        } else {
            model.addAttribute("loginAttempt",user.getLoginAttempt());
            return "errorInLogin";
        }

    }
    //for showing all users list on dashboard of admin and agent
    @GetMapping("/usersList")
    public String usersList(HttpServletRequest request,Model model){
        String role=(String)request.getSession().getAttribute("role");
        String userName=(String)request.getSession().getAttribute("userName");
        System.out.println("usersList");
        switch(role){
            case "admin":
                        model.addAttribute("usersList", userLoginService.findUsersList(userName));
                        return "endUserList";
            case "agent":
                List<UserSignupDto> agentEndUser = userLoginService.findUsersList(userName);
                model.addAttribute("usersList", userLoginService.findUsersList(userName));
                model.addAttribute("role",role);
                return "endUserList";
        }
        return "errorInLogin";
    }

    //for showing reports on admin/agent
    @GetMapping("/report")
    public String showReport(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String userName = (String) session.getAttribute("userName");
        List<HashMap<String, String>> requestCount = bloodReportService.getRequestCount(userName, role);
        model.addAttribute("requestDetails", requestCount);
        return "/report/report";

    }

    //for handling coin value
    @GetMapping("/coinReport")
    public String handleCoin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String role = (String) session.getAttribute("role");
        if (role != null && role.equalsIgnoreCase("agent")) {
            HashMap<String, Object[]> bloodGroupCoinsMap = bloodReportService.findCoinByBloodGroup(userName);
            model.addAttribute("usersList", bloodGroupCoinsMap);
            return "agentCoinReport";
        } else {
            List<HashMap<String, String>> requestedUserList = bloodReportService.requestedUserCoins();
            model.addAttribute("requestedUserList", requestedUserList);
            return "/admin/coinReport";
        }

    }

    //for register agent and user
    @PostMapping(value = "/register")
    public String register(@ModelAttribute @Validated UserSignupDto userDto, HttpServletRequest request, Model model) {
        String userRole = (String) request.getSession().getAttribute("role");
        if (userRole != null) {
            userDto.setPassword(String.valueOf(userDto.getDob()));
            if (userRole.equalsIgnoreCase("admin")) {
                userDto.setRole("agent");
                userDto.setCreatedBy("admin");
            } else {
                userDto.setRole("enduser");
                userDto.setCreatedBy((String) request.getSession().getAttribute("userName"));
            }
            userDto.setFirstLogin(true);
        } else {
            userDto.setRole("enduser");
            userDto.setCreatedBy("auto");
            userDto.setFirstLogin(false);
        }
        if (!userSignupService.insert(userDto))
            model.addAttribute("message", "Username Already exist please try again with different name");
        else
            model.addAttribute("message", "Successfully Registered!!");
        return "signup";

    }

    //for showing userlist on dashboard of admin and agent iwth filter and sorting
    @PostMapping("/endUserList")
    public String sortAndFilter(@RequestParam String sortBy, @RequestParam String filterBy,
                                @RequestParam String username, @RequestParam String startDate,
                                @RequestParam String endDate,
                                Model model, HttpServletRequest request) throws ParseException {
        String loginUserName = (String) request.getSession().getAttribute("userName");
        String loginUserRole = (String) request.getSession().getAttribute("role");
        System.out.println("in sortign and filter");
        System.out.println("userName"+loginUserName);
        List<UserSignupDto> userSignupDtoList = userLoginService.findUsersList(loginUserName);
        if (sortBy != null) {
            userSignupDtoList = sortAndFilterService.sortUsers(sortBy, userSignupDtoList);
            model.addAttribute("usersList", userSignupDtoList);
        }
        if(filterBy!=null){
            switch (filterBy) {
                case "notLoggedIn":
                    model.addAttribute("usersList", sortAndFilterService.notLoggedInUser(userSignupDtoList));
                    break;
                case "byAgent":
                    model.addAttribute("usersList", sortAndFilterService.filterByAgent(username,userSignupDtoList));
                    break;
                case "byUsername":
                    model.addAttribute("usersList", sortAndFilterService.filterByUsername(username, userSignupDtoList));
                    break;
                case "createdBetween":
                    model.addAttribute("usersList", sortAndFilterService.filterByDate(startDate, endDate, userSignupDtoList));
                    break;
                default:
                    model.addAttribute("usersList", userSignupDtoList);

            }
        }
        return "endUserList";


    }

}

