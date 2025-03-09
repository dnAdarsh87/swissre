package com.swissre.employee.service;


import com.swissre.employee.bean.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Get Data related to Employees from datalist
 */
@Service
public interface EmployeeDataService {

    List<Employee> getEmployeeListFromData(List<String[]> dataList);

    Map<Integer, List<Employee>> getManagerSubourdinateMap();

    List<Employee> getEmployees();

    Optional<Employee> getCeo();
}
