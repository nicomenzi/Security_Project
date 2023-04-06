package ch.bbw.pr.sospri;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
/**
 * CurrentUserControllerAdvice
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
@ControllerAdvice
public class CurrentUserControllerAdvice {
    
    @ModelAttribute("username")
    public User getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (User) authentication.getPrincipal();
    }
    

}
