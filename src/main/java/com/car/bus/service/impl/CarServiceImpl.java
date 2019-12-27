package com.car.bus.service.impl;

import com.car.bus.domain.Car;
import com.car.bus.domain.CarExample;
import com.car.bus.mapper.CarMapper;
import com.car.bus.service.CarService;
import com.car.bus.vo.CarVo;
import com.car.sys.constast.SysConstast;
import com.car.sys.utils.AppFileUtils;
import com.car.sys.utils.DataGridView;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;


    @Override
    public DataGridView queryAllCar(CarVo carvo) {
        CarExample example = new CarExample();
        CarExample.Criteria criteria = example.createCriteria();
        if(carvo.getCarnumber()!=null){
            criteria.andCarnumberLike("%"+carvo.getCarnumber()+"%");
        }
        if(carvo.getCartype()!=null){
            criteria.andCartypeLike("%"+carvo.getCartype()+"%");
        }
        if(carvo.getColor()!=null){
            criteria.andColorLike("%"+carvo.getColor()+"%");
        }
        if(carvo.getDescription()!=null){
            criteria.andDescriptionLike("%"+carvo.getDescription()+"%");
        }
        if(carvo.getIsrenting()!=null) {
            criteria.andIsrentingEqualTo(carvo.getIsrenting());
        }
        Page<Object> page = PageHelper.startPage(carvo.getPage(),carvo.getLimit());
        List<Car> cars = carMapper.selectByExample(example);
        return new DataGridView(page.getTotal(),cars);
    }

    @Override
    public void addCar(CarVo carvo) {
        carMapper.insert(carvo);
    }

    @Override
    public void updateCar(CarVo carvo) {
        Car car = carMapper.selectByPrimaryKey(carvo.getCarnumber());
        carvo.setCreatetime(car.getCreatetime());
        carMapper.updateByPrimaryKey(carvo);
    }


    @Override
    public void deleteCar(String carid) {
        Car car = carMapper.selectByPrimaryKey(carid);
        if(!car.getCarimg().equals(SysConstast.DEFAULT_CAR_IMG)){
            AppFileUtils.removeFileByPath(car.getCarimg());
        }
        carMapper.deleteByPrimaryKey(carid);
    }

    @Override
    public void deleteBatchRole(String[] ids) {
        for (String id:ids){
            deleteCar(id);
        }
    }

    @Override
    public Car queryCarByCarNumber(String carnumber) {
        return carMapper.selectByPrimaryKey(carnumber);

    }
}
