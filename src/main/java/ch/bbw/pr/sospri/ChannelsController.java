package ch.bbw.pr.sospri;

import java.util.Date;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;

/**
 * ChannelsController
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
@Controller
@Log4j2
public class ChannelsController {
   Logger logger = LoggerFactory.getLogger(ChannelsController.class);
   @Autowired
   MessageService messageservice;
   @Autowired
   MemberService memberservice;



   @GetMapping("/get-channel")
   public String getRequestChannel(Model model) {
      logger.info("getRequestChannel");
      model.addAttribute("messages", messageservice.getAll());

      Message message = new Message();
      message.setContent("Der zweite Pfeil trifft immer.");
      logger.info("message: " + message);
      model.addAttribute("message", message);
      return "channel";
   }

   @PostMapping("/add-message")
   public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult, Authentication authentication) {
      logger.info("postRequestChannel(): message: " + message.toString());
      if (bindingResult.hasErrors()) {
         model.addAttribute("messages", messageservice.getAll());
         logger.info("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
         return "channel";
      }
      // Hack solange es kein authenticated member hat
      String currentName = authentication.getName();
      String currentUserName = currentName.toLowerCase().replace(" ", ".");
      Member member = memberservice.getByUserName(currentUserName);
      if (member != null) {
         message. setAuthor (member.getPrename() + " " + member.getLastname() );
      }
      else {
         message.setAuthor("Anonymous");
      }

      message.setOrigin(new Date());
      messageservice.add(message);
      logger.info("postRequestChannel(): message: " + message.toString());
      logger.info("postRequestChannel(): message added by " + message.getAuthor());


      return "redirect:/get-channel";
   }
}
