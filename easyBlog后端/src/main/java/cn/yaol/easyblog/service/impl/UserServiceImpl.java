package cn.yaol.easyblog.service.impl;

import cn.yaol.easyblog.entity.User;
import cn.yaol.easyblog.mapper.UserMapper;
import cn.yaol.easyblog.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/5 9:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public String getUsernameById(Integer userid) {
        return userMapper.getUsernameById(userid);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    /**
     * @description: 用户注册(密码加密)
     * @author yaol
     * @date 2023/11/3 22:48
     * @version 1.0
     */
    @Override
    public void register(User user) {
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.getUserByUsername(username);
        String salt = result.getSalt();
        String md5Password = getMd5Password(password, salt);
        if(!result.getPassword().equals(md5Password)){
            return null;
        }
        return result;
    }

    @Override
    public User getByTruename(String truename) {
        return userMapper.getUserByTruename(truename);
    }

    @Override
    public String getFinalPassword(String password, String salt) {
        return getMd5Password(password,salt);
    }

    /*
     * @description: 通过用户id和密码查找用户
     * @Date 14:14 2023/11/11
     * @param: userid 用户id
     * @param password 用户输入的原密码
     * @return:  找到的用户
     * @return cn.yaol.easyblog.entity.User
     * @author yaol
     */
    @Override
    public User getByIdAndPassword(Integer userid, String password) {
        User currentUser = userMapper.selectById(userid);
        String currentSalt = currentUser.getSalt(); // 获取当前用户的盐值
        String md5Password = getMd5Password(password, currentSalt); // 将用户输入的密码和盐值一起进行加密
        User haveUser = userMapper.getUserByIdAndPasswrod(userid,md5Password); // 根据id与密码查询是否有该用户
        return haveUser;
    }
}
