package by.bsuir.poit.dao;


import by.bsuir.poit.model.ClientFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
public interface ClientFeedbackRepository extends JpaRepository<ClientFeedback, Long> {
List<ClientFeedback> findAllByTargetClientId(long clientId);

List<ClientFeedback> findAllByAuthorClientId(long clientId);

List<ClientFeedback> findAllByLotId(long lotId);

Optional<ClientFeedback> findByIdAndAuthorClientId(long lotId, long authorId);

Optional<ClientFeedback> findByIdAndTargetClientId(long lotId, long targetId);

List<ClientFeedback> findAllByTargetClientIdOrderByRankingDesc(long clientId);

List<ClientFeedback> findAllByAuthorClientIdOrderByRankingDesc(long clientId);

}
