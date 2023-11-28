package cn.yaol.easyblog.mapper;

import cn.yaol.easyblog.entity.BlogCity;
import cn.yaol.easyblog.vo.BlogCityAreaVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yao
 * @Description TODO
 * @Date 2023/11/26 15:16
 */
@Repository
public interface BlogCityMapper extends BaseMapper<BlogCity> {

    List<BlogCityAreaVo> getAllArea();

    List<BlogCity> getAreaAllCity(String area);

}
