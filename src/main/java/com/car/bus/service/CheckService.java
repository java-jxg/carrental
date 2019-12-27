package com.car.bus.service;

import com.car.bus.vo.CheckVo;
import com.car.sys.utils.DataGridView;

import java.util.Map;

public interface CheckService {
    Map<String, Object> initCheckFormData(String rentid);

    void addCheck(CheckVo checkVo);

    DataGridView loadAllCheck(CheckVo checkVo);

    void deleteCheck(String id);

    void deleteBatchCheck(String[] ids);

    void updateCheck(CheckVo checkVo);
}
