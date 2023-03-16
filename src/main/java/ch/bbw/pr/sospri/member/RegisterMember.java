package ch.bbw.pr.sospri.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * To regist a new Member
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Getter
@Setter
@ToString
public class RegisterMember {
   private String prename;
   private String lastname;
   private String password;
   private String confirmation;
}
