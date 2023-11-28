package cn.yaol.easyblog.controller;

import cn.yaol.easyblog.entity.R;
import cn.yaol.easyblog.entity.User;
import cn.yaol.easyblog.service.UserService;
import cn.yaol.easyblog.util.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yao
 * @Description 用户控制器
 * @Date 2023/9/5 9:39
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${avatarImagesFilePath}")
    private String avatarImagesFilePath;

    @PostMapping("/login")
    public R login(@RequestParam("username") String userName, @RequestParam("password") String password){
        User currentUser = userService.login(userName,password);

        if (currentUser == null ) {
            return R.error("用户名或密码错误！");
        } else if("1".equals(currentUser.getStatus()) ){
            return R.error("该用户已被封禁，请联系管理人员！");
        } else {
            currentUser.setLastTime(new Date());
            userService.updateById(currentUser);
            return R.ok("登录成功！").put("currentUser",currentUser);
        }
    }

    /*
     * @description: 查询是否有相同的用户名
     * @Date 22:44 2023/11/1
     * @param: user
     * @return:
     * @return cn.yaol.easyblog.entity.R
     * @author yaol
     */
    @PostMapping("/checkUserName")
    public R checkUserName(@RequestBody User user){
        if(userService.getByUsername(user.getUsername())==null){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * @description: 用户注册
     * @author yaol
     * @date 2023/11/4 9:14
     * @version 1.0
     */
    @PostMapping("/register")
    public R register(@RequestBody User user){
        userService.register(user);
        return R.ok();
    }

    /**
     * @description: 上传头像
     * @author yaol
     * @date 2023/11/9 10:00
     * @version 1.0
     */
    @RequestMapping("/uploadAvatar")
    public Map<String,Object> uploadCover(MultipartFile file)throws Exception{
        Map<String,Object> resultMap=new HashMap<>();
        if(!file.isEmpty()){// 获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName= "avatar_"+ DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(avatarImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","images/avatar/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }


    /**
     * @description: 用户修改头像
     * @author yaol
     * @date 2023/11/9 14:24
     * @version 1.0
     */
    @RequestMapping("/updateAvatar")
    public R updateAvatar(@RequestBody User user){
        User currentUser = userService.getById(user.getUserId());
        currentUser.setLastTime(new Date());
        currentUser.setAvatar(user.getAvatar());
        userService.updateById(currentUser);
        return R.ok().put("currentUser",currentUser);
    }

    /**
     * @description: 查询用户昵称是否已存在
     * @author yaol
     * @date 2023/11/10 8:21
     * @version 1.0
     */
    @PostMapping("/checkTrueName")
    public R checkTrueName(@RequestParam("tname") String truename){
        if(userService.getByTruename(truename)==null){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * @description: 更新用户名
     * @author yaol
     * @date 2023/11/10 15:52
     * @version 1.0
     */
    @PostMapping("/changeTrueName")
    public R changeTrueName(@RequestParam("id") String userid,@RequestParam("tname") String truename){
        User currentUser = userService.getById(userid);
        currentUser.setLastTime(new Date());
        currentUser.setTname(truename);
        boolean flag = userService.updateById(currentUser);
        if(flag){
            return R.ok().put("currentUser",currentUser);
        }else {
            return R.error();
        }
    }


    @PostMapping("/changePassword")
    public R changePassword(@RequestParam("id") Integer userid,@RequestParam("password") String password,@RequestParam("newPassword") String newPassword){
        User currentUser = userService.getByIdAndPassword(userid,password);
        if(currentUser == null){
            return R.error("原密码错误!");
        }
        String salt = currentUser.getSalt();
        String newPsw = userService.getFinalPassword(newPassword,salt);
        currentUser.setPassword(newPsw);
        currentUser.setLastTime(new Date());
        boolean flag = userService.updateById(currentUser);
        if(flag){
            return R.ok().put("currentUser",currentUser);
        }else {
            return R.error("更新密码产生错误!");
        }
    }
}
