-- Job Portal System - MySQL Schema
-- Note: Hibernate (ddl-auto=update) will auto-generate this schema on first run.
-- This file is provided as a deliverable / reference and for manual setup.

CREATE DATABASE IF NOT EXISTS job_portal_db;
USE job_portal_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    company_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    company VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    salary DOUBLE NOT NULL,
    job_type VARCHAR(20) NOT NULL,
    posted_date DATE NOT NULL,
    employer_id BIGINT,
    CONSTRAINT fk_job_employer FOREIGN KEY (employer_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS job_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT,
    applicant_id BIGINT,
    status VARCHAR(20) NOT NULL,
    applied_date DATE NOT NULL,
    cover_letter VARCHAR(1000),
    CONSTRAINT fk_app_job FOREIGN KEY (job_id) REFERENCES jobs(id),
    CONSTRAINT fk_app_applicant FOREIGN KEY (applicant_id) REFERENCES users(id),
    CONSTRAINT uq_job_applicant UNIQUE (job_id, applicant_id)
);
