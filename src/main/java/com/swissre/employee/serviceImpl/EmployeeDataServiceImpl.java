package com.swissre.employee.serviceImpl;

import com.swissre.employee.bean.Employee;
import com.swissre.employee.service.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implimentation for getting Data related to Employees from datalist
 */
@Service
public class EmployeeDataServiceImpl implements EmployeeDataService {


    @Autowired
    CSVReader csvReader;

    private static  List<Employee> employees = new ArrayList<>();

    public static Map<Integer, List<Employee>> managerSubourdinateMap = new HashMap<Integer, List<Employee>>();

    private static Employee ceo;

    @Override
    public List<Employee> getEmployeeListFromData(List<String[]> dataList) {
        List<Employee> employees = new ArrayList<>();
        if(employees.isEmpty()){
            dataList.stream().forEach(data -> {
                Integer id = Integer.parseInt(data[0].trim());
                String firstName = data[1].trim();
                String lastName = data[2].trim();
                Double salary = Double.parseDouble(data[3].trim());
                Integer managerId = null;
                if(data.length > 4){
                    managerId = data[4].trim().isEmpty() ? null : Integer.parseInt(data[4].trim());
                } else{
                    ceo = new Employee(id, firstName, lastName, salary, managerId);
                }
                employees.add(new Employee(id, firstName, lastName, salary, managerId));
            });
        }
        return employees;
    }

    @Override
    public Map<Integer, List<Employee>> getManagerSubourdinateMap() {
        if (managerSubourdinateMap.isEmpty())
            managerSubourdinateMap = employees.stream()
                    .filter(emp -> emp.getManagerId() != null)
                    .collect(Collectors.groupingBy(Employee::getManagerId));
        return managerSubourdinateMap;
    }

    @Override
    public List<Employee> getEmployees() {
        if (employees.isEmpty())
            employees = getEmployeeListFromData(csvReader.parseCsv());
        return employees;
    }

    @Override
    public Optional<Employee> getCeo() {
        return Optional.ofNullable(ceo);
    }




}
