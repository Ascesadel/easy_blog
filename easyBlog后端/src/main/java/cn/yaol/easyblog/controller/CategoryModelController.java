package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.CategoryModel;
import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.service.CategoryModelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/3 14:03
 */
@RestController
@RequestMapping("/CategoryModel")
public class CategoryModelController {

    @Autowired
    private CategoryModelService categoryModelService;

    /**
     * @description: 将接收到的数据插入数据库中
     * @author yaol
     * @date 2023/9/3 15:49
     * @version 1.0
     */
    @Transactional
    @PostMapping("/save")
    public R CategoryModelSave(@RequestBody List<CategoryModel> categoryModelArr){
        // 获取第一个元素的 category_id
        int categoryId = categoryModelArr.get(0).getCategoryId();
        // 删除具有该 category_id 的现有数据
        QueryWrapper<CategoryModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        categoryModelService.remove(queryWrapper);
        // 将categoryModelArr的值插入数据库中
        boolean flag = categoryModelService.saveBatch(categoryModelArr);
        if (flag) {
            return R.ok("博客保存成功！");
        }else{
            return R.error("博客保存失败，请联系工作人员！");
        }
    }

    /**
     * @description: 获取对应博客的内容
     * @author yaol
     * @date 2023/9/18 10:08
     * @version 1.0
     */
    @PostMapping("/list/{categoryId}")
    public R CategoryModelList(@PathVariable(value = "categoryId")Integer categoryId){
        Map<String,Object> resutMap = new HashMap<>();
        QueryWrapper<CategoryModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        List<CategoryModel> categoryModelList = categoryModelService.list(queryWrapper);
        Collections.sort(categoryModelList, (c1, c2) -> c1.getModelSort().compareTo(c2.getModelSort()));
        resutMap.put("categoryModelList",categoryModelList);
        return R.ok(resutMap);
    }

}
