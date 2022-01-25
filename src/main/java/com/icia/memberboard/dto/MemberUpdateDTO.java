package com.icia.memberboard.dto;

import lombok.Data;

@Data
public class MemberUpdateDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberPhone;
    private String memberName;
}
