package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.BlogCategory;
import cn.yaol.easyblog.entity.User;
import cn.yaol.easyblog.vo.BlogCityAreaVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/10/1 10:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogCityMapper blogCityMapper;

    @Test
    public void getCategoryList() {
        List<BlogCategory> allBlogCategory = blogCategoryMapper.getCategoryList(100000);
        for (BlogCategory blogCategory : allBlogCategory){
            System.out.println(blogCategory);
        }
    }

    @Test
    public void selectList() {
        List<BlogCategory> allBlogCategory = blogCategoryMapper.getSortList();
        for (BlogCategory blogCategory : allBlogCategory){
            System.out.println(blogCategory);
        }
    }

    @Test
    public void selectIdList() {
        List<Integer> idLists = userMapper.userIdLists();
        for (Integer id : idLists){
            System.out.println("获取到的用户id为:"+id);
        }
    }

    @Test
    public void selectUserByUserName() {
        User user = userMapper.getUserByUsername("admin");
        System.out.println(user);
    }

    @Test
    public void selectUserByTrueName() {
        User user = userMapper.getUserByTruename("顶真");
        System.out.println(user);
    }


    @Test
    public void getByCategoryName() {
        BlogCategory blogCategory = blogCategoryMapper.getByCategoryName("你干嘛");
        System.out.println(blogCategory);
    }

    @Test
    public void getAreaList() {
        List<BlogCityAreaVo> lists = blogCityMapper.getAllArea();
        for (BlogCityAreaVo id : lists){
            System.out.println("获取到的地区:"+id);
        }
    }
}
