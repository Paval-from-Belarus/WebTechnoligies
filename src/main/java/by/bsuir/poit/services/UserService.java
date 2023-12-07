package by.bsuir.poit.services;

import by.bsuir.poit.dto.ClientDto;
import by.bsuir.poit.dto.UserDto;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface UserService {
ClientDto findClientByUserId(long userId);

UserDto findUserByUserId(long userId);
}
