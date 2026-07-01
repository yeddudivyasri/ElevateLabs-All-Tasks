package com.jobportal.config;

import com.jobportal.model.*;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User employer = new User();
        employer.setFullName("Priya Sharma");
        employer.setEmail("employer@demo.com");
        employer.setPassword(passwordEncoder.encode("password123"));
        employer.setRole(Role.EMPLOYER);
        employer.setCompanyName("TechNova Solutions");
        userRepository.save(employer);

        User applicant = new User();
        applicant.setFullName("Rakshitha Tamada");
        applicant.setEmail("applicant@demo.com");
        applicant.setPassword(passwordEncoder.encode("password123"));
        applicant.setRole(Role.APPLICANT);
        userRepository.save(applicant);

        Job job1 = new Job();
        job1.setTitle("Backend Java Developer");
        job1.setCompany("TechNova Solutions");
        job1.setLocation("Hyderabad");
        job1.setJobType("FULL_TIME");
        job1.setSalary(700000.0);
        job1.setDescription("Develop and maintain REST APIs using Spring Boot. Work with MySQL and JPA. 0-2 years experience welcome.");
        job1.setEmployer(employer);
        jobRepository.save(job1);

        Job job2 = new Job();
        job2.setTitle("Frontend React Developer");
        job2.setCompany("TechNova Solutions");
        job2.setLocation("Bangalore");
        job2.setJobType("FULL_TIME");
        job2.setSalary(650000.0);
        job2.setDescription("Build responsive UIs with React and Tailwind. Collaborate with backend team on API integration.");
        job2.setEmployer(employer);
        jobRepository.save(job2);

        Job job3 = new Job();
        job3.setTitle("Software Engineering Intern");
        job3.setCompany("TechNova Solutions");
        job3.setLocation("Remote");
        job3.setJobType("INTERNSHIP");
        job3.setSalary(180000.0);
        job3.setDescription("6-month internship for final-year students. Work on real production features under mentorship.");
        job3.setEmployer(employer);
        jobRepository.save(job3);
    }
}
