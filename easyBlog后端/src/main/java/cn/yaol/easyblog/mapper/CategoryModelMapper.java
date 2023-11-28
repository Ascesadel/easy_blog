package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.CategoryModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/3 10:07
 */
@Repository
public interface CategoryModelMapper extends BaseMapper<CategoryModel> {

    List<CategoryModel> selectByModelId(Long id);

}
