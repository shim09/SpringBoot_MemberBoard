package com.icia.memberboard.dto;

import com.icia.memberboard.entity.MemberEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberDetailDTO {
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberPassword;
    private MultipartFile memberImage;
    private String memberImageName;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity member) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(member.getId());
        memberDetailDTO.setMemberEmail(member.getMemberEmail());
        memberDetailDTO.setMemberName(member.getMemberName());
        memberDetailDTO.setMemberPhone(member.getMemberPhone());
        memberDetailDTO.setMemberPassword(member.getMemberPassword());
        memberDetailDTO.setMemberImageName(member.getMemberImageName());
        return memberDetailDTO;
    }
}
