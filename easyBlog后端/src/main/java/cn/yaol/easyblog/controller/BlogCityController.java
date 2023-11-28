package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.BlogCity;
import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.service.BlogCityService;
import cn.yaol.easyblog.vo.BlogCityAreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author yao
 * @Description 城市气象表 控制器
 * @Date 2023/11/26 15:24
 */
@RestController
@RequestMapping("/city")
public class BlogCityController {

    @Autowired
    private BlogCityService blogCityService;

    /**
     * @description: 获取所有的地区
     * @author yaol
     * @date 2023/11/26 16:30
     * @version 1.0
     */
    @PostMapping("/getArea")
    public R getArea(){
        List<BlogCityAreaVo> list = blogCityService.getAllArea();
        return R.ok().put("areas",list);
    }


    /**
     * @description: 根据地区 获取城市
     * @author yaol
     * @date 2023/11/26 22:36
     * @version 1.0
     */
    @PostMapping("/getCity")
    public R getCity(@RequestParam("area") String area){
        List<BlogCity> cityLists = blogCityService.getAreaAllCity(area);
        return R.ok().put("citys",cityLists);
    }

}
