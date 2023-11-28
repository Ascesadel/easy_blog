package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.CategoryModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/3 10:03
 */
public interface CategoryModelService extends IService<CategoryModel> {

    List<CategoryModel> selectByModelId(Long id);

}
