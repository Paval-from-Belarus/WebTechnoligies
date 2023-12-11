package by.bsuir.poit.dao;

import by.bsuir.poit.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Short> {
}