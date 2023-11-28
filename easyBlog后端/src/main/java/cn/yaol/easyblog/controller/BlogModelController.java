package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.BlogModel;
import cn.yaol.easyblog.entity.CategoryModel;
import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.service.BlogModelService;
import cn.yaol.easyblog.service.CategoryModelService;
import cn.yaol.easyblog.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/9/3 10:17
 */
@RestController
@RequestMapping("/blogModel")
public class BlogModelController {

    @Autowired
    private BlogModelService blogModelService;

    @Autowired
    private CategoryModelService categoryModelService;

    @Value("${contentImagesFilePath}")
    private String contentImagesFilePath;

    @GetMapping("/list")
    public R modelList(){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogModel> modelList = blogModelService.list();
        resutMap.put("modelList",modelList);
        return R.ok(resutMap);
    }

    /**
     * @description: 查询是否有模板有相同的标题，如果有就返回错误，没有就返回成功
     * @author yaol
     * @date 2023/9/19 16:13
     * @version 1.0
     */
    @PostMapping("/checkTitle")
    public R checkTitle(@RequestBody BlogModel blogModel){
        if(blogModelService.getByTitle(blogModel.getModelTitle())==null){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * @description: 插入新的模板
     * @author yaol
     * @date 2023/9/20 8:56
     * @version 1.0
     */
    @PostMapping("/saveModel")
    public R saveModel(@RequestBody BlogModel blogModel){
        boolean flag = blogModelService.save(blogModel);
        if(flag){
            return R.ok();
        }else {
            return R.error("新建模板失败！");
        }
    }

    /**
     * @description: 删除模板信息数据，在删除前在博客模板表进行查询，如果不存在博客使用该模板则删除
     * @author yaol
     * @date 2023/9/20 16:12
     * @version 1.0
     */
    @Transactional
    @GetMapping("/deleteModel/{id}")
    public R deleteModel(@PathVariable(value = "id")Long id){
        List<CategoryModel> list = categoryModelService.selectByModelId(id);
        if(list.size() > 0){
            return R.error("存在使用该模板的博客，请先删除！");
        }else {
            blogModelService.removeById(id);
            return R.ok();
        }
    }

    /**
     * @description: 上传博客内容中的图片
     * @author yaol
     * @date 2023/9/20 8:53
     * @version 1.0
     */
    @RequestMapping("/uploadContent")
    public Map<String,Object> uploadContent(MultipartFile file)throws Exception{
        Map<String,Object> resultMap=new HashMap<>();
        if(!file.isEmpty()){// 获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(contentImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","images/content/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

}
