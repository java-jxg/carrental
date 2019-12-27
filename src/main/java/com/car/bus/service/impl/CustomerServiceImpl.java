package com.car.bus.service.impl;

import com.car.bus.domain.Customer;
import com.car.bus.domain.CustomerExample;
import com.car.bus.mapper.CustomerMapper;
import com.car.bus.service.CustomerService;
import com.car.bus.vo.CustomerVo;
import com.car.sys.utils.DataGridView;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public DataGridView queryAllCustomer(CustomerVo customervo) {
        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();
        if(customervo.getCustname()!=null){
            criteria.andCustnameLike("%"+customervo.getCustname()+"%");
        }
        if(customervo.getIdentity()!=null){
            criteria.andIdentityLike("%"+customervo.getIdentity()+"%");
        }
        if(customervo.getAddress()!=null){
            criteria.andAddressLike("%"+customervo.getAddress()+"%");
        }
        if(customervo.getPhone()!=null){
            criteria.andPhoneLike("%"+customervo.getPhone()+"%");
        }
        if(customervo.getCareer()!=null){
            criteria.andCareerLike("%"+customervo.getCareer()+"%");
        }
        if(customervo.getSex()!=null){
            criteria.andSexEqualTo(customervo.getSex());
        }

        Page<Object> page = PageHelper.startPage(customervo.getPage(),customervo.getLimit());
        List<Customer> customers = customerMapper.selectByExample(example);
        return new DataGridView(page.getTotal(),customers);
    }

    @Override
    public void addCustomer(CustomerVo customervo) {
        customerMapper.insert(customervo);
    }

    @Override
    public void updateCustomer(CustomerVo customervo) {
        Customer customer = customerMapper.selectByPrimaryKey(customervo.getIdentity());
        customervo.setCreatetime(customer.getCreatetime());
        customerMapper.updateByPrimaryKey(customervo);
    }


    @Override
    public void deleteCustomer(String customerid) {
        customerMapper.deleteByPrimaryKey(customerid);
    }

    @Override
    public void deleteBatchRole(String[] ids) {
        for (String id:ids){
            deleteCustomer(id);
        }
    }

    @Override
    public Customer queryCustomerByIdentity(String identity) {
        return customerMapper.selectByPrimaryKey(identity);
    }
}
