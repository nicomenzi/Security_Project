package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
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
 * @author Nico Menzi
 * @version 06.04.2023
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

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
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
         if (!registerMember.getPassword().matches("^(?=.*\\d)(?=.*[!@#$%^&*()])(?=.*[A-Z]).{8,}$")) {
            model.addAttribute("registerMember", registerMember);
            model.addAttribute("error", "Password must contain at least 8 characters, one uppercase letter, one number and one special character");
            return "register";
         }
         Member member = new Member();
         //int strength = registerMember.getPassword().length() > 10 ? 12 : 10;
         //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new java.security.SecureRandom());
         //String hashedPassword = bCryptPasswordEncoder.encode(registerMember.getPassword());

         String pepper = "sospri";
         int iterations = 1000;
         int hashWidth = 256;
         Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
         pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
         String hashedPassword = pbkdf2PasswordEncoder.encode(registerMember.getPassword());
         member.setPassword(hashedPassword);
         member.setPrename(registerMember.getPrename());
         member.setLastname(registerMember.getLastname());
         //set username prename.lastname lowercase and check if username already exists
         member.setUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase());
         member.setAuthority("member");

         memberservice.add(member);
         model.addAttribute("username", member.getUsername());
         return "registerconfirmed";
      }
      else {
         model.addAttribute("registerMember", registerMember);
         model.addAttribute("error", "Password and confirmation are not equal");
         return "register";
      }
   }
}