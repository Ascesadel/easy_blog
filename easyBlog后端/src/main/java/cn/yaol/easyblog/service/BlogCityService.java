package cn.yaol.easyblog.service;

import cn.yaol.easyblog.entity.BlogCity;
import cn.yaol.easyblog.vo.BlogCityAreaVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/11/26 15:21
 */
public interface BlogCityService extends IService<BlogCity> {

    List<BlogCityAreaVo> getAllArea();

    List<BlogCity> getAreaAllCity(String area);

}
