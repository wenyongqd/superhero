package com.next.service;

import com.next.pojo.vo.StaffVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffService {

    /**
     * 查询演职人员
     * @param trailerId
     * @param role
     * @return
     */
    public List<StaffVO> queryStaffs(String trailerId, Integer role);
}
