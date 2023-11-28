package cn.yaol.easyblog.vo;

/**
 * @Author yao
 * @Description 存储所有的地区
 * @Date 2023/11/26 16:14
 */
public class BlogCityAreaVo {
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "BlogCityAreaVo{" +
                "area='" + area + '\'' +
                '}';
    }
}
