package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.BlogSort;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/10/3 13:37
 */
public interface BlogSortService extends IService<BlogSort> {

    List<BlogSort> getSortList(Integer userid);

    List<BlogSort> getNewSortList(Integer userid);

    List<BlogCategory> categoryListGetSort(Integer userid);

    void addOneSort(BlogCategory blogCategory);

    void reChangeSort();

}
