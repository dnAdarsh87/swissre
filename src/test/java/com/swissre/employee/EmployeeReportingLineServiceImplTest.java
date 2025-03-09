package com.swissre.employee;

import com.swissre.employee.bean.Employee;
import com.swissre.employee.serviceImpl.EmployeeDataServiceImpl;
import com.swissre.employee.serviceImpl.EmployeeReportingLineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeReportingLineServiceImplTest {

    @InjectMocks
    private EmployeeReportingLineServiceImpl reportingLineService;

    @Mock
    private EmployeeDataServiceImpl employeeDataService;

    private List<Employee> mockEmployeeList;
    private Map<Integer, List<Employee>> mockManagerSubordinateMap;

    @BeforeEach
    void setUp() {
        mockEmployeeList = Arrays.asList(
                new Employee(100, "John", "Doe", 60000.0, null),   // CEO
                new Employee(101, "Jane", "Smith", 50000.0, 100),  // Reports to John
                new Employee(102, "Alice", "Johnson", 55000.0, 101), // Reports to Jane
                new Employee(103, "Bob", "Marley", 45000.0, 102),  // Reports to Alice
                new Employee(104, "Tom", "Hardy", 40000.0, 103)    // Deep-level report
        );

        mockManagerSubordinateMap = new HashMap<>();
        mockManagerSubordinateMap.put(100, Arrays.asList(mockEmployeeList.get(1))); // CEO -> Jane
        mockManagerSubordinateMap.put(101, Arrays.asList(mockEmployeeList.get(2))); // Jane -> Alice
        mockManagerSubordinateMap.put(102, Arrays.asList(mockEmployeeList.get(3))); // Alice -> Bob
        mockManagerSubordinateMap.put(103, Arrays.asList(mockEmployeeList.get(4))); // Bob -> Tom

        // Set reporting limit dynamically using ReflectionTestUtils
        ReflectionTestUtils.setField(reportingLineService, "reportingLimit", 2);
    }


}
