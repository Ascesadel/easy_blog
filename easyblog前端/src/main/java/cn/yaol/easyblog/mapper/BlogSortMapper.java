package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.BlogSort;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/10/3 13:33
 */
@Repository
public interface BlogSortMapper extends BaseMapper<BlogSort> {

    List<BlogSort> getSortList(Integer userid);


    // 获取sort表中所有的用户id
    List<Integer> getSortUserIdList();

}
