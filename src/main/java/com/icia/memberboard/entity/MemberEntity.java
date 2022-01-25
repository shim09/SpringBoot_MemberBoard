package com.icia.memberboard.entity;

import com.icia.memberboard.dto.MemberInsertDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_Id")
    private Long id;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    @Column
    private String memberPhone;

    @Column
    private String memberImageName;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public static MemberEntity toInsertEntity(MemberInsertDTO memberInsertDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberInsertDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberInsertDTO.getMemberPassword());
        memberEntity.setMemberName(memberInsertDTO.getMemberName());
        memberEntity.setMemberPhone(memberInsertDTO.getMemberPhone());
        memberEntity.setMemberImageName(memberInsertDTO.getMemberImageName());
        return memberEntity;
    }


    public static MemberEntity tpUpdateMember(MemberUpdateDTO memberDetailDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPhone(memberDetailDTO.getMemberPhone());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        return memberEntity;
    }
}
