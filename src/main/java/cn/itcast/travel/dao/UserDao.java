package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserDao {
    //根据用户名查询用户信息
    //@Select("select * from tab_user where username= #{username}")
    User findUserByUserName(String username);
    // 保存用户信息
    int insert(User user);

    User findByUserUseNameAndPassword(User user);
}
