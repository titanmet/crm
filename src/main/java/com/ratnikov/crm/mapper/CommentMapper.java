package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.Comment;
import com.ratnikov.crm.model.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "postId", source = "post.postId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "commentAuthorFirstName", source = "author.firstName")
    @Mapping(target = "commentAuthorLastName", source = "author.lastName")
    @Mapping(target = "content", source = "content")
    CommentDTO mapCommentToDTO(Comment comment);
}
