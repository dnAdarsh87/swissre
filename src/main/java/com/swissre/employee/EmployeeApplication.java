package com.swissre.employee;


import com.swissre.employee.serviceImpl.EmployeeReportingLineServiceImpl;
import com.swissre.employee.serviceImpl.EmployeeSalaryReportServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApplication implements CommandLineRunner {

    private final EmployeeSalaryReportServiceImpl employeeSalaryReportService;
    private final EmployeeReportingLineServiceImpl employeeReportingLineService;

    public EmployeeApplication(EmployeeSalaryReportServiceImpl employeeSalaryReportService, EmployeeReportingLineServiceImpl employeeReportingLineService) {
        this.employeeSalaryReportService = employeeSalaryReportService;
        this.employeeReportingLineService = employeeReportingLineService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        employeeSalaryReportService.printEmployeeSalaryReportData();
        employeeReportingLineService.printEmployeeReportingLinetData();
    }
}
