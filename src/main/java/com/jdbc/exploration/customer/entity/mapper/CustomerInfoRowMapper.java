package com.jdbc.exploration.customer.entity.mapper;

import com.jdbc.exploration.customer.entity.CustomerAddress;
import com.jdbc.exploration.customer.entity.CustomerInfo;
import com.jdbc.exploration.customer.entity.CustomerPhone;
import com.jdbc.exploration.customer.entity.MasterTBL;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerInfoRowMapper implements RowMapper<CustomerInfo> {
    @Override
    public CustomerInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerNo(rs.getString("customer_no"));
        customerInfo.setIdType(rs.getString("id_type"));
        customerInfo.setIdNo(rs.getString("id_no"));
        customerInfo.setTitleNameTh(rs.getString("title_name_th"));
        customerInfo.setFirstNameTh(rs.getString("first_name_th"));
        customerInfo.setLastNameTh(rs.getString("last_name_th"));
        customerInfo.setTitleNameEn(rs.getString("title_name_en"));
        customerInfo.setFirstNameEn(rs.getString("first_name_en"));
        customerInfo.setLastNameEn(rs.getString("last_name_en"));
        customerInfo.setBirthDate(rs.getDate("birth_date").toLocalDate());
        customerInfo.setOccupationCode(rs.getString("occupation_code"));
        customerInfo.setWorkingPlace(rs.getString("working_place"));
        customerInfo.setSalary(rs.getBigDecimal("salary"));
        customerInfo.setCreateBy(rs.getString("create_by"));
        customerInfo.setCreateDate(rs.getTimestamp("create_date") != null ? rs.getTimestamp("create_date").toLocalDateTime() : null);
        customerInfo.setUpdateBy(rs.getString("update_by"));
        customerInfo.setUpdateDate(rs.getTimestamp("update_date") != null ? rs.getTimestamp("update_date").toLocalDateTime() : null);

        MasterTBL masterTBL = new MasterTBL();
        masterTBL.setMasterCode(rs.getString("occupation_code"));
        masterTBL.setMasterType(rs.getString("occupation_type"));
        masterTBL.setMasterLabel(rs.getString("occupation_label"));
        customerInfo.setOccupationDetail(masterTBL);

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setAddressSeq(rs.getInt("address_seq"));
        customerAddress.setAddressType(rs.getString("address_type"));
        customerAddress.setAddressNo(rs.getString("address_no"));
        customerAddress.setProvinceCode(rs.getString("province_code"));
        customerAddress.setDistrict(rs.getString("district"));
        customerAddress.setSubDistrict(rs.getString("sub_district"));
        customerAddress.setPostalCode(rs.getString("postal_code"));

//        MasterTBL provinceDetail = new MasterTBL();
//        provinceDetail.setMasterCode(rs.getString("province_code"));
//        provinceDetail.setMasterType(rs.getString("province_type"));
//        provinceDetail.setMasterLabel(rs.getString("province_label"));
//        customerAddress.setProvinceDetail(provinceDetail);
//        customerInfo.setCustomerAddressList(customerAddress);

//        CustomerPhone customerPhone = new CustomerPhone();
//        customerPhone.setPhoneSeq(rs.getInt("phone_seq"));
//        customerPhone.setPhoneType(rs.getString("phone_type"));
//        customerPhone.setPhoneNo(rs.getString("phone_no"));
//        customerInfo.setCustomerPhone(customerPhone);

        return customerInfo;
    }
}