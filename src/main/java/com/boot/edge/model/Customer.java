package com.boot.edge.model;

import com.boot.edge.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
//    private Long id;
    private String name;

    public Department getDept() {
        return dept;
    }

    private Department dept;

    public String getName(){
        return name;
    }


}
