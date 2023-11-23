package by.bsuir.poit.services;

import by.bsuir.poit.bean.ClientFeedback;

import java.util.List;
import java.util.Optional;

/**
 * This is a service class that provides methods to retrieve client feedback based on different criteria.
 *
 * @author Paval Shlyk
 * @since 08/11/2023
 */
public interface ClientFeedbackService {

/**
 * Retrieves a list of client feedback based on the given lot id.
 * This method is unuseful.
 * It's better to use explicit target feedback usr
 *
 * @param lotId the id of the lot
 * @return a list of client feedback
 */
@Deprecated
List<ClientFeedback> findAllByLotId(long lotId);

/**
 * Retrieves the client feedback based on the given lot id and client target id.
 *
 * @param lotId          the id of the lot
 * @param clientTargetId the id of the client target
 * @return the client feedback, if found
 */
Optional<ClientFeedback> findByLotIdAndClientTargetId(long lotId, long clientTargetId);

/**
 * Retrieves the client feedback based on the given lot id and client author id.
 *
 * @param lotId          the id of the lot
 * @param clientAuthorId the id of the client author
 * @return the client feedback, if found
 */
Optional<ClientFeedback> findByLotIdAndClientAuthorId(long lotId, long clientAuthorId);

/**
 * Retrieves a list of client feedback based on the given seller id.
 *
 * @param clientId the id of the seller
 * @return a list of client feedback
 */
List<ClientFeedback> findAllBySellerId(long clientId);
}
