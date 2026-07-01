package com.jobportal.service;

import com.jobportal.dto.JobSearchDto;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job postJob(Job job, User employer) {
        job.setEmployer(employer);
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getJobsByEmployer(User employer) {
        return jobRepository.findByEmployer(employer);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + id));
    }

    public List<Job> searchJobs(JobSearchDto filter) {
        String title = blankToNull(filter.getTitle());
        String location = blankToNull(filter.getLocation());
        String jobType = blankToNull(filter.getJobType());
        Double minSalary = filter.getMinSalary();

        return jobRepository.searchJobs(title, location, jobType, minSalary);
    }

    public void deleteJob(Long id, User employer) {
        Job job = getJobById(id);
        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new SecurityException("You are not authorized to delete this job");
        }
        jobRepository.delete(job);
    }

    private String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
