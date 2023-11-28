package cn.yaol.easyblog.service.impl;

import cn.yaol.easyblog.entity.BlogModel;
import cn.yaol.easyblog.mapper.BlogModelMapper;
import cn.yaol.easyblog.service.BlogModelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/8/31 13:26
 */
@Service
public class BlogModelServiceImpl extends ServiceImpl<BlogModelMapper, BlogModel> implements BlogModelService {

    /**
     * @description: 根据标题找到对应的模板信息
     * @author yaol
     * @date 2023/9/19 16:08
     * @version 1.0
     */
    @Override
    public BlogModel getByTitle(String title) {
        return getOne(new QueryWrapper<BlogModel>().eq("model_title",title));
    }
}
