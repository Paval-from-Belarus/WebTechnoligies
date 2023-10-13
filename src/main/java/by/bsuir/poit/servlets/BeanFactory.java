package by.bsuir.poit.servlets;

import by.bsuir.poit.connections.ConnectionConfig;
import by.bsuir.poit.connections.ConnectionPool;
import by.bsuir.poit.dao.CommentDao;
import by.bsuir.poit.dao.GroupDao;
import by.bsuir.poit.dao.PostDao;
import by.bsuir.poit.dao.PublisherDao;
import by.bsuir.poit.dao.mappers.CommentJdbcMapper;
import jakarta.servlet.ServletContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.security.PublicKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
public class BeanFactory {
public BeanFactory() {
      this.pool = new ConnectionPool(defaultConfig());
      this.beanMap = new HashMap<>(Map.of(
	  CommentDao.class, new CommentDao(pool, CommentJdbcMapper.INSTANCE),
	  PublisherDao.class, new PublisherDao(pool),
	  GroupDao.class, new GroupDao(pool),
	  PostDao.class, new PostDao(pool)
      ));
}
//all beans are singleton
public @Nullable <T> T getBean(@NotNull Class<T> clazz) {
      return clazz.cast(beanMap.get(clazz));
}
public void setBeanDefinitions(ServletContext context) {
      for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
	    context.setAttribute(entry.getKey().getName(), entry.getValue());
      }
}
private ConnectionConfig defaultConfig() {
      ConnectionConfig config = ConnectionConfig.builder()
				    .maxPoolSize(DEFAULT_POOL_SIZE)
				    .jdbcUrl("jdbc:hsqldb:hsql://localhost/dummy-server")
				    .user("SA").password("")
				    .driverClassName("org.hsqldb.jdbc.JDBCDriver")
				    .build();
      return config;
}
public Path getResourcesPath() {
	return null;
}

public static final int DEFAULT_POOL_SIZE = 10;
private final ConnectionPool pool;
private final Map<Class<?>, Object> beanMap;
}
