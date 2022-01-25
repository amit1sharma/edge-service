package com.boot.edge.controller;

import com.boot.edge.enums.Department;
import com.boot.edge.fiegn.CustomerClient;
import com.boot.edge.model.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
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
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    })
    /*@HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "4000")
    })*/
    
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
