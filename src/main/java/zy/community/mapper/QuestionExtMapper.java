package zy.community.mapper;

import zy.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}