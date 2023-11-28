package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.BlogSort;
import cn.yaol.easyblog.vo.BlogCategoryManagerVo;
import cn.yaol.easyblog.vo.BlogCategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/4 12:33
 */
public interface BlogCategoryService extends IService<BlogCategory> {

    List<BlogCategory> getCategoryList(Integer userid);

    List<BlogCategoryVo> changeCategoryToVoList(List<BlogCategory> blogCategoryList);

    List<BlogCategoryManagerVo> changeCategorySortList(List<BlogSort> blogSortList,List<BlogCategory> blogCategoryList);

    List<BlogCategoryManagerVo> changeCategoryToManagerVoList(List<BlogCategory> blogCategoryList);

    void reSortCategory();

    List<BlogCategory> getCategoryDraftList(Integer userid);

    BlogCategory getByCategoryName(String categoryName);

}
