package com.jdbc.exploration.customer.entity.mapper;

import com.jdbc.exploration.customer.entity.CustomerAddress;
import com.jdbc.exploration.customer.entity.CustomerInfo;
import com.jdbc.exploration.customer.entity.CustomerPhone;
import com.jdbc.exploration.customer.entity.MasterTBL;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomerInfoMapExtractor implements ResultSetExtractor<List<CustomerInfo>> {
    @Override
    public List<CustomerInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, CustomerInfo> customerInfoHashMap = new HashMap<>();
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        Set<Integer> customerAddressSet = new HashSet<>();
        Set<Integer> customerPhoneSet = new HashSet<>();

        while (rs.next()) {
            String customerNo = rs.getString("customer_no");
            CustomerInfo customerInfo = customerInfoHashMap.get(customerNo);
            if (customerInfo == null) {
                customerInfo = new CustomerInfo();
                customerInfo.setCustomerNo(customerNo);
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

                if(rs.getString("master_code_op") != null){
                    MasterTBL masterTBL = new MasterTBL();
                    masterTBL.setMasterCode(rs.getString("master_code_op"));
                    masterTBL.setMasterType(rs.getString("occupation_type"));
                    masterTBL.setMasterLabel(rs.getString("occupation_label"));
                    customerInfo.setOccupationDetail(masterTBL);
                }

                List<CustomerAddress> customerAddressList = new ArrayList<>();
                CustomerAddress customerAddress = mapCustomerAddress(rs);
                if(customerAddress != null){
                    customerAddressList.add(customerAddress);
                    customerAddressSet.add(customerAddress.getAddressSeq());
                    customerInfo.setCustomerAddressList(customerAddressList);

                }
                List<CustomerPhone> customerPhoneList = new ArrayList<>();
                CustomerPhone customerPhone = mapCustomerPhones(rs);
                if(customerPhone != null){
                    customerPhoneList.add(customerPhone);
                    customerPhoneSet.add(customerPhone.getPhoneSeq());
                    customerInfo.setCustomerPhoneList(customerPhoneList);
                }

                customerInfoHashMap.put(customerNo, customerInfo);
                customerInfoList.add(customerInfo);
            } else {
                CustomerAddress customerAddress = mapCustomerAddress(rs);
                if (!customerAddressSet.contains(customerAddress.getAddressSeq())) {
                    customerInfo.getCustomerAddressList().add(customerAddress);
                    customerAddressSet.add(customerAddress.getAddressSeq());
                }

                CustomerPhone customerPhone = mapCustomerPhones(rs);
                if (!customerPhoneSet.contains(customerPhone.getPhoneSeq())) {
                    customerInfo.getCustomerPhoneList().add(customerPhone);
                    customerPhoneSet.add(customerPhone.getPhoneSeq());
                }
            }
        }
        return customerInfoList;

    }

    private static CustomerAddress mapCustomerAddress(ResultSet rs) throws SQLException {
        CustomerAddress customerAddress = new CustomerAddress();
        if(rs.getInt("address_seq") == 0){
            return null;
        }
        customerAddress.setAddressSeq(rs.getInt("address_seq"));
        customerAddress.setAddressType(rs.getString("address_type"));
        customerAddress.setAddressNo(rs.getString("address_no"));
        customerAddress.setProvinceCode(rs.getString("province_code"));
        customerAddress.setDistrict(rs.getString("district"));
        customerAddress.setSubDistrict(rs.getString("sub_district"));
        customerAddress.setPostalCode(rs.getString("postal_code"));

        if(rs.getString("master_code_pro") != null){
            MasterTBL provinceDetail = new MasterTBL();
            provinceDetail.setMasterCode(rs.getString("master_code_pro"));
            provinceDetail.setMasterType(rs.getString("province_type"));
            provinceDetail.setMasterLabel(rs.getString("province_label"));
            customerAddress.setProvinceDetail(provinceDetail);
        }
        return customerAddress;
    }

    private static CustomerPhone mapCustomerPhones(ResultSet rs) throws SQLException {
        if(rs.getInt("phone_seq") == 0){
            return null;
        }
        CustomerPhone customerPhone = new CustomerPhone();
        customerPhone.setPhoneSeq(rs.getInt("phone_seq"));
        customerPhone.setPhoneType(rs.getString("phone_type"));
        customerPhone.setPhoneNo(rs.getString("phone_no"));
        return customerPhone;
    }
}
