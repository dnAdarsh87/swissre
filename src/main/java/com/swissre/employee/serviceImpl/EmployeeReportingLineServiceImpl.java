package com.swissre.employee.serviceImpl;

import com.swissre.employee.bean.Employee;
import com.swissre.employee.service.EmployeeReportingLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeReportingLineServiceImpl implements EmployeeReportingLineService {

    @Autowired
    EmployeeDataServiceImpl employeeDataService;

    @Value("${employee.reporting.level.limit}")
    int reportingLimit;


    /**
     * Method to print employees having reporting level more than 4
     * - which employees have a reporting line which is too long, and by how much
     */
    @Override
    public void printEmployeeReportingLinetData() {

        List<Employee> employees = employeeDataService.getEmployees();

        Map<Integer, List<Employee>> managerSubourdinateMap = employeeDataService.getManagerSubourdinateMap();

        if (!employees.isEmpty() && !managerSubourdinateMap.isEmpty()) {
            Map<Integer, List<Employee>> map = new HashMap<>();
            Queue<Integer> queue = new LinkedList<>();
            // Using ceo - employee ID
            queue.addAll(managerSubourdinateMap.getOrDefault(employeeDataService.getCeo().get().getId(), Collections.emptyList())
                    .stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList()));
            int level = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();

                for (int i = 0; i < size; i++) {
                    int employeeId = queue.poll();
                    queue.addAll(managerSubourdinateMap.getOrDefault(employeeId, Collections.emptyList())
                            .stream()
                            .map(Employee::getId)
                            .collect(Collectors.toList()));
                    if (level > reportingLimit) {
                        System.out.println("Level of Reporting for Employee ID " + employeeId + " is  " + level);
                    }
                }
                level++;
            }

        }
    }
}
