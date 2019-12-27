package com.car.bus.service.impl;

import com.car.bus.domain.*;
import com.car.bus.mapper.CarMapper;
import com.car.bus.mapper.CheckMapper;
import com.car.bus.mapper.CustomerMapper;
import com.car.bus.mapper.RentMapper;
import com.car.bus.service.CheckService;
import com.car.bus.vo.CheckVo;
import com.car.sys.constast.SysConstast;
import com.car.sys.domain.User;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.RandomUtils;
import com.car.sys.utils.WebUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private RentMapper rentMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CheckMapper checkMapper;

    @Override
    public Map<String, Object> initCheckFormData(String rentid) {
        Rent rent = rentMapper.selectByPrimaryKey(rentid);
        Customer customer = customerMapper.selectByPrimaryKey(rent.getIdentity());
        Car car = carMapper.selectByPrimaryKey(rent.getCarnumber());
        Check check = new Check();
        check.setCheckid(RandomUtils.createRandomStringUseTime(SysConstast.CAR_ORDER_JC));
        check.setRentid(rentid);
        check.setCheckdate(new Date());
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        check.setOpername(user.getRealname());
        Map<String,Object> map = new HashMap<>();
        map.put("rent",rent);
        map.put("customer",customer);
        map.put("car",car);
        map.put("check",check);

        return map;
    }

    @Override
    public void addCheck(CheckVo checkVo) {
        //更改出租单的状态
        Rent rent = rentMapper.selectByPrimaryKey(checkVo.getRentid());
        rent.setRentflag(SysConstast.RENT_BACK_TRUE);
        rentMapper.updateByPrimaryKey(rent);
        //更改汽车的状态
        Car car = carMapper.selectByPrimaryKey(rent.getCarnumber());
        car.setIsrenting(SysConstast.RENT_CAR_FALSE);
        carMapper.updateByPrimaryKey(car);
        checkMapper.insert(checkVo);
    }

    @Override
    public DataGridView loadAllCheck(CheckVo checkVo) {
        CheckExample example = new CheckExample();
        CheckExample.Criteria criteria = example.createCriteria();
        if(checkVo.getCheckid()!=null){
            criteria.andCheckidLike("%"+checkVo.getCheckid()+"%");
        }
        if(checkVo.getCheckdesc()!=null){
            criteria.andCheckdescLike("%"+checkVo.getCheckdesc()+"%");
        }
        if(checkVo.getProblem()!=null){
            criteria.andProblemLike("%"+checkVo.getProblem()+"%");
        }
        if(checkVo.getRentid()!=null){
            criteria.andRentidLike("%"+checkVo.getRentid()+"%");
        }
        if(checkVo.getStartTime()!=null){
            criteria.andCheckdateGreaterThanOrEqualTo(checkVo.getStartTime());
        }
        if(checkVo.getEndTime()!=null){
            criteria.andCheckdateLessThanOrEqualTo(checkVo.getEndTime());
        }

        Page<Object> page = PageHelper.startPage(checkVo.getPage(),checkVo.getLimit());
        List<Check> checks = checkMapper.selectByExample(example);
        return new DataGridView(page.getTotal(),checks);
    }

    @Override
    public void deleteCheck(String id) {
        checkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatchCheck(String[] ids) {
        for (String id:ids) {
            deleteCheck(id);
        }
    }

    @Override
    public void updateCheck(CheckVo checkVo) {
        Check check = checkMapper.selectByPrimaryKey(checkVo.getCheckid());
        checkVo.setCreatetime(check.getCreatetime());
        checkMapper.updateByPrimaryKey(checkVo);
    }
}
