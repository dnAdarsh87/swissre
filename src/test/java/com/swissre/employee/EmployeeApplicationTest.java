package com.swissre.employee;

import com.swissre.employee.serviceImpl.EmployeeReportingLineServiceImpl;
import com.swissre.employee.serviceImpl.EmployeeSalaryReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeApplicationTest {

    @InjectMocks
    private EmployeeApplication employeeApplication;

    @Mock
    private EmployeeSalaryReportServiceImpl employeeSalaryReportService;

    @Mock
    private EmployeeReportingLineServiceImpl employeeReportingLineService;

    @Test
    void testRunMethod_CallsServicesSuccessfully() {
        // Run the application logic
        employeeApplication.run();

        // Verify the method calls
        verify(employeeSalaryReportService, times(1)).printEmployeeSalaryReportData();
        verify(employeeReportingLineService, times(1)).printEmployeeReportingLinetData();
    }


    @Test
    void testRunMethod_NoExceptionThrown() {
        // Ensure no exceptions are thrown when services are invoked
        doNothing().when(employeeSalaryReportService).printEmployeeSalaryReportData();
        doNothing().when(employeeReportingLineService).printEmployeeReportingLinetData();

        employeeApplication.run();

        verify(employeeSalaryReportService, times(1)).printEmployeeSalaryReportData();
        verify(employeeReportingLineService, times(1)).printEmployeeReportingLinetData();
    }

}
