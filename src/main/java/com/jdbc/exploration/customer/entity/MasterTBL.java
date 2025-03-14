package com.jdbc.exploration.customer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "master_tbl")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterTBL {
    @Id
    private Integer id;

    private String masterType;

    private String masterCode;

    private String masterLabel;
}
