package com.car.bus.service;

import com.car.bus.domain.Rent;
import com.car.bus.vo.RentVo;
import com.car.sys.utils.DataGridView;

public interface RentService {
    void addRent(RentVo rentVo);

    DataGridView queryAllRent(RentVo rentVo);

    void updateRent(RentVo rentVo);

    void deleteRent(RentVo rentVo);

    Rent queryRentByRentId(String rentid);
}
