package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Objects;

/**
 * RegisterController
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
@Controller
@Log4j2
public class RegisterController {
   Logger logger = LoggerFactory.getLogger(RegisterController.class);

   @Autowired
   MemberService memberservice;

   @Autowired
   private ReCaptchaValidationService reCaptchaValidationService;



   @GetMapping("/get-register")
   public String getRequestRegistMembers(Model model) {
      logger.info("getRequestRegistMembers");
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
   public String postRequestRegistMembers(@Valid RegisterMember registerMember,
           @RequestParam(name = "g-recaptcha-response") String captchaResponse, Model model) {
      logger.info("postRequestRegistMembers: registerMember");
      logger.info(String.valueOf(registerMember));

      System.out.println("Captcha response: " + captchaResponse);
      //TODO Hier gemäss Aufgabe ergänzen
      if(! reCaptchaValidationService.validateCaptcha(captchaResponse)){
         registerMember.setConfirmation("Chaptcha invalid");
         logger.error("Captcha invalid");
         //model.addAttribute("error", "Captcha invalid");
         return "register";
      }
      logger.debug("Captcha valid");


      if (Objects.equals(registerMember.getPassword(), registerMember.getConfirmation())) {
         if (memberservice.getByUserName(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase()) != null) {
            model.addAttribute("registerMember", registerMember);
            model.addAttribute("error", "Username already exists");
            return "register";
         }
         if (!registerMember.getPassword().matches("^(?=.*\\d)(?=.*[!@#$%^&*()])(?=.*[A-Z]).{8,}$")) {
            model.addAttribute("registerMember", registerMember);
            model.addAttribute("error", "Password must contain at least 8 characters, one uppercase letter, one number and one special character!");
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
         logger.info("New member registered: " + member.getUsername());
         return "registerconfirmed";
      }
      else {
         model.addAttribute("registerMember", registerMember);
         model.addAttribute("error", "Password and confirmation are not equal");
         logger.info("New member registration failed: " + registerMember.getPrename() + " " + registerMember.getLastname());
         return "register";
      }
   }
}