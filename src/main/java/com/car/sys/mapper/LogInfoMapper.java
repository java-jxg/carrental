package com.car.sys.mapper;

import com.car.sys.domain.LogInfo;
import com.car.sys.domain.LogInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogInfoMapper {
    int countByExample(LogInfoExample example);

    int deleteByExample(LogInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogInfo record);

    int insertSelective(LogInfo record);

    List<LogInfo> selectByExample(LogInfoExample example);

    LogInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogInfo record, @Param("example") LogInfoExample example);

    int updateByExample(@Param("record") LogInfo record, @Param("example") LogInfoExample example);

    int updateByPrimaryKeySelective(LogInfo record);

    int updateByPrimaryKey(LogInfo record);
}