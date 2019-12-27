package com.car.bus.mapper;

import com.car.bus.domain.Rent;
import com.car.bus.domain.RentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RentMapper {
    int countByExample(RentExample example);

    int deleteByExample(RentExample example);

    int deleteByPrimaryKey(String rentid);

    int insert(Rent record);

    int insertSelective(Rent record);

    List<Rent> selectByExample(RentExample example);

    Rent selectByPrimaryKey(String rentid);

    int updateByExampleSelective(@Param("record") Rent record, @Param("example") RentExample example);

    int updateByExample(@Param("record") Rent record, @Param("example") RentExample example);

    int updateByPrimaryKeySelective(Rent record);

    int updateByPrimaryKey(Rent record);
}