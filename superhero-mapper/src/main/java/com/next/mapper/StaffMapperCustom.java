package com.next.mapper;

import com.next.my.mapper.MyMapper;
import com.next.pojo.Staff;
import com.next.pojo.vo.StaffVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffMapperCustom extends MyMapper<Staff> {

    /**
     * 根据预告片的id查询导演列表跟演员列表
     * @return
     */
    public List<StaffVO> queryStaffs(
            @Param(value = "trailerId") String trailerId,
            @Param(value = "role")Integer role);
}
