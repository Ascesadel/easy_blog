package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.BlogModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author yao
 * @Description 针对表【blog_model】的数据库操作Mapper
 * @Date 2023/8/31 13:11
 */
@Repository
public interface BlogModelMapper extends BaseMapper<BlogModel> {
}
