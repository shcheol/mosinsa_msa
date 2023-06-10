package com.mosinsa.customer.dto;

import com.mosinsa.customer.entity.CustomerGrade;
import com.mosinsa.customer.web.controller.response.ResponseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    private String name;

    private List<ResponseOrder> orderList = new ArrayList<>();

}
