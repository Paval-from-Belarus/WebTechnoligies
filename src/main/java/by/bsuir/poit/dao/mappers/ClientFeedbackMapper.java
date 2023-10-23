package by.bsuir.poit.dao.mappers;

import by.bsuir.poit.dao.entities.ClientFeedback;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface ClientFeedbackMapper {
ClientFeedbackMapper INSTANCE = Mappers.getMapper(ClientFeedbackMapper.class);

default ClientFeedback fromResultSet(ResultSet set) throws SQLException {
      return ClientFeedback.builder()
		 .id(set.getLong("client_feedback_id"))
		 .ranking(set.getDouble("ranking"))
		 .text(set.getString("text"))
		 .lotId(set.getLong("lot_id"))
		 .authorId(set.getLong("client_author_id"))
		 .targetId(set.getLong("client_target_id"))
		 .build();
}
}
