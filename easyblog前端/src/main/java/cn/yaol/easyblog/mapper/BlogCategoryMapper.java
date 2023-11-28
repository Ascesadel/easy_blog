package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.BlogCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/4 12:27
 */
@Repository
public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {

    List<BlogCategory> getCategoryList(Integer userid);

    List<BlogCategory> getSortList();

    List<BlogCategory> getCategoryDraftList(Integer userid);

    BlogCategory getByCategoryName(String categoryName);

}
