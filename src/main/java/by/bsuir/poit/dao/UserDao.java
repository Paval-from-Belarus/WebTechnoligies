package by.bsuir.poit.dao;

import by.bsuir.poit.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface UserDao extends JpaRepository<User, Long> {
Optional<User> findById(long id);

Optional<User> findByName(String name);

boolean existsByName(String name);

@Query("update User set userStatus.id = :status where id = :user_id")
@Modifying
void setUserStatus(@Param("user_id") long userId, @Param("status") short status) throws DataAccessException;
}
