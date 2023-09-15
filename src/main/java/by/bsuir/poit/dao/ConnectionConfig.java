package by.bsuir.poit.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@NoArgsConstructor
@Data
public class ConnectionConfig implements ConnectionConfigMXBean {
public static final int DEFAULT_CONNECTION_COUNT = 1;
private volatile String user;
private volatile String password;
private volatile int connectionCount = DEFAULT_CONNECTION_COUNT;
private int maxPoolSize;
private String jdbcUrl;
private String driverClassName;
}
