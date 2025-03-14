package com.jdbc.exploration;

import com.jdbc.exploration.customer.entity.CustomerInfo;
import com.jdbc.exploration.customer.repository.CustomerAddressRepository;
import com.jdbc.exploration.customer.repository.CustomerInfoRepository;
import com.jdbc.exploration.customer.repository.CustomerPhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerInfoRepository customerInfoRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerPhoneRepository customerPhoneRepository;

    public List<CustomerInfo> findAllCustomerInfos(){
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAllCustomerInfos();
        return customerInfoList;
    }

    public Page<CustomerInfo> findPageCustomerInfos(int page, int size, String sortField, String sortDirection){
        List<CustomerInfo> customerInfoList = customerInfoRepository.findPageCustomerInfos(page, size, sortField, sortDirection);
        Integer countRows = customerInfoRepository.getTotalCount();

        PageImpl<CustomerInfo> pageResult = new PageImpl<>(customerInfoList, PageRequest.of(page, size), countRows);

        return pageResult;
    }

    @Transactional
    public CustomerInfo createCustomerInfo(CustomerInfo customerInfo){
         customerInfoRepository.createCustomerInfo(customerInfo);
         customerAddressRepository.createAll(customerInfo.getCustomerAddressList(), customerInfo.getCustomerNo());
         customerPhoneRepository.createAll(customerInfo.getCustomerPhoneList(), customerInfo.getCustomerNo());

         CustomerInfo customerInfoResult = customerInfoRepository.findByCustomerNo(customerInfo.getCustomerNo());

         return customerInfoResult;
    }

    @Transactional
    public CustomerInfo updateCustomerInfo(CustomerInfo customerInfo, String staffId){
        customerInfoRepository.update(customerInfo, staffId);
        customerAddressRepository.update(customerInfo.getCustomerAddressList(), customerInfo.getCustomerNo());

        CustomerInfo customerInfoResult = customerInfoRepository.findByCustomerNo(customerInfo.getCustomerNo());

        return customerInfoResult;
    }
}
