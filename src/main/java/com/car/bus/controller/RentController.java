package com.car.bus.controller;

import com.car.bus.domain.Customer;
import com.car.bus.domain.Rent;
import com.car.bus.service.CustomerService;
import com.car.bus.service.RentService;
import com.car.bus.vo.CarVo;
import com.car.bus.vo.CustomerVo;
import com.car.bus.vo.RentVo;
import com.car.sys.constast.SysConstast;
import com.car.sys.domain.User;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.RandomUtils;
import com.car.sys.utils.ResultObj;
import com.car.sys.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("rent")
public class RentController {
    @Autowired
    private RentService rentService;
    @Autowired
    private CustomerService customerService;

    /**
     * 检查身份证号是否存在
     * @param rentVo
     * @return
     */
    @RequestMapping("checkCustomerExist")
    public ResultObj checkCustomerExist(RentVo rentVo){
        Customer customer = customerService.queryCustomerByIdentity(rentVo.getIdentity());
        if(customer!=null){
            return ResultObj.STATUS_TRUE;
        }else{
            return ResultObj.STATUS_FALSE;
        }
    }
    //初始化添加出租单的表单的数据
    @RequestMapping("initRentFrom")
    public RentVo initRentFrom(RentVo rentVo){
        //生成出租单
        rentVo.setRentid(RandomUtils.createRandomStringUseTime(SysConstast.CAR_ORDER_CZ));
        //设置起租时间
        rentVo.setBegindate(new Date());
        //设置操作员
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        rentVo.setOpername(user.getRealname());
        return rentVo;
    }

    //保存出租单
    @RequestMapping("saveRent")
    public ResultObj saveRent(RentVo rentVo){
        try {
            rentVo.setCreatetime(new Date());
            rentVo.setRentflag(SysConstast.RENT_BACK_FALSE);
            rentService.addRent(rentVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    @RequestMapping("loadAllRent")
    public DataGridView loadAllRent(RentVo rentVo){
        return rentService.queryAllRent(rentVo);
    }
    @RequestMapping("updateRent")
    public ResultObj updateRent(RentVo rentVo){
        try{
            rentService.updateRent(rentVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("deleteRent")
    public ResultObj deleteRent(RentVo rentVo){
        try{
            rentService.deleteRent(rentVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

}
