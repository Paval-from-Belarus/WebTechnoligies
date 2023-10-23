package by.bsuir.poit.dao.connections;

/**
 * This method can be used via JConsole
 * @author Paval Shlyk
 * @since 15/09/2023
 */
public interface ConnectionConfigMXBean {
String getUser();
void setUser(String user);
String getPassword();
void setPassword(String password);
int getMaxPoolSize();
void setMaxPoolSize(int poolSize);
}
