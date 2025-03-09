package com.swissre.employee;

import com.swissre.employee.bean.Employee;

import com.swissre.employee.serviceImpl.CSVReader;
import com.swissre.employee.serviceImpl.EmployeeDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeDataServiceImplTest {

    @InjectMocks
    private EmployeeDataServiceImpl employeeDataService;

    @Mock
    private CSVReader csvReader;

    private List<String[]> mockData;

    @BeforeEach
    void setUp() {
        mockData = Arrays.asList(
                new String[]{"100", "John", "Doe", "60000", ""},
                new String[]{"101", "Jane", "Smith", "50000", "100"},
                new String[]{"102", "Alice", "Johnson", "55000", "100"},
                new String[]{"103", "Bob", "Marley", "45000", "101"}
        );
    }

    @Test
    void testGetEmployeeListFromData_ValidData() {
        List<Employee> employees = employeeDataService.getEmployeeListFromData(mockData);

        assertEquals(4, employees.size());

        assertEquals("John", employees.get(0).getFirstName());
        assertEquals("Jane", employees.get(1).getFirstName());
        assertEquals(60000, employees.get(0).getSalary());
        assertEquals(100, employees.get(1).getManagerId());
    }

    @Test
    void testGetEmployeeListFromData_EmptyData() {
        List<Employee> employees = employeeDataService.getEmployeeListFromData(Collections.emptyList());
        assertTrue(employees.isEmpty());
    }

    @Test
    void testGetManagerSubordinateMap_ValidData() {
        // Mock employee data
        employeeDataService.getEmployeeListFromData(mockData);

        Map<Integer, List<Employee>> managerSubordinateMap = employeeDataService.getManagerSubourdinateMap();

        assertFalse(managerSubordinateMap.isEmpty());
        assertEquals(2, managerSubordinateMap.get(100).size());
        assertEquals(1, managerSubordinateMap.get(101).size());
    }



    @Test
    void testGetEmployees_ValidData() {
        when(csvReader.parseCsv()).thenReturn(mockData);

        List<Employee> employees = employeeDataService.getEmployees();

        assertEquals(4, employees.size());
        verify(csvReader, times(1)).parseCsv(); // Ensures CSV reading logic is invoked once
    }

    @Test
    void testGetEmployees_EmptyData() {
        when(csvReader.parseCsv()).thenReturn(Collections.emptyList());

        List<Employee> employees = employeeDataService.getEmployees();

        assertTrue(employees.isEmpty());
    }



    @Test
    void testGetCeo_EmptyData() {
        employeeDataService.getEmployeeListFromData(Collections.emptyList());

        Optional<Employee> ceo = employeeDataService.getCeo();

        assertFalse(ceo.isPresent());
    }
}
