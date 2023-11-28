package cn.yaol.easyblog.service.impl;

import cn.yaol.easyblog.entity.CategoryModel;
import cn.yaol.easyblog.mapper.CategoryModelMapper;
import cn.yaol.easyblog.service.CategoryModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/3 10:09
 */
@Service
public class BlogCategoryModelServiceImpl extends ServiceImpl<CategoryModelMapper, CategoryModel> implements CategoryModelService {
    @Autowired
    private CategoryModelMapper categoryModelMapper;

    @Override
    public List<CategoryModel> selectByModelId(Long id) {
        return categoryModelMapper.selectByModelId(id);
    }
}
