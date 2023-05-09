package ch.bbw.pr.sospri;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;

/**
 * MembersController
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
@Controller
@Log4j2
public class MembersController {

   Logger logger = LoggerFactory.getLogger(MembersController.class);

   @Autowired
   MemberService memberservice;

   @GetMapping("/get-members")
   public String getRequestMembers(Model model) {
      logger.info("getRequestMembers");
      model.addAttribute("members", memberservice.getAll());
      return "members";
   }

   @GetMapping("/edit-member")
   public String editMember(@RequestParam(name = "id", required = true) long id, Model model) {
      Member member = memberservice.getById(id);
      logger.info("editMember get: " + member);
      model.addAttribute("member", member);
      return "editmember";
   }

   @PostMapping("/edit-member")
   public String editMember(Member member, Model model) {
      logger.warn("editMember post: edit member" + member);
      Member value = memberservice.getById(member.getId());
      value.setAuthority(member.getAuthority());
      logger.info("editMember post: update member authority" + value);
      return "redirect:/get-members";
   }

   @GetMapping("/delete-member")
   public String deleteMember(@RequestParam(name = "id", required = true) long id, Model model) {
      memberservice.deleteById(id);
      logger.warn("deleteMember: " + id);
      return "redirect:/get-members";
   }
}
