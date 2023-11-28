package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/4 13:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogBlogCategoryServiceTest {

    @Autowired
    private BlogModelService blogModelService;

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private CategoryModelService categoryModelService;

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private UserService userService;


    @Autowired
    private BlogCityService blogCityService;

    @Test
    public void list() {
        List<BlogCategory> allBlogCategory = blogCategoryService.list();
        for (BlogCategory blogCategory : allBlogCategory){
            System.out.println(blogCategory);
        }
    }

    @Test
    public void sortNum() {
        int num = blogCategoryService.count();
        System.out.println("有" + num + "个博客");
    }

    @Test
    public void getUsername() {
        String name = userService.getUsernameById(100002);
        System.out.println("name"+name);
    }

    @Test
    public void checkTitle() {
        BlogModel blogModel = blogModelService.getByTitle("h1标题");
        System.out.println(blogModel);
    }

    @Test
    public void selectCategoryModelByModelId() {
        List<CategoryModel> categoryModel = categoryModelService.selectByModelId(4l);
        System.out.println("长度为："+categoryModel.size());
    }

    @Test
    public void getNewSortList() {
        List<BlogSort> sortList = blogSortService.getNewSortList(100000);
        for (BlogSort blogSort : sortList){
            System.out.println("获取到的:"+blogSort);
        }
    }

    @Test
    public void getSortList() {
        List<BlogSort> sortList = blogSortService.getSortList(100000);
        for (BlogSort blogSort : sortList){
            System.out.println("获取到的:"+blogSort);
        }
    }


    @Test
    public void getByIdAndPassword() {
        User user = userService.getByIdAndPassword(100004,"123456");
        System.out.println(user);
    }


    @Test
    public void getCityList() {
        List<BlogCity> sortList = blogCityService.getAreaAllCity("华北");
        for (BlogCity city : sortList){
            System.out.println("获取到的:"+city);
        }
    }

}
