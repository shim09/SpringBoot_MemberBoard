package com.icia.memberboard.controller;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberInsertDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;
import com.icia.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService ms;

    @GetMapping("/insert")
    public String insertForm(){
    return "member/insert";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute MemberInsertDTO memberInsertDTO) throws IOException {
        Long memberId = ms.insert(memberInsertDTO);
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("login", new MemberLoginDTO());
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login")
                        MemberLoginDTO memberLoginDTO, BindingResult bindingResult,
                        HttpSession session){
        if(bindingResult.hasErrors()){
            return "member/login";
        }

        if (ms.login(memberLoginDTO)){
            MemberDetailDTO memberDetailDTO = ms.findByMemberId(memberLoginDTO.getMemberEmail());
            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            session.setAttribute("LoginId", memberDetailDTO.getMemberId());
            return "redirect:/board/";
        }else{
            bindingResult.reject("loginFail","이메일 또는 비밀번호가 틀립니다.");
            return "member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

     // 값 변환을 위해 꼭 필요함
    @PostMapping("findByMemberEmail") // 아이디 중복확인을 위한 값으로 따로 매핑
    public @ResponseBody String findByMemberEmail(@RequestParam("memberEmail") String memberEmail){
        String result = ms.findByMemberEmail(memberEmail);
        return result;
    }

    // 전체조회
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        return "member/findAll";

    }
    // 상세조회
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId")Long memberId, Model model){
        System.out.println("memberId = " + memberId);
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member",member);
        return "member/findById";
    }



    // 회원 수정(post)

    @GetMapping("/update/{memberId}")
    public String updateForm(@PathVariable Long memberId, Model model){
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member",member);
        return "member/update";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute MemberUpdateDTO memberUpdateDTO){
        Long memberId = ms.update(memberUpdateDTO);
        return "redirect:/board/";
    }

    // 회원삭제

        @GetMapping("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);
        return "redirect:/member/";
    }

        //회원삭제(/member/5)
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById2(@PathVariable Long memberId) {
        ms.deleteById(memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 마이페이지
    @GetMapping("/myPage/{memberId}")
    public String myPage(@PathVariable("memberId")Long memberId, Model model){
        MemberDetailDTO member = ms.myPage(memberId);
        model.addAttribute("member", member);
        return "member/myPage";
    }

    @GetMapping("/admin")
    public String admin(){
        return "member/admin";
    }
}
