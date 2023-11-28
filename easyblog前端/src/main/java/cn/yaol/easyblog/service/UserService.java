package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/5 9:37
 */
public interface UserService extends IService<User> {

    String getUsernameById(Integer userid);

    User getByUsername(String username);

    void register(User user);

    User login(String username,String password);

    User getByTruename(String truename);

    String getFinalPassword(String password, String salt);

    User getByIdAndPassword(Integer userid,String password);
}
