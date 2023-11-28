package by.bsuir.poit.dao;

import by.bsuir.poit.dto.User;
import by.bsuir.poit.dao.exception.DataModifyingException;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface UserDao {
Optional<User> findById(long id);

Optional<User> findByUserName(String name);

boolean existsByName(String name);

void setUserStatus(long userId, short status) throws DataModifyingException;

User save(User user);

}
