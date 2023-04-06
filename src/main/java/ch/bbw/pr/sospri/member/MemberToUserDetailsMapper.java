package ch.bbw.pr.sospri.member;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
/**
 * MemberToUserDetailsMapper
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member) {
        User user = null;

        if(member!=null){
            System.out.println("MemberToUserDetailsMapper: member != null");

            java.util.Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            System.out.println("MemberToUserDetailsMapper: authorities: " + authorities);

            user = new User(member.getPrename()+ " " + member.getLastname()
                , member.getPassword()
                , true
                , true
                , true
                , true
                , authorities);
        }
        return user;
    }


}
