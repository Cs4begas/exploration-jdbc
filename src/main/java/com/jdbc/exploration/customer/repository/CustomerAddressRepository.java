package com.jdbc.exploration.customer.repository;

import com.jdbc.exploration.customer.entity.CustomerAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerAddressRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CUSTOMER_ADDRESS_SQL =
            "INSERT INTO customer.customer_address (customer_no, address_type, address_no, province_code, district, sub_district, postal_code) " +
                    "VALUES (:customerNo, :addressType, :addressNo, :provinceCode, :district, :subDistrict, :postalCode)";

    private static final String UPDATE_CUSTOMER_ADDRESS_BASE_SQL =
            "UPDATE customer.customer_address SET ";

    public void createAll(List<CustomerAddress> customerAddresses, String customerNo) {
        MapSqlParameterSource[] batch = customerAddresses.stream()
                .map(address -> new MapSqlParameterSource()
                        .addValue("customerNo", customerNo)
                        .addValue("addressType", address.getAddressType())
                        .addValue("addressNo", address.getAddressNo())
                        .addValue("moo", address.getMoo())
                        .addValue("buildingVillage", address.getBuildingVillage())
                        .addValue("floor", address.getFloor())
                        .addValue("roomNo", address.getRoomNo())
                        .addValue("soi", address.getSoi())
                        .addValue("road", address.getRoad())
                        .addValue("subDistrict", address.getSubDistrict())
                        .addValue("district", address.getDistrict())
                        .addValue("provinceCode", address.getProvinceCode())
                        .addValue("postalCode", address.getPostalCode())
                        .addValue("createBy", address.getCreateBy())
                        .addValue("createDate", address.getCreateDate())
                        .addValue("updateBy", address.getUpdateBy()))
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(INSERT_CUSTOMER_ADDRESS_SQL, batch);
    }

    public void update(List<CustomerAddress> addresses, String customerNo) {
        addresses.forEach(address -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            List<String> setClauses = new ArrayList<>();


            addClauseIfNotNull(setClauses, params, address.getAddressType(), "address_type", "addressType");
            addClauseIfNotNull(setClauses, params, address.getAddressNo(), "address_no", "addressNo");
            addClauseIfNotNull(setClauses, params, address.getProvinceCode(), "province_code", "provinceCode");
            addClauseIfNotNull(setClauses, params, address.getDistrict(), "district", "district");
            addClauseIfNotNull(setClauses, params, address.getSubDistrict(), "sub_district", "subDistrict");
            addClauseIfNotNull(setClauses, params, address.getPostalCode(), "postal_code", "postalCode");
            addClauseIfNotNull(setClauses, params, address.getMoo(), "moo", "moo");
            addClauseIfNotNull(setClauses, params, address.getBuildingVillage(), "building_village", "buildingVillage");
            addClauseIfNotNull(setClauses, params, address.getFloor(), "floor", "floor");
            addClauseIfNotNull(setClauses, params, address.getRoomNo(), "room_no", "roomNo");
            addClauseIfNotNull(setClauses, params, address.getSoi(), "soi", "soi");
            addClauseIfNotNull(setClauses, params, address.getRoad(), "road", "road");

            setClauses.add("update_by = :updateBy");
            params.addValue("updateBy", address.getUpdateBy());
            params.addValue("customerNo", customerNo);

            if (!setClauses.isEmpty()) {
                String sql = UPDATE_CUSTOMER_ADDRESS_BASE_SQL
                        + String.join(", ", setClauses)
                        + " WHERE customer_no = :customerNo";

                namedParameterJdbcTemplate.update(sql, params);
            }
        });
    }

    // Helper method for conditional parameter handling
    private void addClauseIfNotNull(List<String> clauses,
                                    MapSqlParameterSource params,
                                    Object value,
                                    String columnName,
                                    String paramName) {
        if (value != null) {
            clauses.add(columnName + " = :" + paramName);
            params.addValue(paramName, value);
        }
    }

    private MapSqlParameterSource mapAddressesToSql(CustomerAddress address, String customerNo) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("addressType", address.getAddressType());
        params.addValue("addressNo", address.getAddressNo());
        params.addValue("provinceCode", address.getProvinceCode());
        params.addValue("district", address.getDistrict());
        params.addValue("subDistrict", address.getSubDistrict());
        params.addValue("postalCode", address.getPostalCode());
        params.addValue("moo", address.getMoo());
        params.addValue("buildingVillage", address.getBuildingVillage());
        params.addValue("floor", address.getFloor());
        params.addValue("roomNo", address.getRoomNo());
        params.addValue("soi", address.getSoi());
        params.addValue("road", address.getRoad());

        params.addValue("updateBy", address.getUpdateBy());
        if (customerNo != null && !customerNo.isBlank()) {
            params.addValue("customerNo", customerNo);
        }
        return params;
    }
}
