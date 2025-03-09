package com.swissre.employee.serviceImpl;

import com.swissre.employee.bean.Employee;
import com.swissre.employee.service.EmployeeSalaryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeSalaryReportServiceImpl implements EmployeeSalaryReportService {

    @Autowired
    EmployeeDataServiceImpl employeeDataService;

    @Autowired
    CSVReader csvReader;

    @Value("${salary.lower.limit.percentage}")
    public double lowerSalaryPercentage;

    @Value("${salary.upper.limit.percentage}")
    public double upperSalaryPercentage;

    public static Map<Integer, List<Employee>> managerSubourdinateMap = new HashMap<Integer, List<Employee>>();

    /**
     * Method to print employees having salary more/less than average
     * of there subordinates
     * - which managers earn less than they should, and by how much
     * - which managers earn more than they should, and by how much
     */
    @Override
    public void printEmployeeSalaryReportData() {

        List<Employee> employees = employeeDataService.getEmployees();

        Map<Integer, List<Employee>> managerSubourdinateMap = employeeDataService.getManagerSubourdinateMap();

        if (!employees.isEmpty() && !managerSubourdinateMap.isEmpty()) {

            Map<Integer, Employee> managerMap = employees.stream()
                    .collect(Collectors.toMap(Employee::getId, emp -> emp));

            managerSubourdinateMap.entrySet().stream()
                    .filter(entry -> {
                        double avgSubordinateSalary = entry.getValue().stream()
                                .mapToDouble(Employee::getSalary)
                                .average()
                                .orElse(0.0);

                        Employee manager = managerMap.get(entry.getKey());
                        double managerSalary = manager.getSalary();

                        return managerSalary < avgSubordinateSalary * lowerSalaryPercentage
                                || managerSalary > avgSubordinateSalary * upperSalaryPercentage;
                    })
                    .forEach(entry -> {
                        Employee manager = managerMap.get(entry.getKey());
                        double avgSubordinateSalary = entry.getValue().stream()
                                .mapToDouble(Employee::getSalary)
                                .average()
                                .orElse(0.0);


                        double managerSalary = manager.getSalary();
                        double lowerLimit = avgSubordinateSalary * lowerSalaryPercentage;
                        double upperLimit = avgSubordinateSalary * upperSalaryPercentage;

                        if (managerSalary < lowerLimit) {
                            System.out.println("Manager ID: " + manager.getId() + " earns " + managerSalary +
                                    " (" + String.format("%.2f", lowerLimit - managerSalary) + " less than expected)");
                        }

                        if (managerSalary > upperLimit) {
                            System.out.println("Manager ID: " + manager.getId() + " earns " + managerSalary +
                                    " (" + String.format("%.2f", managerSalary - upperLimit) + " more than expected)");
                        }
                    });
        }
    }


}

