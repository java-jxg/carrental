package com.car.bus.controller;

import com.car.bus.service.CustomerService;
import com.car.bus.vo.CustomerVo;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customervo){
        return customerService.queryAllCustomer(customervo);
    }
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(CustomerVo customervo){
        try{
            customervo.setCreatetime(new Date());
            customerService.addCustomer(customervo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }
    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(CustomerVo customervo){
        try{
            customerService.updateCustomer(customervo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(CustomerVo customervo){
        try{
            customerService.deleteCustomer(customervo.getIdentity());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }
    @RequestMapping("deleteBatchCustomer")
    public ResultObj deleteBatchCustomer(CustomerVo customervo){
        try{
            customerService.deleteBatchRole(customervo.getIds());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

}
