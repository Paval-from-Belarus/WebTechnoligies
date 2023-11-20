package by.bsuir.poit.services;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.User;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface UserService {
Client findClientByUserId(long userId);

User findUserByUserId(long userId);
}
