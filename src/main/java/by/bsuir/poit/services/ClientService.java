package by.bsuir.poit.services;

import by.bsuir.poit.bean.Client;
import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface ClientService {
Client findClientByUserId(long userId);
}
