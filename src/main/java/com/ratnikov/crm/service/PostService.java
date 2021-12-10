package com.ratnikov.crm.service;

import com.ratnikov.crm.mapper.PostMapper;
import com.ratnikov.crm.model.Employee;
import com.ratnikov.crm.model.Post;
import com.ratnikov.crm.model.dto.PostDTO;
import com.ratnikov.crm.repository.EmployeeRepository;
import com.ratnikov.crm.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final EmployeeRepository employeeRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<PostDTO> getAllPosts(Pageable pageable){
        return postRepository.findAllBy(pageable)
                .stream()
                .map(postMapper::mapPostToDTO)
                .collect(Collectors.toList());
    }

    public Post addNewPost(Post post, Principal principal) {
        Employee employee = employeeRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new IllegalStateException("Employee not found"));
        LocalDateTime actualTime = LocalDateTime.now();
        post.setAuthor(employee);
        post.setCreatedAt(actualTime);
        return postRepository.save(post);
    }

    public void deletePostById(Long id, Principal principal) {
        Post post = postRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Post cannot be found, the specified id does not exist"));
        Employee employee = employeeRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new IllegalStateException("Employee not found"));
        if(employee.isAdmin() || isAuthorOfPost(post, principal)){
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this post");
        }
    }

    @Transactional(readOnly = true)
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return postMapper.mapPostToDTO(post);
    }

    @Transactional(readOnly = true)
    public Set<PostDTO> findPostsByAuthorFirstnameAndLastname(String firstName, String lastName, Pageable pageable) {
        return postRepository.findPostByAuthorFirstNameAndAuthorLastName(firstName, lastName, pageable)
                .stream()
                .map(postMapper::mapPostToDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public PostDTO editPostContent(Post post, Principal principal){
        Post editedPost = postRepository.findById(post.getPostId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Post does not exist"));
        if(isAuthorOfPost(editedPost, principal)) {
            editedPost.setTitle(post.getTitle());
            editedPost.setContent(post.getContent());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this post");
        }
        return postMapper.mapPostToDTO(editedPost);
    }

    private boolean isAuthorOfPost(Post post, Principal principal){
        return post.getAuthor().getEmail().equals(principal.getName());
    }
}
