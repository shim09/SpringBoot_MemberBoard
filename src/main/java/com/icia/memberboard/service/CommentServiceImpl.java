package com.icia.memberboard.service;


import com.icia.memberboard.dto.CommentDetailDTO;
import com.icia.memberboard.dto.CommentSaveDTO;
import com.icia.memberboard.entity.BoardEntity;
import com.icia.memberboard.entity.CommentEntity;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.BoardRepository;
import com.icia.memberboard.repository.CommentRepository;
import com.icia.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository cr;
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        BoardEntity boardEntity = br.findById(commentSaveDTO.getBoardId()).get();
        MemberEntity memberEntity = mr.findById(commentSaveDTO.getMemberId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentSaveDTO, boardEntity, memberEntity);
        return cr.save(commentEntity).getId();
    }

    @Override
    public List<CommentDetailDTO> findAll(Long boardId) {
        BoardEntity boardEntity = br.findById(boardId).get();
        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        List<CommentDetailDTO> commentList = new ArrayList<>();
        for(CommentEntity c: commentEntityList) {
            CommentDetailDTO commentDetailDTO = CommentDetailDTO.toCommentDetailDTO(c);
            commentList.add(commentDetailDTO);
        }
        return commentList;
    }

    @Override
    public void deleteById(Long commentId) {
        cr.deleteById(commentId);
    }
}
