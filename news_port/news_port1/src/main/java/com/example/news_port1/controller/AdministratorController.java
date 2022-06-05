package com.example.news_port1.controller;

import com.example.news_port1.mapper.CommentMapper;
import com.example.news_port1.mapper.PassageMapper;
import com.example.news_port1.mapper.UserMapper;
import com.example.news_port1.model.Comment;
import com.example.news_port1.model.Passage;
import com.example.news_port1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:9527", allowCredentials = "true")
@Controller
public class AdministratorController {
    @Autowired
    PassageMapper passageMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper  commentMapper;

    //Postman测试
    //http://localhost:8080/ad/showPassagesAd
    @GetMapping(value="/ad/showPassagesAd")//管理员界面显示所有新闻及评论
    public @ResponseBody
    Object showPassagesAd()
    {
        List<Passage> passages = passageMapper.selectAll();
        for(Passage p:passages)
        {
            List<Comment> p_comment = commentMapper.selectByPrimaryKey(p.getPassageId());
            p.comments=p_comment;
        }
        return passages;
    }
    //Postman测试
    //http://localhost:8080/ad/showUsersAd
    @GetMapping(value="/ad/showUsersAd")//管理员界面显示所有用户
    public @ResponseBody
    Object showUsersAd()
    {
        List<User> users = userMapper.selectAll();;
        return users;
    }

    //用户审核申请
    //http://localhost:8080/ad/checkUser
    @RequestMapping("/ad/checkUser")
    public @ResponseBody
    Object checkUser(Model model){
        List<User> users = userMapper.checkUser();
        return users;
    }

    //同意或反对用户申请
    //http://localhost:8080/ad/admitUser?userId=4
    @RequestMapping("/ad/admitUser")
    public  @ResponseBody
    Object admitUser(String userId){
        Map<String,String> result=new HashMap<>();
        if(userMapper.admitUser(userId)){
            {
                result.put("status","successful");
                return result;
            }
        }else{
            result.put("status","fail");
            return result;
        }
    }

    //管理员修改用户信息，返回ok则修改成功；返回fail则修改失败
    //http://localhost:8080/ad/updateUser?userId=1&password=ddd&phone=33333&type=official
    @ResponseBody
    @RequestMapping("/ad/updateUser")
    public Object updateUser(User user ){
        Map<String,String> result=new HashMap<>();
        User userTemp=userMapper.selectByPrimaryKey(user.getUserId());
        if(user!=null) {
            userTemp.setPassword(user.getPassword());
            userTemp.setPhone(user.getPhone());
            userTemp.setType(user.getType());
            userMapper.updateByPrimaryKey(userTemp);
            {
                result.put("status","successful");
                return result;
            }
        }
        else {
            result.put("status","fail");
            return result;
        }
    }

    //Postman测试
    //http://localhost:8080/ad/deletePassageAd?passageId=1
    @GetMapping(value="/ad/deletePassageAd")//删除新闻
    public @ResponseBody
    Object deletePassageAd(Integer passageId)
    {
        Map<String,String> result=new HashMap<>();
        Passage passage =passageMapper.selectByPrimaryKey(passageId);
        if(passage!=null) {
            passageMapper.deleteByPrimaryKey(passageId);
            {
                result.put("status","successful");
                return result;
            }
        }
        else {
            result.put("status","fail");
            return result;
        }
    }

    //Postman测试
    //http://localhost:8080/ad/deleteUserAd?userId=1
    @GetMapping(value="/ad/deleteUserAd")//删除用户
    public @ResponseBody
    Object deleteUserAd(String userId)
    {
        Map<String,String> result=new HashMap<>();
        User user = userMapper.selectByPrimaryKey(userId);
        if(user!=null)
        {userMapper.deleteByPrimaryKey(userId);
            {
                result.put("status","successful");
                return result;
            }}
        else {
            result.put("status","fail");
            return result;
        }
    }

    //Postman测试
    //http://localhost:8080/ad/deleteCommentAd?userId=1&passageId=1&review=great
    @GetMapping(value="/ad/deleteCommentAd")//删除评论
    public @ResponseBody
    Object deleteCommentAd(String userId,Integer passageId,String review)
    {
        Map<String,String> result=new HashMap<>();
        Comment comment=commentMapper.selectByAll(userId,passageId,review);
        if(comment!=null)
        {commentMapper.deleteByPrimaryKey(comment.getCommentId());
            {
                result.put("status","successful");
                return result;
            }}
        else{
            result.put("status","fail");
            return result;
        }
    }
}
