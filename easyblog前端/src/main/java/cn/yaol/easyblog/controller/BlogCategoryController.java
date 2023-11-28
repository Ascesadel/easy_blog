package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.CategoryModel;
import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.entity.User;
import cn.yaol.easyblog.service.BlogSortService;
import cn.yaol.easyblog.service.CategoryModelService;
import cn.yaol.easyblog.service.BlogCategoryService;
import cn.yaol.easyblog.service.UserService;
import cn.yaol.easyblog.util.DateUtil;
import cn.yaol.easyblog.vo.CategoryAddVo;
import cn.yaol.easyblog.vo.CategoryInfoPageVo;
import cn.yaol.easyblog.vo.CategoryInfoVo;
import cn.yaol.easyblog.vo.BlogCategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * @Author yao
 * @Description 博客文章的控制器
 * @Date 2023/9/4 12:35
 */
@RestController
@RequestMapping("/Category")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private CategoryModelService categoryModelService;

    @Autowired
    private UserService userService;


    @Autowired
    private BlogSortService blogSortService;


    @Value("${coverImagesFilePath}")
    private String coverImagesFilePath;
    /**
     * @description: 获取博客全部数据
     * @author yaol
     * @date 2023/9/4 13:26
     * @version 1.0
     */
    @GetMapping("/list")
    public R categoryList(){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogCategory> blogCategoryList = blogCategoryService.list();
        resutMap.put("categoryList", blogCategoryList);
        return R.ok(resutMap);
    }

    /**
     * @description: 获取指定的博客（不是草稿）（用户id的所有博客，其他用户的共享博客）
     * @author yaol
     * @date 2023/10/1 15:41
     * @version 1.0
     */
    @GetMapping("/userCategoryList/{userid}")
    public R userCategoryList(@PathVariable(value = "userid")Integer userid){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogCategory> blogCategoryList = blogCategoryService.getCategoryList(userid);
        resutMap.put("categoryList", blogCategoryList);
        return R.ok(resutMap);
    }

    /**
     * @description: 获取封装的博客vo数据
     * @author yaol
     * @date 2023/9/4 22:50
     * @version 2.0
     */
    @GetMapping("/voList")
    public R categoryVoList(){
        Map<String,Object> resutMap = new HashMap<>();
        List<BlogCategory> blogCategoryList = blogCategoryService.list();
        resutMap.put("categoryVoList", blogCategoryService.changeCategoryToVoList(blogCategoryList));
        return R.ok(resutMap);
    }


    /*
     * @description: 获取某个用户的所有草稿博客
     * @Date 15:40 2023/11/11
     * @param: userid
     * @return:
     * @return cn.yaol.easyblog.entity.R
     * @author yaol
     */
    @GetMapping("/userDraftCategoryList/{userid}")
    public R userDraftCategoryList(@PathVariable(value = "userid")Integer userid){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogCategory> blogCategoryList = blogCategoryService.getCategoryDraftList(userid);
        resutMap.put("categoryList", blogCategoryList);
        return R.ok(resutMap);
    }

    /**
     * @description: 更新拖拽后的list数据
     * @author yaol
     * @date 2023/9/11 10:18
     * @version 1.0
     */
    @PostMapping("/updateVoList")
    public R categoryUpdateVoList(@RequestBody List<BlogCategoryVo> blogCategoryVoList){
        for(BlogCategoryVo blogCategoryVo : blogCategoryVoList){
            BlogCategory newBlogCategory = new BlogCategory();
            newBlogCategory.setCategoryId(blogCategoryVo.getCategoryId());
            newBlogCategory.setSort(blogCategoryVo.getSort());
            boolean flag = blogCategoryService.updateById(newBlogCategory);
            if(!flag){
                return R.error("修改排列顺序失败!");
            }
        }
        return R.ok("修改排列顺序成功!");
    }

    /**
     * @description: 获取指定id的博客的部分信息
     * @author yaol
     * @date 2023/9/16 11:10
     * @version 1.0
     */
    @PostMapping("/findCategory/{categoryId}")
    public R findCategory(@PathVariable(value = "categoryId")Integer categoryId){
        BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
        CategoryAddVo currentCategoryAddVo = new CategoryAddVo();
        currentCategoryAddVo.setCategoryId(currentBlogCategory.getCategoryId());
        currentCategoryAddVo.setCategoryBrief(currentBlogCategory.getCategoryBrief());
        currentCategoryAddVo.setCategorySelf(currentBlogCategory.getCategorySelf());
        currentCategoryAddVo.setCategoryName(currentBlogCategory.getCategoryName());
        currentCategoryAddVo.setCover(currentBlogCategory.getCover());
        currentCategoryAddVo.setSort(currentBlogCategory.getSort());
        currentCategoryAddVo.setUserId(currentBlogCategory.getUserId());
        return R.ok().put("currentCategory",currentCategoryAddVo);
    }

    /**
     * @description: 新增博客的第一步，将获取的顺序、自动生成的博客id传到前端。
     * 获取当前博客的数量，并加一成为新博客的序列数
     * @author yaol
     * @date 2023/9/8 20:05
     * @version 1.0
     */
    @GetMapping("/addCategory/{userId}/username/{username}")
    public R addCategory(@PathVariable(value = "userId")Integer userId,@PathVariable(value = "username")String username){
        int num = blogCategoryService.count();
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setSort(num);
        blogCategory.setUserId(userId);
        blogCategory.setUsername(username);
        blogCategory.setCategoryType("0");
        blogCategory.setCreateTime(new Date());
        blogCategoryService.save(blogCategory);
        int newCategoryId = blogCategory.getCategoryId();
        return R.ok().put("categoryId",newCategoryId).put("newSort",num);
    }

    /**
     * @description: 新增或者修改博客的一部分，将sort、cover、categoryName、categoryBrief、
     * categorySelf、userId 插入数据库
     * @author yaol
     * @date 2023/9/17 20:05
     * @version 1.0
     */
    @PostMapping("/updateCategory")
    public R updateCategory(@RequestBody CategoryAddVo categoryAddVo){
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setCategoryId(categoryAddVo.getCategoryId());
        blogCategory.setSort(categoryAddVo.getSort());
        blogCategory.setCover(categoryAddVo.getCover());
        blogCategory.setCategoryName(categoryAddVo.getCategoryName());
        blogCategory.setCategoryBrief(categoryAddVo.getCategoryBrief());
        blogCategory.setCategorySelf(categoryAddVo.getCategorySelf());
        blogCategory.setUserId(categoryAddVo.getUserId());
        String username = userService.getUsernameById(categoryAddVo.getUserId());
        blogCategory.setUsername(username);
        blogCategory.setUpdateTime(new Date());
        blogCategoryService.saveOrUpdate(blogCategory);
        return R.ok();
    }

    /**
     * @description: 上传博客的封面(先把图片传到文件夹，再form提交新的图片名称)
     * @author yaol
     * @date 2023/9/9 14:06
     * @version 1.0
     */
    @RequestMapping("/uploadCover")
    public Map<String,Object> uploadCover(MultipartFile file)throws Exception{
        Map<String,Object> resultMap=new HashMap<>();
        if(!file.isEmpty()){// 获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName= "cover_"+DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(coverImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","images/cover/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

    /**
     * 更新博客的查看权限
     * @param categoryId
     * @param categorySelf
     * @return
     */
    @GetMapping("/updateCategorySelf/{categoryId}/categorySelf/{categorySelf}")
    public R updateCategorySelf(@PathVariable(value = "categoryId")Integer categoryId,@PathVariable(value = "categorySelf")String categorySelf){
        BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
        currentBlogCategory.setCategorySelf(categorySelf);
        currentBlogCategory.setUpdateTime(new Date());
        blogCategoryService.saveOrUpdate(currentBlogCategory);
        return R.ok();
    }

    /**
     * @description: 博客提交后，设置默认的分类为博客
     * @author yaol
     * @date 2023/9/15 14:41
     * @version 1.0
     */
    @GetMapping("/setCategorySelf/{categoryId}")
    public R setCategorySelf(@PathVariable(value = "categoryId")Integer categoryId){
        BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
        currentBlogCategory.setCategorySelf("1");
        blogCategoryService.saveOrUpdate(currentBlogCategory);
        return R.ok();
    }

    /**
     * @description: 删除博客,并对sort表进行删除，同时更新博客排序
     * @author yaol
     * @date 2023/10/30 14:57
     * @version 2.0
     */
    @Transactional
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        if (ids != null && ids.length > 0) {
            blogCategoryService.removeByIds(Arrays.asList(ids));
            // 删除sort表中的数据
            blogSortService.removeByIds(Arrays.asList(ids));
            // 重新排序
            blogSortService.reChangeSort();
            // 更新博客排序
            blogCategoryService.reSortCategory();
            // 将存储博客内容的表中数据删除
            categoryModelService.remove(new QueryWrapper<CategoryModel>().in("category_id",ids));
            return R.ok();
        }else {
            return R.error("删除出现错误");
        }
    }

    /**
     * @description: 根据博客id获取单个博客信息（infoVo类）
     * @author yaol
     * @date 2023/9/22 15:04
     * @version 1.0
     */
    @PostMapping("/findCategoryInfo/{categoryId}")
    public R findCategoryInfo(@PathVariable(value = "categoryId")Integer categoryId){
        BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
        Integer userid = currentBlogCategory.getUserId();
        User theUser = userService.getById(userid);
        CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
        categoryInfoVo.setCategoryId(currentBlogCategory.getCategoryId());
        categoryInfoVo.setCategoryName(currentBlogCategory.getCategoryName());
        categoryInfoVo.setCategoryBrief(currentBlogCategory.getCategoryBrief());
        categoryInfoVo.setUsername(theUser.getTname());
        categoryInfoVo.setCreateTime(currentBlogCategory.getCreateTime());
        return R.ok().put("categoryInfoVo",categoryInfoVo);
    }

    /**
     * @description: 获取对应博客的前一个和后一个博客的部分数据
     * @author yaol
     * @date 2023/10/7 21:13
     * @version 1.0
     */
    @GetMapping("/categoryInfoPage/{categoryId}/userId/{userId}/managerType/{managerType}")
    public R categoryInfoPage(@PathVariable(value = "categoryId")Integer categoryId
            ,@PathVariable(value = "userId")Integer userid
            ,@PathVariable(value = "managerType")Integer managerType){
        Map<String,Object> resultMap = new HashMap<>();
        List<CategoryInfoPageVo> categoryInfoPageVoList = new ArrayList<>();
        List<BlogCategory> blogCategoryList;
        if(managerType == 1){
            blogCategoryList = blogCategoryService.list();
        }else {
            blogCategoryList = blogSortService.categoryListGetSort(userid);
        }

        for (BlogCategory blogCategory : blogCategoryList) {
            CategoryInfoPageVo categoryInfoPageVo = new CategoryInfoPageVo();
            categoryInfoPageVo.setCategoryId(blogCategory.getCategoryId());
            categoryInfoPageVo.setSort(blogCategory.getSort());
            categoryInfoPageVo.setCategoryName(blogCategory.getCategoryName());
            categoryInfoPageVoList.add(categoryInfoPageVo);
        }
        Collections.sort(categoryInfoPageVoList, (c1, c2) -> c1.getSort().compareTo(c2.getSort()));

        // 查找id所在的位置
        int index = -1;
        for (int i = 0; i < categoryInfoPageVoList.size(); i++) {
            if (categoryInfoPageVoList.get(i).getCategoryId().equals(categoryId)) {
                index = i;
                break;
            }
        }

        // 相当于 if (index < categoryInfoPageVoList.size() - 1) {
        //    nextCategoryVoList = categoryInfoPageVoList.subList(index + 1, index + 2);
        //} else {
        //    nextCategoryVoList = Collections.emptyList();
        //}

        if (index >= 0) {
            // 返回id对应的前一组数据和后一组数据
            List<CategoryInfoPageVo> prevCategoryVoList = (index > 0) ? categoryInfoPageVoList.subList(index - 1, index) :  Arrays.asList(new CategoryInfoPageVo(0, -1, "已经到顶了"));
            List<CategoryInfoPageVo> nextCategoryVoList = (index < categoryInfoPageVoList.size() - 1) ? categoryInfoPageVoList.subList(index + 1, index + 2) : Arrays.asList(new CategoryInfoPageVo(0, -1, "已经到底部了"));
            resultMap.put("prevCategory", prevCategoryVoList);
            resultMap.put("nextCategory", nextCategoryVoList);
            return R.ok(resultMap);
        } else {
            return R.error();
        }

    }


    /**
     * @description: 将博客的分类修改为 博客正文
     * @author yaol
     * @date 2023/11/11 16:36
     * @version 1.0
     */
    @GetMapping("/changeCategoryType/{categoryId}")
    public R changeCategoryType(@PathVariable(value = "categoryId")Integer categoryId){
        BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
        currentBlogCategory.setCategoryType("1");
        blogCategoryService.saveOrUpdate(currentBlogCategory);
        return R.ok();
    }


    /**
     * @description: 将博客的分类修改为 草稿
     * @author yaol
     * @date 2023/11/12 9:56
     * @version 1.0
     */
    @GetMapping("/draftCategoryType/{categoryId}")
    public R draftCategoryType(@PathVariable(value = "categoryId")Integer categoryId){
        BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
        currentBlogCategory.setCategoryType("0");
        blogCategoryService.saveOrUpdate(currentBlogCategory);
        return R.ok();
    }

    /**
     * @description: 检查博客是否同名
     * @author yaol
     * @date 2023/11/12 15:36
     * @version 1.0
     */
    @GetMapping("/checkCategoryName/{categoryName}")
    public R checkCategoryName(@PathVariable(value = "categoryName") String categoryName){
        if(blogCategoryService.getByCategoryName(categoryName)==null){
            return R.ok();
        }else{
            return R.error();
        }
    }

}
