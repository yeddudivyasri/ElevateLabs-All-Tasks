package com.jobportal.service;

import com.jobportal.model.*;
import com.jobportal.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobService jobService;

    public JobApplication applyToJob(Long jobId, User applicant, String coverLetter) {
        if (applicationRepository.existsByJobIdAndApplicantId(jobId, applicant.getId())) {
            throw new IllegalStateException("You have already applied to this job");
        }

        Job job = jobService.getJobById(jobId);

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setCoverLetter(coverLetter);
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    public List<JobApplication> getApplicationsByApplicant(User applicant) {
        return applicationRepository.findByApplicant(applicant);
    }

    public List<JobApplication> getApplicationsForEmployer(Long employerId) {
        return applicationRepository.findByJobEmployerId(employerId);
    }

    public List<JobApplication> getApplicationsForJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    public JobApplication updateStatus(Long applicationId, ApplicationStatus status, User employer) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        if (!application.getJob().getEmployer().getId().equals(employer.getId())) {
            throw new SecurityException("You are not authorized to update this application");
        }

        application.setStatus(status);
        return applicationRepository.save(application);
    }
}
