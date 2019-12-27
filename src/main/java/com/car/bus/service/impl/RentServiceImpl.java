package com.car.bus.service.impl;

import com.car.bus.domain.Car;
import com.car.bus.domain.Rent;
import com.car.bus.domain.RentExample;
import com.car.bus.mapper.CarMapper;
import com.car.bus.mapper.RentMapper;
import com.car.bus.service.RentService;
import com.car.bus.vo.RentVo;
import com.car.sys.constast.SysConstast;
import com.car.sys.utils.DataGridView;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentServiceImpl implements RentService {
    @Autowired
    private RentMapper rentMapper;
    @Autowired
    private CarMapper carMapper;

    @Override
    public void addRent(RentVo rentVo) {
        Car car = carMapper.selectByPrimaryKey(rentVo.getCarnumber());
        car.setIsrenting(SysConstast.RENT_CAR_TRUE);
        carMapper.updateByPrimaryKey(car);
        rentMapper.insert(rentVo);
    }

    @Override
    public DataGridView queryAllRent(RentVo rentVo) {
        RentExample example = new RentExample();
        RentExample.Criteria criteria = example.createCriteria();
        if(rentVo.getRentid()!=null){
            criteria.andRentidLike("%"+rentVo.getRentid()+"%");
        }
        if(rentVo.getIdentity()!=null){
            criteria.andIdentityLike("%"+rentVo.getIdentity()+"%");
        }
        if(rentVo.getCarnumber()!=null){
            criteria.andCarnumberLike("%"+rentVo.getCarnumber()+"%");
        }
        if(rentVo.getStartTime()!=null){
            criteria.andBegindateGreaterThanOrEqualTo(rentVo.getStartTime());
        }
        if(rentVo.getReturndate()!=null){
            criteria.andReturndateLessThanOrEqualTo(rentVo.getReturndate());
        }
        if(rentVo.getRentflag()!=null){
            criteria.andRentflagEqualTo(rentVo.getRentflag());
        }
        Page<Object> page = PageHelper.startPage(rentVo.getPage(),rentVo.getLimit());
        List<Rent> rents = rentMapper.selectByExample(example);
        return new DataGridView(page.getTotal(),rents);
    }

    @Override
    public void updateRent(RentVo rentVo) {
        Rent rent = rentMapper.selectByPrimaryKey(rentVo.getRentid());
        rentVo.setCreatetime(rent.getCreatetime());
        rentVo.setRentflag(rent.getRentflag());
        rentMapper.updateByPrimaryKey(rentVo);
    }

    @Override
    public void deleteRent(RentVo rentVo) {
        Rent rent = rentMapper.selectByPrimaryKey(rentVo.getRentid());
        Car car = carMapper.selectByPrimaryKey(rent.getCarnumber());
        car.setIsrenting(SysConstast.RENT_CAR_FALSE);
        carMapper.updateByPrimaryKey(car);
        rentMapper.deleteByPrimaryKey(rentVo.getRentid());
    }

    @Override
    public Rent queryRentByRentId(String rentid) {
        Rent rent = rentMapper.selectByPrimaryKey(rentid);
        return rent;
    }
}
