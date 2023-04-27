package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public  class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    //校验用户名是否存在
    @Override
    public boolean jyUserByUserName(String username) {
        //根据用户名查询用户信息
        User user=userDao.findUserByUserName(username);
        if (user==null){
            //表示用户名不存在
            return false;
        }
        //用户存在
        return true;
    }
    // 保存用户信息
    @Override
    public int insetUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public User findByUserUseNameAndPassword(User user) {
        return userDao.findByUserUseNameAndPassword(user);
    }
}
