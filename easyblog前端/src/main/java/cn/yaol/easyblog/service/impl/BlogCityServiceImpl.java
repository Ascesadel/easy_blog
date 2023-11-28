package cn.yaol.easyblog.service.impl;

import cn.yaol.easyblog.entity.BlogCity;
import cn.yaol.easyblog.mapper.BlogCityMapper;
import cn.yaol.easyblog.service.BlogCityService;
import cn.yaol.easyblog.vo.BlogCityAreaVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/11/26 15:22
 */
@Service
public class BlogCityServiceImpl extends ServiceImpl<BlogCityMapper, BlogCity> implements BlogCityService {

    @Autowired
    private BlogCityMapper blogCityMapper;

    @Override
    public List<BlogCityAreaVo> getAllArea() {
        return blogCityMapper.getAllArea();
    }

    @Override
    public List<BlogCity> getAreaAllCity(String area) {
        return blogCityMapper.getAreaAllCity(area);
    }
}
