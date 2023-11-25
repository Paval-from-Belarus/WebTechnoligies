package by.bsuir.poit.bean.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@FunctionalInterface
public interface ResultSetMapper<T> {
T fromResultSet(ResultSet set) throws SQLException;
}
