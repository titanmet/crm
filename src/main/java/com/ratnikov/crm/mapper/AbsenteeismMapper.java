package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.Absenteeism;
import com.ratnikov.crm.model.dto.AbsenteeismDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AbsenteeismMapper {
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "firstName", source = "employee.firstName")
    @Mapping(target = "lastName", source = "employee.lastName")
    @Mapping(target = "departmentName", source = "employee.department.departmentName")
    @Mapping(target = "absenteeismName", source = "reasonOfAbsenteeismCode.absenteeismName")
    AbsenteeismDTO mapAbsenteeismToDto(Absenteeism absenteeism);
}
