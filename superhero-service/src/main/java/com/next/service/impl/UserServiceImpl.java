package com.next.service.impl;

import com.next.mapper.UsersMapper;
import com.next.pojo.Users;
import com.next.pojo.bo.MPWXUserBO;
import com.next.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.swing.plaf.TextUI;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLoginByMPWX(String openId) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mpWxOpenId", openId);

        return usersMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUserMPWX(String openId, MPWXUserBO mpwxUserBO) {

        String userid = sid.nextShort();

        Users user = new Users();

        user.setId(userid);
        user.setNickname(mpwxUserBO.getNickName());
        user.setFaceImage(mpwxUserBO.getAvatarUrl());
        user.setMpWxOpenId(openId);

        user.setBirthday("1900-01-01");
        user.setIsCertified(0);
        user.setRegistTime(new Date());

        usersMapper.insert(user);
        return user;
    }
}
