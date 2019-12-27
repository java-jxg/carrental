package com.car.bus.controller;

import com.car.bus.domain.Car;
import com.car.bus.domain.CarExample;
import com.car.bus.service.CarService;
import com.car.bus.vo.CarVo;
import com.car.sys.constast.SysConstast;
import com.car.sys.utils.AppFileUtils;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("car")
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping("loadAllCar")
    public DataGridView loadAllCar(CarVo carvo){
        return carService.queryAllCar(carvo);
    }
    @RequestMapping("addCar")
    public ResultObj addCar(CarVo carvo){
        try{
            carvo.setCreatetime(new Date());
            if(!carvo.getCarimg().equals(SysConstast.DEFAULT_CAR_IMG)){
                String filePath = AppFileUtils.updateFileName(carvo.getCarimg(), SysConstast.FILE_UPLOAD_TEMP);
                carvo.setCarimg(filePath);
            }
            carService.addCar(carvo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }
    @RequestMapping("updateCar")
    public ResultObj updateCar(CarVo carvo){
        try{
            String carimg = carvo.getCarimg();
            if (carimg.endsWith(SysConstast.FILE_UPLOAD_TEMP)) {
                String filePath =AppFileUtils.updateFileName(carvo.getCarimg(), SysConstast.FILE_UPLOAD_TEMP);
                carvo.setCarimg(filePath);
                //把原来的删除
                Car car = this.carService.queryCarByCarNumber(carvo.getCarnumber());
                AppFileUtils.removeFileByPath(car.getCarimg());
            }
            carService.updateCar(carvo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("deleteCar")
    public ResultObj deleteCar(CarVo carvo){
        try{
            carService.deleteCar(carvo.getCarnumber());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }
    @RequestMapping("deleteBatchCar")
    public ResultObj deleteBatchCar(CarVo carvo){
        try{
            carService.deleteBatchRole(carvo.getIds());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

}
