package com.jdbc.exploration.customer.repository;

import com.jdbc.exploration.customer.entity.CustomerPhone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerPhoneRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CUSTOMER_PHONE_SQL =
            "INSERT INTO customer.customer_phone (customer_no, phone_type, phone_no) " +
                    "VALUES (:customerNo, :phoneType, :phoneNo)";

    public void createAll(List<CustomerPhone> customerPhones, String customerNo) {
        MapSqlParameterSource[] batch = customerPhones.stream()
                .map(phone -> new MapSqlParameterSource()
                        .addValue("phoneSeq", phone.getPhoneSeq())
                        .addValue("customerNo", customerNo)
                        .addValue("phoneType", phone.getPhoneType())
                        .addValue("phoneNo", phone.getPhoneNo()))
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(INSERT_CUSTOMER_PHONE_SQL, batch);
    }
}
