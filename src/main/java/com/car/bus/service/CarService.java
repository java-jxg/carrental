package com.car.bus.service;

import com.car.bus.domain.Car;
import com.car.bus.vo.CarVo;
import com.car.sys.utils.DataGridView;

public interface CarService {

    DataGridView queryAllCar(CarVo carvo);

    void addCar(CarVo carvo);

    void updateCar(CarVo carvo);

    void deleteCar(String carid);

    void deleteBatchRole(String[] ids);

    Car queryCarByCarNumber(String carnumber);

}
