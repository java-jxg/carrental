package com.car.stat.service;

import com.car.stat.domain.BaseEntity;

import java.util.List;

public interface StatService {
    List<BaseEntity> loadCustomerAreaStatList();

    List<BaseEntity> loadOpernameYearGradeStatList(String year);

    List<Double> loadCompanyYearGradeStatList(String year);

}
