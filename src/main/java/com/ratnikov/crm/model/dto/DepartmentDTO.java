package com.ratnikov.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    Long departmentId;
    String departmentName;
    String city;
    //source = "managers.firstName"
    String managerFirstName;
    //source = "managers.lastName"
    String managerLastName;
}
