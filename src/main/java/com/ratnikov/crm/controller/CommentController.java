package com.ratnikov.crm.controller;

import com.ratnikov.crm.model.Comment;
import com.ratnikov.crm.model.dto.CommentDTO;
import com.ratnikov.crm.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.ratnikov.crm.controller.ApiMapping.COMMENTS_REST_URL;
import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping(path = COMMENTS_REST_URL)
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<CommentDTO>> getAllComments(Pageable pageable){
        return status(HttpStatus.OK).body(commentService.getAllComments(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable("id") Long id, Pageable pageable){
        return status(HttpStatus.OK).body(commentService.getAllCommentsByPostId(id, pageable));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public Comment addNewCommentToPost(@PathVariable("id") Long id, @RequestBody Comment comment, Principal principal){
        return commentService.addNewCommentToPost(id, comment, principal);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<CommentDTO> editComment(@RequestBody Comment comment, Principal principal){
        return status(HttpStatus.OK).body(commentService.editComment(comment, principal));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public void deleteCommentInPostById(@PathVariable("id") Long id, Principal principal) {
        commentService.deleteCommentInPostById(id, principal);
    }
}