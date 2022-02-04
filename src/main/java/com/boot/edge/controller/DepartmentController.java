package com.boot.edge.controller;

import com.boot.edge.model.Employee;
import com.boot.edge.rest.EmployeeClient;
import com.boot.edge.service.DepartmentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DepartmentController {

    Predicate<Customer> java = c -> c.getDept() == Department.JAVA;

    Predicate<Customer> ms = c -> c.getDept()== Department.MS;

    Predicate<Customer> etl = c -> c.getDept()== Department.ETL;

    private final CustomerClient customerClient;

    public DepartmentController(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public Collection<Customer> fallback(){
        return new ArrayList<>();
    }

    /**
    *** we can set method level timeout in properties as weel. check application.properties
    **/
    @Override
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000"),

            @HystrixProperty(name=HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value="5"),
            @HystrixProperty(name=HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value="30")
    })
    
    @GetMapping("/java-employees")
    @CrossOrigin("*")
    public Collection<Customer> getJavaEmployee(){
        return customerClient.findAll().getContent()
                .stream()
                .filter(java)
                .collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/ms-employees")
    @CrossOrigin("*")
    public Collection<Customer> getMSEmployee(){
        return customerClient.findAll().getContent()
                .stream()
                .filter(ms)
                .collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/etl-employees")
    @CrossOrigin("*")
    public Collection<Customer> getETLEmployee(){
        return customerClient.findAll().getContent()
                .stream()
                .filter(etl)
                .collect(Collectors.toList());
    }

}
