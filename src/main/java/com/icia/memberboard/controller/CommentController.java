package com.icia.memberboard.controller;

import com.icia.memberboard.dto.CommentDetailDTO;
import com.icia.memberboard.dto.CommentSaveDTO;
import com.icia.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService cs;

    @PostMapping("/save")
    public @ResponseBody List<CommentDetailDTO> save(@RequestBody CommentSaveDTO commentSaveDTO){
    cs.save(commentSaveDTO);
    List<CommentDetailDTO> commentList = cs.findAll(commentSaveDTO.getBoardId());
    return commentList;
    }

}
