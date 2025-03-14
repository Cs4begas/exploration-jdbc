package com.jdbc.exploration.customer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table("customer_address")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerAddress implements Serializable
{
    @Id
    private Integer addressSeq;

    private String customerNo;

    private String addressType;

    private String addressNo;

    private String moo;

    private String buildingVillage;

    private String floor;

    private String roomNo;

    private String soi;

    private String road;

    private String subDistrict;

    private String district;

    private String postalCode;

    private String createBy;

    private LocalDateTime createDate;

    private String updateBy;

    private LocalDateTime updateDate;

    private String provinceCode;

    private MasterTBL provinceDetail;
}
