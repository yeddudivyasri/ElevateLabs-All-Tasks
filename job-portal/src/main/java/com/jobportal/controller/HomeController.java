package com.jobportal.controller;

import com.jobportal.config.CustomUserDetails;
import com.jobportal.dto.RegistrationDto;
import com.jobportal.model.Role;
import com.jobportal.model.User;
import com.jobportal.service.JobApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final JobService jobService;
    private final JobApplicationService applicationService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("jobCount", jobService.getAllJobs().size());
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationDto") RegistrationDto dto,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(dto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);

        if (user.getRole() == Role.EMPLOYER) {
            model.addAttribute("postedJobs", jobService.getJobsByEmployer(user));
            model.addAttribute("totalApplicants",
                    applicationService.getApplicationsForEmployer(user.getId()).size());
            return "dashboard-employer";
        } else {
            model.addAttribute("applications", applicationService.getApplicationsByApplicant(user));
            return "dashboard-applicant";
        }
    }
}
