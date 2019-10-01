package zy.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zy.community.dto.CommentDTO;
import zy.community.dto.ResultDTO;
import zy.community.exception.CustomizeErrorCode;
import zy.community.mapper.CommentMapper;
import zy.community.model.Comment;
import zy.community.model.User;
import zy.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();

    }


}
