package com.jdbc.exploration.customer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "customer_phone")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerPhone {
    @Id
    private Integer phoneSeq;

    private String phoneType;

    private String phoneNo;

    private String createBy;

    private LocalDateTime createDate;

    private String updateBy;

    private LocalDateTime updateDate;
}
