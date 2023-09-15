package by.bsuir.poit.dao.mappers;

import by.bsuir.poit.dao.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Mapper
public interface CommentJdbcMapper {
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
