package com.car.sys.controller;

import com.car.sys.service.LogInfoService;
import com.car.sys.utils.DataGridView;
import com.car.sys.utils.ResultObj;
import com.car.sys.vo.LogInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping("loadAllLogInfo")
    public DataGridView loadAllLogInfo(LogInfoVo logInfoVo){
        return logInfoService.queryAllLogInfo(logInfoVo);
    }
    /**
     * 删除一条日志
     * @param logInfoVo
     * @return
     */
    @RequestMapping("deleteLogInfo")
    public ResultObj deleteLogInfo(LogInfoVo logInfoVo){
        try {
            logInfoService.deleteLogInfo(logInfoVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 批量删除日志
     * @param logInfoVo
     * @return
     */
    @RequestMapping("deleteBatchLogInfo")
    public ResultObj deleteBatchLogInfo(LogInfoVo logInfoVo){
        try {
            logInfoService.deleteBatchLogInfo(logInfoVo.getIds());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}
