package com.boot.edge.fiegn;

import com.boot.edge.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("${customer-service-name}")
public interface CustomerClient {

    @GetMapping("/customers")
    CollectionModel<Customer> findAll();
}
