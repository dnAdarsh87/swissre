package com.swissre.employee;

import com.swissre.employee.bean.Employee;
import com.swissre.employee.service.EmployeeSalaryReportService;
import com.swissre.employee.serviceImpl.CSVReader;
import com.swissre.employee.serviceImpl.EmployeeDataServiceImpl;
import com.swissre.employee.serviceImpl.EmployeeSalaryReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeSalaryReportServiceImplTest {

    @InjectMocks
    private EmployeeSalaryReportServiceImpl employeeSalaryReportService;

    @Mock
    private EmployeeDataServiceImpl employeeDataService;

    @Mock
    private CSVReader csvReader;

    @BeforeEach
    void setUp() {
        // Inject mock property values
        employeeSalaryReportService.lowerSalaryPercentage = 0.2; // 20%
        employeeSalaryReportService.upperSalaryPercentage = 1.5; // 150%
    }

    @Test
    void testPrintEmployeeSalaryReportData_ManagerEarnsLessThanExpected() {
        // Mock Employee Data
        Employee manager = new Employee(100, "John", "Doe", 5000.0, null);
        Employee sub1 = new Employee(101, "Jane", "Smith", 30000.0, 100);
        Employee sub2 = new Employee(102, "Alice", "Johnson", 25000.0, 100);

        List<Employee> employees = Arrays.asList(manager, sub1, sub2);

        Map<Integer, List<Employee>> managerSubordinateMap = new HashMap<>();
        managerSubordinateMap.put(100, Arrays.asList(sub1, sub2));

        when(employeeDataService.getEmployees()).thenReturn(employees);
        when(employeeDataService.getManagerSubourdinateMap()).thenReturn(managerSubordinateMap);

        // Capture console output
        assertDoesNotThrow(() -> employeeSalaryReportService.printEmployeeSalaryReportData());
    }

    @Test
    void testPrintEmployeeSalaryReportData_ManagerEarnsMoreThanExpected() {
        // Mock Employee Data
        Employee manager = new Employee(200, "Bruce", "Wayne", 80000.0, null);
        Employee sub1 = new Employee(201, "Clark", "Kent", 30000.0, 200);
        Employee sub2 = new Employee(202, "Diana", "Prince", 35000.0, 200);

        List<Employee> employees = Arrays.asList(manager, sub1, sub2);

        Map<Integer, List<Employee>> managerSubordinateMap = new HashMap<>();
        managerSubordinateMap.put(200, Arrays.asList(sub1, sub2));

        when(employeeDataService.getEmployees()).thenReturn(employees);
        when(employeeDataService.getManagerSubourdinateMap()).thenReturn(managerSubordinateMap);

        // Capture console output
        assertDoesNotThrow(() -> employeeSalaryReportService.printEmployeeSalaryReportData());
    }

    @Test
    void testPrintEmployeeSalaryReportData_EmptyData() {
        when(employeeDataService.getEmployees()).thenReturn(Collections.emptyList());
        when(employeeDataService.getManagerSubourdinateMap()).thenReturn(Collections.emptyMap());

        // No errors should occur when handling empty data
        assertDoesNotThrow(() -> employeeSalaryReportService.printEmployeeSalaryReportData());
    }
}
