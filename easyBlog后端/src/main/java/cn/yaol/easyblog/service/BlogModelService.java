package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.BlogModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/8/31 13:24
 */
public interface BlogModelService extends IService<BlogModel> {

    BlogModel getByTitle(String title);
}
