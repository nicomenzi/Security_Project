package ch.bbw.pr.sospri.message;

import org.springframework.data.repository.CrudRepository;

/**
 * MessageRepository
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
//                                                         Klasse, id-Typ
public interface MessageRepository extends CrudRepository<Message, Long> {
   //Da wir eine embedded database verwenden, braucht es keine Conecction Information.
}

