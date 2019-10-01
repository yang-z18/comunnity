package zy.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zy.community.enums.CommentTypeEnum;
import zy.community.exception.CustomizeErrorCode;
import zy.community.exception.CustomizeException;
import zy.community.mapper.CommentMapper;
import zy.community.mapper.QuestionExtMapper;
import zy.community.mapper.QuestionMapper;
import zy.community.model.Comment;
import zy.community.model.Question;
import zy.community.model.QuestionExample;


@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    public void insert(Comment comment) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question =questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
