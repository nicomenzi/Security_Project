package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;

import java.util.Objects;

/**
 * RegisterController
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Controller
public class RegisterController {
   @Autowired
   MemberService memberservice;

   @GetMapping("/get-register")
   public String getRequestRegistMembers(Model model) {
      System.out.println("getRequestRegistMembers");
      model.addAttribute("registerMember", new RegisterMember());
      return "register";
   }

   @PostMapping("/get-register")
   public String postRequestRegistMembers(RegisterMember registerMember, Model model) {
      System.out.println("postRequestRegistMembers: registerMember");
      System.out.println(registerMember);

      //TODO Hier gemäss Aufgabe ergänzen


      if (Objects.equals(registerMember.getPassword(), registerMember.getConfirmation())) {
         if (memberservice.getByUserName(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase()) != null) {
            model.addAttribute("registerMember", registerMember);
            model.addAttribute("error", "Username already exists");
            return "register";
         }
         // check password with regex ^(?=.*\d)(?=.*[!@#$%^&*()])(?=.*[A-Z]).{8,}$
         if (!registerMember.getPassword().matches("^(?=.*\\d)(?=.*[!@#$%^&*()])(?=.*[A-Z]).{8,}$")) {
            model.addAttribute("registerMember", registerMember);
            model.addAttribute("error", "Password must contain at least 8 characters, one uppercase letter, one number and one special character");
            return "register";
         }
         Member member = new Member();
         member.setPassword(registerMember.getPassword());
         member.setPrename(registerMember.getPrename());
         member.setLastname(registerMember.getLastname());
         //set username prename.lastname lowercase and check if username already exists
         member.setUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase());
         member.setAuthority("ROLE_USER");

         memberservice.add(member);
         return "registerconfirmed";
      }
      else {
         model.addAttribute("registerMember", registerMember);
         model.addAttribute("error", "Password and confirmation are not equal");
         return "register";
      }
   }
}