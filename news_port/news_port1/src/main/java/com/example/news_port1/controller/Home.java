package com.example.news_port1.controller;

import com.example.news_port1.mapper.PassageMapper;
import com.example.news_port1.model.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9527", allowCredentials = "true")
@Controller
public class Home {
    @Autowired
    PassageMapper passageMapper;
    //搜索
    //http://localhost:8080/searchPassage?searchKey=高考
    @GetMapping("/searchPassage")
    public @ResponseBody
    Object searchPassage(String searchKey, Model model){
        searchKey = "%"+searchKey+"%";
        List<Passage> passages = passageMapper.searchPassage(searchKey);
        //model.addAttribute("searchPassage",passages);
        return passages;
    }

    //首页“热搜榜”，将所有新闻按热度排行，并返回前20形成热搜榜
    //http://localhost:8080/hotSearch
    @GetMapping("/hotSearch")
    public @ResponseBody
    Object hotSearch(){
        List<Passage> passages = passageMapper.hotSearch();
        return passages;
    }

    //首页“最新”模块，展示系统内所有用户最新发布的新闻
    //http://localhost:8080/selectLatestPassage
    @RequestMapping("/selectLatestPassage")
    public @ResponseBody
    Object selectLatestPassage(Model model){
        List<Passage> passages = passageMapper.selectLatestPassage();

        return passages;
    }
}
