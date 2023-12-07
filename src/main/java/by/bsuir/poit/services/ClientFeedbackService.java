package by.bsuir.poit.services;

import by.bsuir.poit.dto.ClientFeedbackDto;

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
List<ClientFeedbackDto> findAllByLotId(long lotId);

/**
 * Retrieves the client feedback based on the given lot id and client target id.
 *
 * @param lotId          the id of the lot
 * @param clientTargetId the id of the client target
 * @return the client feedback, if found
 */
Optional<ClientFeedbackDto> findByLotIdAndClientTargetId(long lotId, long clientTargetId);

/**
 * Retrieves the client feedback based on the given lot id and client author id.
 *
 * @param lotId          the id of the lot
 * @param clientAuthorId the id of the client author
 * @return the client feedback, if found
 */
Optional<ClientFeedbackDto> findByLotIdAndClientAuthorId(long lotId, long clientAuthorId);

/**
 * Retrieves a list of client feedback based on the given seller id.
 *
 * @param clientId the id of the seller
 * @return a list of client feedback
 */
List<ClientFeedbackDto> findAllBySellerId(long clientId);
}
