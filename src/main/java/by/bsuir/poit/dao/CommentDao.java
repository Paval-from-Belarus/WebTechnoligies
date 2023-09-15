package by.bsuir.poit.dao;

import by.bsuir.poit.dao.entities.Comment;
import by.bsuir.poit.dao.mappers.CommentJdbcMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@RequiredArgsConstructor
public class CommentDao {
private final @NotNull ConnectionPool pool;
private final @NotNull CommentJdbcMapper mapper;

public Optional<Comment> findById(@NotNull Integer id) throws DataAccessException {
      Optional<Comment> comment = Optional.empty();
      try (Connection connection = pool.getConnection();
	   PreparedStatement statement = connection.prepareStatement("select * from COMMENT where ID= ?")) {
	    statement.setInt(1, id);
	    ResultSet set = statement.executeQuery();
	    if (set.next()) { //try to fetch the first
		  comment = Optional.of(mapper.toComment(set));
	    }
	    set.close();//we can do nothing if this method fails
      } catch (SQLException e) {
	    throw new DataAccessException(e);
      }
      return comment;
}

public List<Comment> findByPublisherId(Integer publisherId) {
      return List.of();
}

public List<Comment> findByPostId(Integer postId) {
      return List.of();
}

public void save(Comment comment) {

}

public void update(Comment comment) {

}
}
