package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/5 8:58
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    String getUsernameById(Integer userid);

    // 获取所有的用户id
    List<Integer> userIdLists();

    User getUserByUsername(String username);

    User getUserByTruename(String truename);

    User getUserByIdAndPasswrod(Integer userid,String password);
}
