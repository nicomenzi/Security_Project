package ch.bbw.pr.sospri.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RegisterMember
 *
 * @author Nico Menzi
 * @version 06.04.2023
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
