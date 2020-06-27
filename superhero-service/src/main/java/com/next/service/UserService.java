package com.next.service;

import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;

public interface UserService {
    /**
     * 根据openId查询用户是否注册过
     * @param openId
     * @return
     */
    public Users queryUserForLoginByMPWX(String openId);

    /**
     * 用户注册
     * @param openId
     * @param mpwxUserBO
     * @return
     */
    public Users saveUserMPWX(String openId, MPWXUserBO mpwxUserBO);
}
