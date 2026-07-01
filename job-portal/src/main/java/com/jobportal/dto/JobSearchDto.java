package com.jobportal.dto;

import lombok.Data;

@Data
public class JobSearchDto {
    private String title;
    private String location;
    private String jobType;
    private Double minSalary;
}
