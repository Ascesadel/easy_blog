package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.BlogSort;
import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.service.BlogCategoryService;
import cn.yaol.easyblog.service.BlogSortService;
import cn.yaol.easyblog.vo.BlogCategoryManagerVo;
import cn.yaol.easyblog.vo.BlogCategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description: TODO
 * @author yaol
 * @date 2023/10/3 20:06
 * @version 1.0
 */
@RestController
@RequestMapping("/BlogSort")
public class BlogSortController {

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private BlogCategoryService blogCategoryService;

    /*
     * @description: 获取用户界面能够展示的博客数据，并更新sort表
     * @Date 20:26 2023/10/29
     * @param: userid 用户的id
     * @return:
     * @return cn.yaol.easyblog.entity.R
     * @author yaol
     */
    @GetMapping("/userCategoryList/{userid}")
    public R userCategoryList(@PathVariable(value = "userid")Integer userid){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogCategory> blogCategoryList = blogCategoryService.getCategoryList(userid);
        List<BlogSort> blogSortList = blogSortService.getSortList(userid);
        if(blogCategoryList.size() == blogSortList.size() + 1){
            // 用户新增了一个博客，但是sort表中没有更新。
            BlogCategory addBlogCategory = blogCategoryList.get(blogCategoryList.size()-1);
            blogSortService.addOneSort(addBlogCategory);
            blogSortList.clear();
            blogSortList = blogSortService.getSortList(userid);
        }else if(blogCategoryList.size() != blogSortList.size()){
            // 如果博客表中查询到的数据与blogSort不相等
            // 删除BlogSort表中所有与userid有关的数据，再重新插入
            blogSortService.remove(new QueryWrapper<BlogSort>().in("user_id",userid));
            // 清空blogSortList
            blogSortList.clear();
            // 重新赋值
            blogSortList = blogSortService.getNewSortList(userid);
            blogSortService.saveBatch(blogSortList);
        }else {
            blogSortList.clear();
            // 重新赋值
            blogSortList = blogSortService.getSortList(userid);
        }

        List<BlogCategoryManagerVo> blogCategoryVoList = blogCategoryService.changeCategorySortList(blogSortList,blogCategoryList);
        resutMap.put("categoryVoList", blogCategoryVoList);

        return R.ok(resutMap);
    }

    /**
     * @description: 更新拖拽后的list数据（仅更新sort表中的顺序）
     * @author yaol
     * @date 2023/10/7 9:34
     * @version 1.0
     */
    @PostMapping("/updateManagerVoList")
    public R UpdateListSort (@RequestBody List<BlogCategoryManagerVo> managerVoList){
        for(BlogCategoryManagerVo managerVo : managerVoList){
            BlogSort blogSort = new BlogSort();
            blogSort.setCategoryId(managerVo.getCategoryId());
            blogSort.setSort(managerVo.getSort());
            boolean flag = blogSortService.update(blogSort, new UpdateWrapper<BlogSort>().eq("category_id", blogSort.getCategoryId()));
            if(!flag){
                return R.error("修改排列顺序失败!");
            }
        }
        return R.ok("修改排列顺序成功!");
    }

}
