package com.icia.memberboard.dto;

import com.icia.memberboard.entity.CommentEntity;
import lombok.Data;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDetailDTO {
    private Long commentId;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createTime;

    public static CommentDetailDTO toCommentDetailDTO(CommentEntity commentEntity) {
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setCommentId(commentEntity.getId());
        commentDetailDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDetailDTO.setCommentContents(commentEntity.getCommentContents());
        commentDetailDTO.setCreateTime(commentEntity.getCreateTime());
        commentDetailDTO.setBoardId(commentEntity.getBoardEntity().getId());
        return commentDetailDTO;
    }

    public static List<CommentDetailDTO> commentDetailDTOList(List<CommentEntity> commentEntityList){
        List<CommentDetailDTO> commentDetailDTOList = new ArrayList<>();
        for (CommentEntity c : commentEntityList){
            commentDetailDTOList.add(toCommentDetailDTO(c));
        }
        return commentDetailDTOList;
     }

}
