package com.icia.memberboard.dto;

import com.icia.memberboard.entity.BoardEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDetailDTO {
    private Long boardId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String boardImageName;
    private LocalDateTime boardTime;
    private List<CommentDetailDTO> commentDetailDTOList;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity) {
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardImageName(boardEntity.getBoardImageName());
        boardDetailDTO.setCommentDetailDTOList(CommentDetailDTO.commentDetailDTOList(boardEntity.getCommentEntityList()));
        if(boardEntity.getUpdateTime()==null){
            boardDetailDTO.setBoardTime(boardEntity.getCreateTime());
        }else {
            boardDetailDTO.setBoardTime(boardEntity.getUpdateTime());
        }

        return boardDetailDTO;
    }
}
