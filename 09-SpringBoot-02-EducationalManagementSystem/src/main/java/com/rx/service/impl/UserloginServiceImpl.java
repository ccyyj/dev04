package com.rx.service.impl;

import com.rx.dao.UserloginMapper;
import com.rx.entity.Userlogin;
import com.rx.entity.UserloginExample;
import com.rx.service.UserloginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserloginServiceImpl implements UserloginService {
    @Autowired
    private UserloginMapper userloginMapper;
    private String name;

    @Override
    public void save(Userlogin userlogin) {
        userloginMapper.insert(userlogin);
    }

    @Override
    public void removeByName(String toString) {
        UserloginExample userloginExample = new UserloginExample();

        UserloginExample.Criteria criteria = userloginExample.createCriteria();
        criteria.andUsernameEqualTo(name);

        userloginMapper.deleteByExample(userloginExample);
    }
}
