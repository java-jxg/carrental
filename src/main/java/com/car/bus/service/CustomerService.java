package com.car.bus.service;

import com.car.bus.domain.Customer;
import com.car.bus.vo.CustomerVo;
import com.car.sys.utils.DataGridView;

public interface CustomerService {

    DataGridView queryAllCustomer(CustomerVo customervo);

    void addCustomer(CustomerVo customervo);

    void updateCustomer(CustomerVo customervo);

    void deleteCustomer(String customerid);

    void deleteBatchRole(String[] ids);

    Customer queryCustomerByIdentity(String identity);

}
