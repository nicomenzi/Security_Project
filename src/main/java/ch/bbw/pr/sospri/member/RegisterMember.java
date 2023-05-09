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

   public String getPrename() {
      return prename;
   }

   public void setPrename(String prename) {
      this.prename = prename;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getConfirmation() {
      return confirmation;
   }

   public void setConfirmation(String confirmation) {
      this.confirmation = confirmation;
   }
}
