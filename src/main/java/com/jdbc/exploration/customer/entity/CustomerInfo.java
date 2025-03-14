package com.jdbc.exploration.customer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table("customer_info")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerInfo implements Serializable {
    @Id
    private String customerNo;

    private String idType;

    private String idNo;

    private String titleNameTh;

    private String firstNameTh;

    private String lastNameTh;

    private String titleNameEn;

    private String firstNameEn;

    private String lastNameEn;

    private String occupationCode;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private String workingPlace;

    private BigDecimal salary;

    private String createBy;

    private LocalDateTime createDate;

    private String updateBy;

    private LocalDateTime updateDate;

    private MasterTBL occupationDetail;

    @JsonProperty("addresses")
    private List<CustomerAddress> customerAddressList;

    @JsonProperty("phones")
    private List<CustomerPhone> customerPhoneList;
}
