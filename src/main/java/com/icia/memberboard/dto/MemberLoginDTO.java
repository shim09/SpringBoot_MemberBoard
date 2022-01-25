package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {
    @NotBlank(message = "이메일을 입력하세요.")
    private String memberEmail;
    @NotBlank(message = "비밀번호를 입력하세요")
    @Length(min = 2, max = 8, message = "최소 2자리 최대 8자리의 번호를 입력하세요")
    private String memberPassword;
}
