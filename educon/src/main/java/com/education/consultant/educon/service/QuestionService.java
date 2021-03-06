package com.education.consultant.educon.service;

import com.education.consultant.educon.document.Comment;
import com.education.consultant.educon.document.Question;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface QuestionService {

    public Question save(Question question);
    public Question update(Long questionId, Question question);
    public Long deleteById(Long id);
    public List<Question> findAll();
    public List<Question> findByFilters(int radius, String latitude, String longitude, List<String> filters);
    public Question findOne(Long id);
    public Comment saveComment(Long questionId, Comment comment);
	public List<Comment> findAllComments(Long questionId);
	public List<Comment> findAllComm();

}
