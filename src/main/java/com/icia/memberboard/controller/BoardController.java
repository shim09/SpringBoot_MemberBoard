package com.icia.memberboard.controller;

import com.icia.memberboard.common.PagingConst;
import com.icia.memberboard.dto.BoardDetailDTO;
import com.icia.memberboard.dto.BoardPagingDTO;
import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardSearchDTO;
import com.icia.memberboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService bs;

    @GetMapping("/save")
    public String saveForm(){
        return "board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
        Long boardId = bs.save(boardSaveDTO);
        return "redirect:/board/";
    }

    // 게시글 전체조회
//    @GetMapping
//    public String findAll(Model model) {
//        List<BoardDetailDTO> boardList = bs.findAll();
//        model.addAttribute("boardList", boardList);
//        log.info("findAll 호출");
//        return "board/findAll";
//    }


    //글조회(/board/5)
    @GetMapping("/{boardId}")
    public String findById(Model model, @PathVariable Long boardId) {
        log.info("글보기 메서드 호출. 요청글번호: {}", boardId);
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        model.addAttribute("comment", board.getCommentDetailDTOList());
        return "board/findById";
    }

    // 게시글 수정
    @GetMapping("/update/{boardId}")
    public String updateForm(Model model, @PathVariable("boardId") Long boardId){
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board",board);
        return "board/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute BoardDetailDTO boardDetailDTO) {
        Long boardId = bs.update(boardDetailDTO);
        //수정완료 후 해당글의 상세페이지 출력
        return "index";
    }

    //
    //게시글삭제(/member/delete/5)
    @GetMapping("delete/{boardId}")
    public String deleteById(@PathVariable("boardId") Long boardId) {
        bs.deleteById(boardId);
        return "redirect:/board/";
    }

    // 페이징 처리(/board?page=4)
    // 5번글 (/board/5)
    @GetMapping
    public String paging(@PageableDefault(page =1) Pageable pageable, Model model){
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/paging";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(page =1) Pageable pageable, @ModelAttribute BoardSearchDTO boardSearchDTO, Model model){

        Page<BoardPagingDTO> boardDetailDTOList = bs.search(boardSearchDTO, pageable);
        System.out.println("boardDetailDTOList.toString() = " + boardDetailDTOList.toString());
        model.addAttribute("boardList", boardDetailDTOList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardDetailDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardDetailDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("select", boardSearchDTO.getSelect());
        model.addAttribute("keyword", boardSearchDTO.getKeyword());
        return "board/search";
    }

}
