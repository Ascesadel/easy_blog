package cn.yaol.easyblog.service.impl;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.BlogSort;
import cn.yaol.easyblog.mapper.BlogCategoryMapper;
import cn.yaol.easyblog.service.BlogCategoryService;
import cn.yaol.easyblog.vo.BlogCategoryManagerVo;
import cn.yaol.easyblog.vo.BlogCategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/4 12:33
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;


    @Lazy
    @Autowired
    private IService<BlogCategory> blogCategoryService;

    @Override
    public List<BlogCategory> getCategoryList(Integer userid) {
        return blogCategoryMapper.getCategoryList(userid);
    }

    /**
     * @description: 将blogCategoryList的值赋给Vo类
     * @author yaol
     * @date 2023/10/4 21:28
     * @version 1.0
     */
    @Override
    public List<BlogCategoryVo> changeCategoryToVoList(List<BlogCategory> blogCategoryList) {
        List<BlogCategoryVo> blogCategoryVoList = new ArrayList<>();
        for (BlogCategory blogCategory : blogCategoryList) {
            BlogCategoryVo blogCategoryVo = new BlogCategoryVo();
            blogCategoryVo.setCategoryId(blogCategory.getCategoryId());
            blogCategoryVo.setSort(blogCategory.getSort());
            blogCategoryVo.setCover(blogCategory.getCover());
            blogCategoryVo.setCategoryName(blogCategory.getCategoryName());
            blogCategoryVo.setCategoryBrief(blogCategory.getCategoryBrief());
            blogCategoryVo.setCategorySelf(blogCategory.getCategorySelf());
            blogCategoryVoList.add(blogCategoryVo);
        }
        // 对categoryVoList按sort属性值进行排序
        Collections.sort(blogCategoryVoList, (c1, c2) -> c1.getSort().compareTo(c2.getSort()));
        return blogCategoryVoList;
    }

    @Override
    public List<BlogCategoryManagerVo> changeCategorySortList(List<BlogSort> blogSortList,List<BlogCategory> blogCategoryList) {
        // 将BlogCategory和BlogSort表中的数据进行对比，将BlogSort中的顺序赋给BlogCategory
        for (BlogCategory blogCategory : blogCategoryList) {
            for (BlogSort blogSort : blogSortList) {
                if (blogCategory.getCategoryId().equals(blogSort.getCategoryId())) {
                    blogCategory.setSort(blogSort.getSort());
                    break;
                }
            }
        }
        return changeCategoryToManagerVoList(blogCategoryList);
    }

    /**
     * @description: 将blogCategoryList的值赋给Vo类(补充添加了userid)
     * @author yaol
     * @date 2023/10/7 8:50
     * @version 1.0
     */
    @Override
    public List<BlogCategoryManagerVo> changeCategoryToManagerVoList(List<BlogCategory> blogCategoryList) {
        List<BlogCategoryManagerVo> managerVoList = new ArrayList<>();
        for (BlogCategory blogCategory : blogCategoryList) {
            BlogCategoryManagerVo managerVo = new BlogCategoryManagerVo();
            managerVo.setCategoryId(blogCategory.getCategoryId());
            managerVo.setSort(blogCategory.getSort());
            managerVo.setCover(blogCategory.getCover());
            managerVo.setCategoryName(blogCategory.getCategoryName());
            managerVo.setCategoryBrief(blogCategory.getCategoryBrief());
            managerVo.setCategorySelf(blogCategory.getCategorySelf());
            managerVo.setUserId(blogCategory.getUserId());
            managerVoList.add(managerVo);
        }
        Collections.sort(managerVoList, (c1, c2) -> c1.getSort().compareTo(c2.getSort()));
        return managerVoList;
    }

    /*
     * @description: 在删除某些博客后对博客数组重新排序
     * @Date 22:44 2023/10/29
     * @author yaol
     */
    @Override
    public void reSortCategory() {
        List<BlogCategory> blogCategoryList = blogCategoryMapper.getSortList();
        // 遍历排序后的数组，将索引值赋给每个元素的sort属性
        for (int i = 0; i < blogCategoryList.size(); i++) {
            blogCategoryList.get(i).setSort(i);
        }
        blogCategoryService.saveOrUpdateBatch(blogCategoryList);
    }

    @Override
    public List<BlogCategory> getCategoryDraftList(Integer userid) {
        return blogCategoryMapper.getCategoryDraftList(userid);
    }

    @Override
    public BlogCategory getByCategoryName(String categoryName) {
        return blogCategoryMapper.getByCategoryName(categoryName);
    }
}
