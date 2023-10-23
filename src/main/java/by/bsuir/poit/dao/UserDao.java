package by.bsuir.poit.dao;

import by.bsuir.poit.dao.entities.User;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface UserDao {
Optional<User> findById(long id);

User save(User user);

User update(User user);
}
