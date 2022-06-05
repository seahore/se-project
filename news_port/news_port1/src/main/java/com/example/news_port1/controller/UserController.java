package com.example.news_port1.controller;

import com.example.news_port1.mapper.*;
import com.example.news_port1.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

@CrossOrigin(origins = "http://localhost:9527", allowCredentials = "true")
@Controller
public class UserController {
    @Autowired
    PassageMapper passageMapper;
    @Autowired
    FollowMapper followMapper;
   @Autowired
    UserMapper userMapper;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    CommentMapper commentMapper;

    @GetMapping(value="/index")//拦截器测试
    public @ResponseBody Object index()
    {
        System.out.println("被拦截");
        return "被拦截";
    }


   //Postman测试
    //http://localhost:8080/register?userId=1&password=aaa&phone=12345&type=normal
    @GetMapping(value="/register")//注册
    public @ResponseBody Object register(String userId,String password,Long phone,String type) {
        Map<String,String> result=new HashMap<>();
        User temp = userMapper.selectByPrimaryKey(userId);
        if (temp == null) {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setPhone(phone);
            if (type.equals("normal")) {
                user.setType(type);
                userMapper.insertSelective(user);
                result.put("status","successful");
                return result;
            }
            else if (type.equals("official")) {
                user.setType("wait");
                userMapper.insertSelective(user);
                result.put("status","wait");
                return result;
            }
            result.put("status","");
            return result;
        }
        else {
            result.put("status","fail");
            return result;
        }

    }

    //Postman测试
    //http://localhost:8080/login?userId=1&password=aaa
    @GetMapping(value="/login")//登录
    public @ResponseBody Object login(String userId, String password, HttpSession session, HttpServletRequest request)
    {
        Map<String,String> result=new HashMap<>();
        User temp=userMapper.selectByPrimaryKey(userId);
        if(temp !=null && !temp.getType().equals("wait") && temp.getPassword().equals(password))
        {

            session.setAttribute("userId",userId);
            session.setAttribute("passWord",password);
            result.put("status","successful");
            return result;
        }
        else {
            result.put("status","fail");
            return result;
        }
    }

    //Postman测试
    //http://localhost:8080/info
    @GetMapping(value="/info")//返回用户信息
    public @ResponseBody Object info(HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        User user = userMapper.selectByPrimaryKey(userId);
        if(user!=null)
            return user;
        else {
            result.put("status","fail");
            return result;
        }
    }

    //Postman测试
    //http://localhost:8080/exit
    @GetMapping(value="/exit")//退出登录
    public @ResponseBody Object exit(HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        session.removeAttribute("userId");
        session.removeAttribute("password");
        {
            result.put("status","successful");
            return result;
        }

    }

    //Postman测试
    //http://localhost:8080/updateUserInformation?password=ddd&phone=33333
    @GetMapping(value="/updateUserInformation")//个人信息修改
    public @ResponseBody Object updateUserInformation(String password,Long phone,HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        User user=userMapper.selectByPrimaryKey(userId);
        if(user!=null) {
            user.setPassword(password);
            user.setPhone(phone);
            userMapper.updateByPrimaryKey(user);
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

    //Postman里测试
    //http://localhost:8080/publishPassage?title=China&keyword=Science&content=China_is_trying_to_develop_the_level_of_science&passageId=1
    @GetMapping(value="/publishPassage")//发布、编辑文章
    public @ResponseBody Object publishPassage(String title,String keyword,String content,HttpSession session) throws ParseException {
        String userId=session.getAttribute("userId").toString();
        Passage passage=new Passage();
        passage.setTitle(title);
        passage.setKeyword(keyword);
        passage.setUserId(userId);
        passage.setContent(content);
        passage.setGoodCount(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time=df.format(new Date());// new Date()为获取当前系统时间
        Date date = df.parse(Time);
        passage.setTime(date);
     passageMapper.insert(passage);
           return passage;
    }

    //Postman测试
    //http://localhost:8080/updatePassage?passageId=1&title=China&keyword=Science&content=China_is_trying_to_develop_the_level_of_science
    @GetMapping(value="/updatePassage")//编辑文章
    public @ResponseBody Object updatePassage(Passage passage,HttpSession session) throws ParseException {
        Map<String,String> result=new HashMap<>();
        Passage passageTemp = passageMapper.selectByPrimaryKey(passage.getPassageId());
        if(passage!=null)
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String Time=df.format(new Date());// new Date()为获取当前系统时间
            Date date = df.parse(Time);
            passageTemp.setTime(date);
            passageTemp.setContent(passage.getContent());
            passageTemp.setKeyword(passage.getKeyword());
            passageTemp.setTitle(passage.getTitle());
            passageMapper.updateByPrimaryKey(passageTemp);
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
    //http://localhost:8080/deletePassage?passageId=1
    @GetMapping(value="/deletePassage")//删除文章
            public @ResponseBody Object deletePassage(Integer passageId,HttpSession session)
    {

        Map<String,String> result=new HashMap<>();
        Passage passage = passageMapper.selectByPrimaryKey(passageId);
        if(passage!=null)
        {passageMapper.deleteByPrimaryKey(passageId);
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
    //http://localhost:8080/selectPassage?passageId=1
    @GetMapping(value="/selectPassage")//查看文章
    public @ResponseBody Object selectPassage(Integer passageId,HttpSession session)
    {
        Passage passage=passageMapper.selectByPrimaryKey(passageId);
        List<Comment> p_comment = commentMapper.selectByPrimaryKey(passageId);
        passage.comments=p_comment;
        return passage;
    }

    //Postman测试
    //http://localhost:8080/selectAllPassage
    @GetMapping(value="/selectAllPassage")//展示用户的所有文章
    public @ResponseBody Object selectAllPassage(HttpSession session)
    {
        String userId=session.getAttribute("userId").toString();
        List<Passage> passages = passageMapper.selectPassagesByUserId(userId);
        for(Passage p:passages)
        {
            List<Comment> p_comment = commentMapper.selectByPrimaryKey(p.getPassageId());
            p.comments=p_comment;
        }
        return passages;
    }

    //Postman测试
    //http://localhost:8080/selectAllGoodPassage
    @GetMapping(value="/selectAllGoodPassage")//展示用户点赞的所有文章
    public @ResponseBody Object selectAllGoodPassage(HttpSession session)
    {
        String userId=session.getAttribute("userId").toString();
        List<Passage> passages=new ArrayList<>();
        List<Good> selectAllGood=goodMapper.selectAllGood(userId);
        for(Good f:selectAllGood)
        {
            Passage p=passageMapper.selectByPrimaryKey(f.getPassageId());
            List<Comment> p_comment = commentMapper.selectByPrimaryKey(f.getPassageId());
            p.comments=p_comment;
           passages.add(p);
        }
        return passages;
    }

    //Postman测试
    //http://localhost:8080/followUser?user_id_followed=2
    @GetMapping(value="/followUser")//关注用户
    public @ResponseBody Object followUser(String user_id_followed,HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        List<Follow> selectAllFollowUser=followMapper.selectAllFollowUser(userId);
        boolean temp=false;
        for(Follow f:selectAllFollowUser)
        {
            if(f.getUserIdFollowed().equals(user_id_followed))
                temp=true;
        }
        if(temp==false)
        {Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setUserIdFollowed(user_id_followed);
        followMapper.insertSelective(follow);
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
    //http://localhost:8080/deleteFollowUser?userIdFollowed=2
    @GetMapping(value="/deleteFollowUser")//取消关注用户
    public @ResponseBody Object deleteFollowUser(String userIdFollowed,HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        List<Follow> selectAllFollowUser=followMapper.selectAllFollowUser(userId);
        boolean temp=false;
        for(Follow f:selectAllFollowUser)
        {
            if(f.getUserIdFollowed().equals(userIdFollowed))
                temp=true;
        }
        if(temp==true)
        {followMapper.deleteFollowUser(userId,userIdFollowed);
         {
                result.put("status","successful");
            return result;
        }}
        else  {
                result.put("status","fail");
        return result;
        }
    }

    //Postman测试
    //http://localhost:8080/selectFollowUser
    @GetMapping(value="/selectFollowUser")//查看关注的用户
    public @ResponseBody Object selectFollowUser(HttpSession session)
    {
        String userId=session.getAttribute("userId").toString();
        List<Follow> selectAllFollowUser=followMapper.selectAllFollowUser(userId);
       List<String> allFollowUser=new ArrayList<>();

        for(Follow f:selectAllFollowUser)
        {
            allFollowUser.add(f.getUserIdFollowed());
        }
        return allFollowUser;
    }

    //Postman测试
    //http://localhost:8080/good?passageId=1
    @GetMapping(value="/good")//点赞
    public @ResponseBody Object good(Integer passageId,HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        List<Good> selectAllGood=goodMapper.selectAllGood(userId);
        boolean temp=false;
        for(Good f:selectAllGood)
        {
            if(f.getPassageId()==passageId)
                temp=true;
        }
        if(temp==false) {
            Good good = new Good();
            good.setUserId(userId);
            good.setPassageId(passageId);
            goodMapper.insertSelective(good);
            Passage passage = passageMapper.selectByPrimaryKey(passageId);
            passage.setGoodCount(passage.getGoodCount() + 1);
            passageMapper.updateByPrimaryKey(passage);
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
    //http://localhost:8080/deleteGood?passageId=1
    @GetMapping(value="/deleteGood")//取消点赞
    public @ResponseBody Object deleteGood(Integer passageId,HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        List<Good> selectAllGood=goodMapper.selectAllGood(userId);
        boolean temp=false;
        for(Good f:selectAllGood)
        {
            if(f.getPassageId()==passageId)
                temp=true;
        }
        if(temp==true)
        {Good good =new Good();
      good.setUserId(userId);
      good.setPassageId(passageId);
        goodMapper.deleteByPrimaryKey(good);
        Passage passage=passageMapper.selectByPrimaryKey(passageId);
        passage.setGoodCount(passage.getGoodCount()-1);
        passageMapper.updateByPrimaryKey(passage);
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
    //http://localhost:8080/comment?passageId=1&review=great
    @GetMapping(value="/comment")//评论
    public @ResponseBody Object comment(Integer passageId,String review,HttpSession session) throws ParseException {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        Comment comment =new Comment();
        comment.setUserId(userId);
        comment.setPassageId(passageId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String Time=df.format(new Date());// new Date()为获取当前系统时间
        Date date = df.parse(Time);
        comment.setTime(date);
        comment.setReview(review);
        commentMapper.insertSelective(comment);
        {
            result.put("status","successful");
            return result;
        }
    }

    //Postman测试
    //http://localhost:8080/deleteComment?passageId=1&review=great
    @GetMapping(value="/deleteComment")//取消评论
    public @ResponseBody Object deleteComment(Integer passageId,String review,HttpSession session)
    {
        Map<String,String> result=new HashMap<>();
        String userId=session.getAttribute("userId").toString();
        Comment comment=commentMapper.selectByAll(userId,passageId,review);
        if(comment!=null)
        {commentMapper.deleteByPrimaryKey(comment.getCommentId());
            {
                result.put("status","successful");
                return result;
            }}
        else {
            result.put("status","fail");
            return result;
        }
    }
}
