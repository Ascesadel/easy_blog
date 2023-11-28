package cn.yaol.easyblog.service.impl;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.BlogSort;
import cn.yaol.easyblog.mapper.BlogCategoryMapper;
import cn.yaol.easyblog.mapper.BlogSortMapper;
import cn.yaol.easyblog.service.BlogSortService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @Date 2023/10/3 13:38
 */
@Service
public class BlogSortServiceImpl extends ServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Autowired
    private BlogSortMapper blogSortMapper;

    @Lazy
    @Autowired
    private IService<BlogSort> blogSortService;

    /**
     * @description: 获取sort表中的顺序
     * @author yaol
     * @date 2023/10/7 14:16
     * @version 1.0
     */
    @Override
    public List<BlogSort> getSortList(Integer userid) {
        return blogSortMapper.getSortList(userid);
    }

    /**
     * @description: 将某个用户能查询到的博客数据获取，存储到BlogSort中，并为其排序。
     * BlogSort中的sort为用户设置的排序顺序
     * @author yaol
     * @date 2023/10/3 22:23
     * @version 1.0
     */
    @Override
    public List<BlogSort> getNewSortList(Integer userid) {
        List<BlogCategory> categoryList = blogCategoryMapper.getCategoryList(userid);
        List<BlogSort> sortList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++){
            BlogCategory blogCategory = categoryList.get(i);
            BlogSort currentBlogSort = new BlogSort();
            currentBlogSort.setUserId(userid);
            currentBlogSort.setCategoryId(blogCategory.getCategoryId());
            currentBlogSort.setSort(i);
            sortList.add(currentBlogSort);
        }
        return sortList;
    }

    /**
     * @description: 将BlogSort的sort排序顺序赋值给BlogCategory
     * @author yaol
     * @date 2023/10/9 8:51
     * @version 1.0
     */
    @Override
    public List<BlogCategory> categoryListGetSort(Integer userid) {
        List<BlogCategory> categoryList = blogCategoryMapper.getCategoryList(userid);
        List<BlogSort> sortList = blogSortMapper.getSortList(userid);
        for (BlogCategory blogCategory : categoryList) {
            for (BlogSort blogSort : sortList) {
                if (blogCategory.getCategoryId().equals(blogSort.getCategoryId())) {
                    blogCategory.setSort(blogSort.getSort());
                    break;
                }
            }
        }
        return categoryList;
    }

    /*
     * @description: 用户新增一个符合要求的博客后，sort表更新一条数据
     * @Date 18:19 2023/10/29
     * @param: blogCategory 最后的一个博客实体类，即新添加的博客
     * @return:
     * @author yaol
     */
    @Override
    public void addOneSort(BlogCategory blogCategory) {
        BlogSort newBlogSort = new BlogSort();
        int theSort = blogSortMapper.getSortList(blogCategory.getUserId()).size();
        newBlogSort.setCategoryId(blogCategory.getCategoryId());
        newBlogSort.setUserId(blogCategory.getUserId());
        newBlogSort.setSort(theSort);
        blogSortMapper.insert(newBlogSort);
    }

    /*
     * @description: (在删除博客后调用)对sort表中的数据重新排序，
     * 首先获取该表中所有的用户id(有些用户id在该表中没有数据，因此不从user表中获取userid)
     * 根据id获取到对应的博客数组，对每个数组重新排序
     * @Date 10:27 2023/10/30
     * @param:
     * @return:
     * @author yaol
     */
    @Override
    public void reChangeSort() {
        List<Integer> userIdList = blogSortMapper.getSortUserIdList();
        // 对sort表中的用户id进行遍历
        for (Integer userId : userIdList){
            List<BlogSort> currentSortList = blogSortMapper.getSortList(userId);
            // 根据博客的sort排序
            Collections.sort(currentSortList, (c1, c2) -> c1.getSort().compareTo(c2.getSort()));
            // 生成新的sort值(即使得sort值是连续的)
            for (int i = 0; i < currentSortList.size(); i++) {
                currentSortList.get(i).setSort(i);
            }
            blogSortService.saveOrUpdateBatch(currentSortList);
        }
    }
}
