package cn.itcast.travel.controller;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.SmsResult;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.utils.SMSUtils;
import cn.itcast.travel.utils.ValidateCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody  //返回的数据格式为json
    @GetMapping("/sendCheckCode")
    public SmsResult sendCheckCode(String telephone){
        //1 创建返回对象
        SmsResult  smsResult=new SmsResult();
        System.out.println(telephone);
        //1.校验手机号是否为空
        if (telephone.equals("")||telephone==null){
            //短信发送失败 0 表示发送正常 1 或者其他不表示发送失败
            smsResult.setCode(1);
            smsResult.setMsg("手机号不合法");
            return smsResult;
        }
        //2 合法 发送手机验证码
        //2.1 生成一个随机验证码
        String code=ValidateCodeUtils.generateValidateCode(6)+"";
        System.out.println(code);
        //2.2 发送短信
        //  try {
        // SMSUtils.sendMessage("商之翼","SMS_142946316",telephone,code);

//        } catch (ClientException e) {
//            //短信发送失败 0 表示发送正常 1 或者其他不表示发送失败
//            smsResult.setCode(1);
//            smsResult.setMsg("短信发送异常");
//            return smsResult;
//
//        }
        //短信发送成功
        smsResult.setCode(0);
        smsResult.setResult(code);
        return  smsResult;
    }

    @ResponseBody
    @PostMapping("/registered")
    public ResultInfo register(User user,String checkCodeKey,String check){
        //1.校验验证码是否正确
        if (!check.equals(checkCodeKey)){
            return  new ResultInfo(false,"验证码错误");
        }
        //2.校验用户名是否存在
        boolean flag=userService.jyUserByUserName(user.getUsername());
        if (flag){
            return new ResultInfo(false,"用户名已存在");
        }
        //3.保存用户信息
        int i= userService.insetUser(user);
        if (i<=0){
            return new ResultInfo(false,"注册失败 请联系管理员 或者重新注册");
        }
        return new ResultInfo(true,"","");
    }

    @ResponseBody
    @PostMapping("/login")
    public ResultInfo login(User user , String check, HttpServletRequest request){
        String check_code = (String) request.getSession().getAttribute("CHECK_CODE");
        if(!(check!=null&&check.equals(check_code))){
        return  new ResultInfo(false,"验证码输入错误");
        }
       User u = userService.findByUserUseNameAndPassword(user);
        if(u == null){
            return new ResultInfo(false,"用户名密码不正确");
        }
        request.getSession().setAttribute("user",u);
        return new ResultInfo(true,"登陆成功","");

    }

    @ResponseBody
    @GetMapping("/findOne")
    public  User findOne(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        return  user;
    }
    @GetMapping("/exit")
    public  String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
//        重定向，跳转到登录页面
        return "redirect:/login.html";
    }


}
