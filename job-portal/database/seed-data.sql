-- Job Portal System - Sample Seed Data
-- Passwords below are BCrypt hashes for "password123"

USE job_portal_db;

INSERT INTO users (full_name, email, password, role, company_name) VALUES
('Priya Sharma', 'employer@demo.com', '$2a$10$DowQveQOQ8gAyMtNnEx9V.QU8WTZh8K8wEXIfV2vKtAW6.knFsj9.', 'EMPLOYER', 'TechNova Solutions'),
('Rakshitha Tamada', 'applicant@demo.com', '$2a$10$DowQveQOQ8gAyMtNnEx9V.QU8WTZh8K8wEXIfV2vKtAW6.knFsj9.', 'APPLICANT', NULL);

INSERT INTO jobs (title, description, company, location, salary, job_type, posted_date, employer_id) VALUES
('Backend Java Developer', 'Develop and maintain REST APIs using Spring Boot. Work with MySQL and JPA. 0-2 years experience welcome.', 'TechNova Solutions', 'Hyderabad', 700000, 'FULL_TIME', CURDATE(), 1),
('Frontend React Developer', 'Build responsive UIs with React and Tailwind. Collaborate with backend team on API integration.', 'TechNova Solutions', 'Bangalore', 650000, 'FULL_TIME', CURDATE(), 1),
('Software Engineering Intern', '6-month internship for final-year students. Work on real production features under mentorship.', 'TechNova Solutions', 'Remote', 180000, 'INTERNSHIP', CURDATE(), 1);
