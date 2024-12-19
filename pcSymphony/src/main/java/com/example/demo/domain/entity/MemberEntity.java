package com.example.demo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "user")
public class MemberEntity {

   @Id
   @Column(name = "user_id", nullable=false)
   private String memberId;

   @Column(name = "user_pw", nullable=false)
   private String memberPw;

   @Column(name = "user_name", nullable=false)
   private String memberName;

   @Column(name = "user_email", nullable = false)
   private String email;

   //주소는 null 허용
   @Column(name = "user_address")
   private String address;

   @Column(name = "user_gender", nullable=false)
   private String gender;

}
