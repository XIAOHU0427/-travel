package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    //校验用户名是否存在
    boolean jyUserByUserName(String username);
    //保存用户信息
    int insetUser(User user);

    User findByUserUseNameAndPassword(User user);
}
