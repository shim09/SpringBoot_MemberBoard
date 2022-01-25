package com.icia.memberboard.service;

import com.icia.memberboard.dto.BoardDetailDTO;
import com.icia.memberboard.dto.BoardPagingDTO;
import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO) throws IOException;

    BoardDetailDTO findById(Long boardId);
//
//    List<BoardDetailDTO> findAll();

    Long update(BoardDetailDTO boardDetailDTO);

    void deleteById(Long boardId);

    Page<BoardPagingDTO> paging(Pageable pageable);

    List<BoardDetailDTO> search(BoardSearchDTO boardSearchDTO);
}
