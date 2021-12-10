package com.ratnikov.crm.service;

import com.ratnikov.crm.mapper.CommentMapper;
import com.ratnikov.crm.model.Comment;
import com.ratnikov.crm.model.Employee;
import com.ratnikov.crm.model.Post;
import com.ratnikov.crm.model.dto.CommentDTO;
import com.ratnikov.crm.repository.CommentRepository;
import com.ratnikov.crm.repository.EmployeeRepository;
import com.ratnikov.crm.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EmployeeRepository employeeRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllCommentsByPostId(Long id, Pageable pageable){
        return commentRepository.getCommentsByPost_PostId(id, pageable)
                .stream()
                .map(commentMapper::mapCommentToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllComments(Pageable pageable){
        return commentRepository.findAllBy(pageable)
                .stream()
                .map(commentMapper::mapCommentToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Comment addNewCommentToPost(Long id, Comment comment, Principal principal){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Post not found"));
        Employee employee = employeeRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new IllegalStateException("Employee not found"));
        LocalDateTime actualTime = LocalDateTime.now();
        return commentRepository.save(new Comment(
                employee,
                actualTime,
                comment.getContent(),
                post
        ));
    }

    public void deleteCommentInPostById(Long id, Principal principal) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(NOT_FOUND, "Comment cannot be found, the specified id does not exist"));
        Employee employee = employeeRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new IllegalStateException("Employee not found"));
        if(employee.isAdmin() || isAuthorOfComment(comment, principal)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(FORBIDDEN, "You are not the author of this comment");
        }
    }

    @Transactional
    public CommentDTO editComment(Comment comment, Principal principal){
        Comment editedComment = commentRepository.findById(comment.getId()).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Comment does not exist"));
        if(isAuthorOfComment(editedComment, principal)) {
            editedComment.setContent(comment.getContent());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this comment");
        }
        return commentMapper.mapCommentToDTO(editedComment);
    }

    private boolean isAuthorOfComment(Comment comment, Principal principal){
        return comment.getAuthor().getEmail().equals(principal.getName());
    }
}
