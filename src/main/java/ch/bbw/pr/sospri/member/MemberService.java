package ch.bbw.pr.sospri.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * MemberService
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Service
@Transactional
public class MemberService {
   @Autowired
   private MemberRepository repository;

   public Iterable<Member> getAll() {
      return repository.findAll();
   }

   public void add(Member member) {
      repository.save(member);
   }

   public void update(Long id, Member member) {
      repository.save(member);
   }

   public void deleteById(Long id) {
      repository.deleteById(id);
   }

   public Member getById(Long id) {
      Optional<Member> member = repository.findById(id);
      if (member.isPresent()) return member.get();
      return null;
   }

   public Member getByUserName(String username) {
      Optional<Member> member = repository.findByUsername(username);
      if (member.isPresent()) return member.get();
      return null;
   }
}
