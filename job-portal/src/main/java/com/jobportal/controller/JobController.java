package com.jobportal.controller;

import com.jobportal.config.CustomUserDetails;
import com.jobportal.dto.JobSearchDto;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.service.JobApplicationService;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    private final JobApplicationService applicationService;

    @GetMapping
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        model.addAttribute("searchDto", new JobSearchDto());
        return "job-list";
    }

    @PostMapping("/search")
    public String searchJobs(@ModelAttribute JobSearchDto searchDto, Model model) {
        model.addAttribute("jobs", jobService.searchJobs(searchDto));
        model.addAttribute("searchDto", searchDto);
        return "job-list";
    }

    @GetMapping("/{id}")
    public String jobDetails(@PathVariable Long id, Model model,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);

        if (userDetails != null && userDetails.getUser().getRole().name().equals("APPLICANT")) {
            boolean alreadyApplied = applicationService.getApplicationsByApplicant(userDetails.getUser())
                    .stream().anyMatch(a -> a.getJob().getId().equals(id));
            model.addAttribute("alreadyApplied", alreadyApplied);
        }
        return "job-details";
    }

    @GetMapping("/post")
    public String postJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-post";
    }

    @PostMapping("/post")
    public String postJob(@Valid @ModelAttribute("job") Job job, BindingResult result,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (result.hasErrors()) {
            return "job-post";
        }
        jobService.postJob(job, userDetails.getUser());
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/apply")
    public String applyForm(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "job-apply";
    }

    @PostMapping("/{id}/apply")
    public String apply(@PathVariable Long id, @RequestParam(required = false) String coverLetter,
                         @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        try {
            applicationService.applyToJob(id, userDetails.getUser(), coverLetter);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("job", jobService.getJobById(id));
            return "job-apply";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/applicants")
    public String viewApplicants(@PathVariable Long id, Model model,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Job job = jobService.getJobById(id);
        if (!job.getEmployer().getId().equals(userDetails.getUser().getId())) {
            return "redirect:/dashboard";
        }
        model.addAttribute("job", job);
        model.addAttribute("applications", applicationService.getApplicationsForJob(job));
        return "job-applicants";
    }

    @PostMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        jobService.deleteJob(id, userDetails.getUser());
        return "redirect:/dashboard";
    }
}
