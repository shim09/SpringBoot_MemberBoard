package com.icia.memberboard.service;


import com.icia.memberboard.common.PagingConst;
import com.icia.memberboard.dto.BoardDetailDTO;
import com.icia.memberboard.dto.BoardPagingDTO;
import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardSearchDTO;
import com.icia.memberboard.entity.BoardEntity;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.BoardRepository;
import com.icia.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository br;

    private final MemberRepository mr;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) throws IOException {

        MultipartFile boardImage = boardSaveDTO.getBoardImage();
        String boardImageName = boardImage.getOriginalFilename();
        boardImageName = System.currentTimeMillis() + "-" + boardImageName;
        String savePath = "C:\\Users\\Shim\\Desktop\\shim\\development.shc\\source\\member\\MemberBoardProject\\src\\main\\resources\\static\\upload\\"+boardImageName;
        if(!boardImage.isEmpty()) {
            boardImage.transferTo(new File(savePath));
        }
        boardSaveDTO.setBoardImageName(boardImageName);

        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO, memberEntity);
        System.out.println(boardSaveDTO);
        return br.save(boardEntity).getId();
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
            Optional<BoardEntity> optionalBoardEntity = br.findById(boardId);
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        if(optionalBoardEntity.isPresent())
        {
            BoardEntity boardEntity = optionalBoardEntity.get();
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        }

        return boardDetailDTO;
    }

//    @Override
//    public List<BoardDetailDTO> findAll() {
//        List<BoardEntity> boardEntityList = br.findAll();
//        List<BoardDetailDTO> boardList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardEntityList) {
//            boardList.add(BoardDetailDTO.toBoardDetailDTO(boardEntity));
//        }
//        return boardList;
//    }

    @Override
    public Long update(BoardDetailDTO boardDetailDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateBoard(boardDetailDTO);
        Long boardId = br.save(boardEntity).getId();
        return boardId;
    }

    @Override
    public void deleteById(Long boardId) {
        br.deleteById(boardId);
    }

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        // 삼항연산자 사용 시
        page = (page==1)? 0: (page-1);
        // JPA가 제공하는 페이징 기능 사용
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // 페이지 객체를 가져오는 findAll 을 사용
        // Page<BoardEntity> => Page<BoardPagingDTO>
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle())
        );

        return boardList;
    }

    @Override
    public Page<BoardPagingDTO> search(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        if(boardSearchDTO.getSelect().equals("boardTitle")){
            Page<BoardEntity> boardEntityList = br.findByBoardTitleContaining(boardSearchDTO.getKeyword(), PageRequest.of(pageable.getPageNumber()-1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<BoardPagingDTO> boardList = boardEntityList.map(
                    board -> new BoardPagingDTO(board.getId(),
                            board.getBoardWriter(),
                            board.getBoardTitle())
            );
            return boardList;
        } else {
            Page<BoardEntity> boardEntityList = br.findByBoardWriterContaining(boardSearchDTO.getKeyword(), PageRequest.of(pageable.getPageNumber()-1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<BoardPagingDTO> boardList = boardEntityList.map(
                    board -> new BoardPagingDTO(board.getId(),
                            board.getBoardWriter(),
                            board.getBoardTitle())
            );
            return boardList;
        }
    }
}
