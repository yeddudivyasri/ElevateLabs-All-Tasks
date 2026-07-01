# Job Portal System

A full-stack web application built with **Spring Boot**, **Spring Security**, **Spring Data JPA**, **Thymeleaf**, and **MySQL/H2** that connects employers with job seekers.

## Features

### Employer
- Register/login with role `EMPLOYER`
- Post new job listings (title, description, company, location, salary, type)
- View all posted jobs on dashboard
- View applicants for each job
- Update application status (Applied → Shortlisted → Selected/Rejected)
- Delete job postings

### Applicant
- Register/login with role `APPLICANT`
- Browse all job listings
- Search/filter jobs by title, location, job type, and minimum salary
- View full job details
- Apply to jobs with an optional cover letter
- Track application status from dashboard

### Security
- Spring Security with BCrypt password hashing
- Role-based route protection (`EMPLOYER` vs `APPLICANT`)
- Form-based login/logout
- Authorization checks at service layer (e.g. an employer can only manage their own jobs/applicants)

## Tech Stack
- Java 17, Spring Boot 3.3
- Spring Web, Spring Data JPA, Spring Security, Thymeleaf
- H2 (default, in-memory, zero setup) or MySQL (production)
- Maven

## How to Run

### Option 1: H2 (default, no DB setup needed)
```bash
mvn spring-boot:run
```
App runs at `http://localhost:8080`. H2 console available at `/h2-console` (JDBC URL: `jdbc:h2:mem:jobportaldb`).

### Option 2: MySQL
1. Edit `src/main/resources/application.properties`:
   - Comment out the H2 block
   - Uncomment the MySQL block and set your credentials
2. Run:
```bash
mvn spring-boot:run
```
Hibernate will auto-create tables (`ddl-auto=update`). Optionally run `database/schema.sql` and `database/seed-data.sql` manually.

## Demo Credentials
| Role      | Email              | Password    |
|-----------|--------------------|-------------|
| Employer  | employer@demo.com  | password123 |
| Applicant | applicant@demo.com | password123 |

Sample data (3 jobs) is seeded automatically on first run.

## Project Structure
```
src/main/java/com/jobportal/
├── config/         # Security config, UserDetails, data seeder
├── controller/      # MVC controllers (Home, Job, Application)
├── dto/              # Registration & search DTOs
├── model/            # JPA entities (User, Job, JobApplication, enums)
├── repository/       # Spring Data JPA repositories
└── service/           # Business logic layer
src/main/resources/
├── templates/        # Thymeleaf views
├── static/css/        # Stylesheet
└── application.properties
database/
├── schema.sql        # MySQL DDL (optional, ddl-auto=update handles this)
└── seed-data.sql     # Sample data inserts (optional)
```

## Resume Highlights
- Implemented **role-based access control** with Spring Security and BCrypt
- Designed **relational schema** with JPA (One-to-Many: User→Jobs, User→Applications; Many-to-One: Application→Job/User)
- Built **dynamic search/filter** using JPQL with optional parameters
- Enforced **authorization at the service layer** (ownership checks beyond URL-level security)
- Server-rendered UI with Thymeleaf + Spring Security tag integration
