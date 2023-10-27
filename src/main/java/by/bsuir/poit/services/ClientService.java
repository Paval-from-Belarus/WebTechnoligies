package by.bsuir.poit.services;

import by.bsuir.poit.bean.Client;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface ClientService {
Client findClientByUserId(long userId);

}
