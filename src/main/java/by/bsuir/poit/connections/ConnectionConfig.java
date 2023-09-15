package by.bsuir.poit.connections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConnectionConfig implements ConnectionConfigMXBean {
public static final int DEFAULT_POOL_SIZE = 1;
private volatile String user;
private volatile String password;
@Builder.Default
private int maxPoolSize = DEFAULT_POOL_SIZE;
private String jdbcUrl;
private String driverClassName;
}
