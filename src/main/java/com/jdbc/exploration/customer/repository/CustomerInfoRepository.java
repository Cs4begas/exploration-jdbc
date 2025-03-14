package com.jdbc.exploration.customer.repository;

import com.jdbc.exploration.customer.entity.CustomerInfo;
import com.jdbc.exploration.customer.entity.mapper.CustomerInfoMapExtractor;
import com.jdbc.exploration.customer.entity.mapper.CustomerInfoRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerInfoRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CUSTOMER_SQL =
            "INSERT INTO customer.customer_info (customer_no, id_type, id_no, title_name_th, first_name_th, last_name_th, " +
                    "title_name_en, first_name_en, last_name_en, birth_date, occupation_code, working_place, salary, " +
                    "create_by, update_by) " +
                    "VALUES (:customerNo, :idType, :idNo, :titleNameTh, :firstNameTh, :lastNameTh, " +
                    ":titleNameEn, :firstNameEn, :lastNameEn, :birthDate, :occupationCode, :workingPlace, :salary, " +
                    ":createBy, :updateBy);";

    private static final String FIND_ALL_CUSTOMER_SQL =
            "SELECT ci.customer_no, " +
                    "ci.id_type, ci.id_no, ci.title_name_th, ci.first_name_th, ci.last_name_th, " +
                    "ci.title_name_en, ci.first_name_en, ci.last_name_en, ci.birth_date, ci.occupation_code, " +
                    "ci.working_place, ci.salary, ci.create_by, ci.create_date, ci.update_by, ci.update_date, " +
                    "mt.master_code as master_code_op, mt.master_type as occupation_type ,mt.master_label as occupation_label, " +
                    "ca.address_seq, ca.address_type, ca.address_no, ca.province_code, ca.district, ca.sub_district, ca.postal_code, " +
                    "mt2.master_code as master_code_pro, mt2.master_type as province_type ,mt2.master_label as province_label, " +
                    "cp.phone_seq, cp.phone_type, cp.phone_no " +
                    "FROM customer.customer_info ci " +
                    "LEFT JOIN customer.master_tbl mt ON ci.occupation_code = mt.master_code AND mt.master_type  = 'occupation' " +
                    "LEFT JOIN customer.customer_address ca ON ci.customer_no = ca.customer_no " +
                    "LEFT JOIN customer.master_tbl mt2 ON ca.province_code = mt2.master_code AND mt2.master_type  = 'province' " +
                    "LEFT JOIN customer.customer_phone cp ON ci.customer_no = cp.customer_no " +
                    "ORDER BY ci.birth_date asc;";

    private static final String FIND_PAGE_CUSTOMER_SQL =
            "SELECT ci.customer_no, " +
                    "ci.id_type, ci.id_no, ci.title_name_th, ci.first_name_th, ci.last_name_th, " +
                    "ci.title_name_en, ci.first_name_en, ci.last_name_en, ci.birth_date, ci.occupation_code, " +
                    "ci.working_place, ci.salary, ci.create_by, ci.create_date, ci.update_by, ci.update_date, " +
                    "mt.master_code as occupation_code, mt.master_type as occupation_type ,mt.master_label as occupation_label, " +
                    "ca.address_seq, ca.address_type, ca.address_no, ca.province_code, ca.district, ca.sub_district, ca.postal_code, " +
                    "mt2.master_code as province_code, mt2.master_type as province_type ,mt2.master_label as province_label, " +
                    "cp.phone_seq, cp.phone_type, cp.phone_no " +
                    "FROM customer.customer_info ci " +
                    "LEFT JOIN customer.master_tbl mt ON ci.occupation_code = mt.master_code AND mt.master_type  = 'occupation' " +
                    "LEFT JOIN customer.customer_address ca ON ci.customer_no = ca.customer_no " +
                    "LEFT JOIN customer.master_tbl mt2 ON ca.province_code = mt2.master_code AND mt2.master_type  = 'province' " +
                    "LEFT JOIN customer.customer_phone cp ON ci.customer_no = cp.customer_no ";

    private static final String FIND_TOTAL_COUNT =
            "SELECT count(1) AS row_count " +
                    "FROM customer.customer_info ci ";

    private static final String FIND_CUSTOMER_BY_CUSTOMER_NO_SQL =
            "SELECT ci.customer_no, " +
                    "ci.id_type, ci.id_no, ci.title_name_th, ci.first_name_th, ci.last_name_th, " +
                    "ci.title_name_en, ci.first_name_en, ci.last_name_en, ci.birth_date, ci.occupation_code, " +
                    "ci.working_place, ci.salary, ci.create_by, ci.create_date, ci.update_by, ci.update_date, " +
                    "mt.master_code as master_code_op, mt.master_type as occupation_type ,mt.master_label as occupation_label, " +
                    "ca.address_seq, ca.address_type, ca.address_no, ca.province_code, ca.district, ca.sub_district, ca.postal_code, " +
                    "mt2.master_code as master_code_pro, mt2.master_type as province_type ,mt2.master_label as province_label, " +
                    "cp.phone_seq, cp.phone_type, cp.phone_no " +
                    "FROM customer.customer_info ci " +
                    "LEFT JOIN customer.master_tbl mt ON ci.occupation_code = mt.master_code AND mt.master_type  = 'occupation' " +
                    "LEFT JOIN customer.customer_address ca ON ci.customer_no = ca.customer_no " +
                    "LEFT JOIN customer.master_tbl mt2 ON ca.province_code = mt2.master_code AND mt2.master_type  = 'province' " +
                    "LEFT JOIN customer.customer_phone cp ON ci.customer_no = cp.customer_no " +
                    "WHERE ci.customer_no = :customerNo";

    private static final String UPDATE_CUSTOMER_BASE_SQL
            = "UPDATE customer. customer_info SET ";


    public List<CustomerInfo> findAllCustomerInfos() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        List<CustomerInfo> customerInfoList = namedParameterJdbcTemplate.query(
                FIND_ALL_CUSTOMER_SQL,
                parameters,
                new CustomerInfoMapExtractor()
        );
        if (customerInfoList != null && customerInfoList.isEmpty()) {
            return null;
        }
        return customerInfoList;
    }

    public List<CustomerInfo> findPageCustomerInfos(int page, int size, String sortField, String sortDirection) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("page", page * size);
        parameters.addValue("size", size != 0 ? size : 10);
        parameters.addValue("sortField", sortField == null ? "customer_no" : sortField);
        parameters.addValue("sortDirection", sortDirection == null ? "ASC" : sortDirection);

        String sql = FIND_PAGE_CUSTOMER_SQL + " ORDER BY " + sortField + " " + sortDirection + " LIMIT "+ size + " OFFSET " + page * size;

        List<CustomerInfo> customerInfoList = namedParameterJdbcTemplate.query(
                sql,
                parameters,
                new CustomerInfoRowMapper()
        );
        if (customerInfoList.isEmpty()) {
            return null;
        }
        return customerInfoList;
    }

    public Integer getTotalCount() {
        return namedParameterJdbcTemplate.query(FIND_TOTAL_COUNT, new MapSqlParameterSource(), rs -> {
            if (rs.next()) {
                return rs.getInt("row_count");
            }
            return 0;
        });

    }

    public Integer createCustomerInfo(CustomerInfo customerInfo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("customerNo", customerInfo.getCustomerNo());
        parameters.addValue("idType", customerInfo.getIdType());
        parameters.addValue("idNo", customerInfo.getIdNo());
        parameters.addValue("titleNameTh", customerInfo.getTitleNameTh());
        parameters.addValue("firstNameTh", customerInfo.getFirstNameTh());
        parameters.addValue("lastNameTh", customerInfo.getLastNameTh());
        parameters.addValue("titleNameEn", customerInfo.getTitleNameEn());
        parameters.addValue("firstNameEn", customerInfo.getFirstNameEn());
        parameters.addValue("lastNameEn", customerInfo.getLastNameEn());
        parameters.addValue("birthDate", customerInfo.getBirthDate());
        parameters.addValue("occupationCode", customerInfo.getOccupationCode());
        parameters.addValue("workingPlace", customerInfo.getWorkingPlace());
        parameters.addValue("salary", customerInfo.getSalary());
        parameters.addValue("createBy", customerInfo.getCreateBy());
        parameters.addValue("updateBy", customerInfo.getUpdateBy());
        try {
            return namedParameterJdbcTemplate.update(INSERT_CUSTOMER_SQL, parameters);
        } catch (Exception e) {
            log.error("createCustomerInfo got exception", e);
            throw e;
        }

    }

    public CustomerInfo findByCustomerNo(String customerNo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("customerNo", customerNo);
        List<CustomerInfo> customerInfoList = namedParameterJdbcTemplate.query(
                FIND_CUSTOMER_BY_CUSTOMER_NO_SQL,
                parameters,
                new CustomerInfoMapExtractor()
        );
        return customerInfoList != null && !customerInfoList.isEmpty() ? customerInfoList.get(0) : null;
    }

    public void update(CustomerInfo customerInfo, String staffId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_CUSTOMER_BASE_SQL);

        if (customerInfo.getIdType() != null) {
            sql.append("id_type = :idType, ");
            params.addValue("idType", customerInfo.getIdType());
        }
        if (customerInfo.getIdNo() != null) {
            sql.append("id_no = :idNo, ");
            params.addValue("idNo", customerInfo.getIdNo());
        }
        if (customerInfo.getTitleNameTh() != null) {
            sql.append("title_name_th = :titleNameTh, ");
            params.addValue("titleNameTh", customerInfo.getTitleNameTh());
        }
        if (customerInfo.getFirstNameTh() != null) {
            sql.append("first_name_th = :firstNameTh, ");
            params.addValue("firstNameTh", customerInfo.getFirstNameTh());
        }
        if (customerInfo.getLastNameTh() != null) {
            sql.append("last_name_th = :lastNameTh, ");
            params.addValue("lastNameTh", customerInfo.getLastNameTh());
        }
        if (customerInfo.getTitleNameEn() != null) {
            sql.append("title_name_en = :titleNameEn, ");
            params.addValue("titleNameEn", customerInfo.getTitleNameEn());
        }
        if (customerInfo.getFirstNameEn() != null) {
            sql.append("first_name_en = :firstNameEn, ");
            params.addValue("firstNameEn", customerInfo.getFirstNameEn());
        }
        if (customerInfo.getLastNameEn() != null) {
            sql.append("last_name_en = :lastNameEn, ");
            params.addValue("lastNameEn", customerInfo.getLastNameEn());
        }
        if (customerInfo.getBirthDate() != null) {
            sql.append("birth_date = :birthDate, ");
            params.addValue("birthDate", customerInfo.getBirthDate());
        }
        if (customerInfo.getOccupationCode() != null) {
            sql.append("occupation_code = :occupationCode, ");
            params.addValue("occupationCode", customerInfo.getOccupationCode());
        }
        if (customerInfo.getWorkingPlace() != null) {
            sql.append("working_place = :workingPlace, ");
            params.addValue("workingPlace", customerInfo.getWorkingPlace());
        }
        if (customerInfo.getSalary() != null) {
            sql.append("salary = :salary, ");
            params.addValue("salary", customerInfo.getSalary());
        }


        sql.append("update_by = :updateBy ");
        params.addValue("updateBy", staffId);

        sql.append("WHERE customer_no = :customerNo");
        params.addValue("customerNo", customerInfo.getCustomerNo());

        if (sql.toString().endsWith(", ")) {
            sql.delete(sql.lastIndexOf(", "), sql.length());
        }

        namedParameterJdbcTemplate.update(sql.toString(), params);
    }
}
