package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.BlogModel;
import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.service.BlogModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yao
 * @Description 测试使用控制器
 * @Date 2023/8/31 13:29
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private BlogModelService blogModelService;

    @GetMapping("/list")
    public R userList(){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogModel> modelList = blogModelService.list();
        resutMap.put("modelList",modelList);
        return R.ok(resutMap);
    }

}
