package com.icia.memberboard.service;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberInsertDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long insert(MemberInsertDTO memberInsertDTO) throws IOException;

    boolean login(MemberLoginDTO memberLoginDTO);

    String findByMemberEmail(String memberEmail);

    List<MemberDetailDTO> findAll();

    MemberDetailDTO findById(Long memberId);

    Long update(MemberUpdateDTO memberUpdateDTO);

    void deleteById(Long memberId);

    MemberDetailDTO findByMemberId(String memberEmail);

    MemberDetailDTO myPage(Long memberId);
}
