package com.car.sys.service.impl;

import com.car.sys.domain.LogInfo;
import com.car.sys.domain.LogInfoExample;
import com.car.sys.mapper.LogInfoMapper;
import com.car.sys.service.LogInfoService;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.LogInfoVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogInfoServiceImpl implements LogInfoService {
    @Autowired
    private LogInfoMapper logInfoMapper;
    @Override
    public DataGridView queryAllLogInfo(LogInfoVo logInfoVo) {
        LogInfoExample logInfoExample = new LogInfoExample();
        LogInfoExample.Criteria criteria = logInfoExample.createCriteria();
        if(logInfoVo.getLoginname()!=null && logInfoVo.getLoginname()!=""){
            criteria.andLoginnameLike("%"+logInfoVo.getLoginname()+"%");
        }
        if(logInfoVo.getLoginip()!=null && logInfoVo.getLoginip()!=""){
            criteria.andLoginipEqualTo(logInfoVo.getLoginip());
        }
        if(logInfoVo.getStartTime()!=null){
            criteria.andLogintimeGreaterThanOrEqualTo(logInfoVo.getStartTime());
        }
        if(logInfoVo.getEndTime()!=null){
            criteria.andLogintimeLessThanOrEqualTo(logInfoVo.getEndTime());
        }
        Page<Object> page = PageHelper.startPage(logInfoVo.getPage(),logInfoVo.getLimit());
        List<LogInfo> logInfos = logInfoMapper.selectByExample(logInfoExample);
        return new DataGridView(page.getTotal(),logInfos);
    }

    @Override
    public void addLogInfo(LogInfoVo logInfoVo) {
        logInfoMapper.insert(logInfoVo);
    }

    @Override
    public void deleteLogInfo(Integer logInfoid) {
        logInfoMapper.deleteByPrimaryKey(logInfoid);
    }

    @Override
    public void deleteBatchLogInfo(Integer[] ids) {
        for(Integer id:ids){
            deleteLogInfo(id);
        }
    }
}
