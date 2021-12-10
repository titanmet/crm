package com.ratnikov.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbsenteeismDTO {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private String absenteeismName;
    private Date dateFrom;
    private Date dateTo;
}