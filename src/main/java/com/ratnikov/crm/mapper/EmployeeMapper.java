package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.Employee;
import com.ratnikov.crm.model.dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "role", source = "userRole")
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.departmentName")
    EmployeeDTO mapEmployeeToDto(Employee employee);
}
