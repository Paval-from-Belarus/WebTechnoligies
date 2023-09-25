package by.bsuir.poit.dao.mappers;

import by.bsuir.poit.dao.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * All mappers should be threadsafe and absolutely stateless
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Mapper
public interface CommentJdbcMapper {
CommentJdbcMapper INSTANCE = Mappers.getMapper(CommentJdbcMapper.class);
default Comment toComment(ResultSet set) throws SQLException {
      return Comment.builder()
			     .id(set.getInt("id"))
			     .postId(set.getInt("post_id"))
			     .publisherId(set.getInt("publisher_id"))
			     .contentPath(set.getString("content_path"))
			     .publishTime(set.getTimestamp("publish_time"))
			     .build();
}
}
