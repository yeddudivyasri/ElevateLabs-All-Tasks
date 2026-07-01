package com.jobportal.controller;

import com.jobportal.config.CustomUserDetails;
import com.jobportal.model.ApplicationStatus;
import com.jobportal.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final JobApplicationService applicationService;

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        var application = applicationService.updateStatus(id, status, userDetails.getUser());
        return "redirect:/jobs/" + application.getJob().getId() + "/applicants";
    }
}
