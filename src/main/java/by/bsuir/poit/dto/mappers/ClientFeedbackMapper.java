package by.bsuir.poit.dto.mappers;

import by.bsuir.poit.dto.ClientFeedback;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface ClientFeedbackMapper extends ResultSetMapper<ClientFeedback> {
@Override
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
