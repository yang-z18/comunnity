package zy.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zy.community.Model.Question;
import zy.community.Model.User;
import zy.community.dto.PaginationDTO;
import zy.community.dto.QuestionDTO;
import zy.community.mapper.QuestionMapper;
import zy.community.mapper.UserMapper;
import zy.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
        public String index(HttpServletRequest request,
                            Model model,
                            @RequestParam(name = "page",defaultValue = "1")Integer page,
                            @RequestParam(name = "size",defaultValue = "5")Integer size){
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
