package by.bsuir.poit;

import by.bsuir.poit.dao.ConnectionConfig;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;


/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class Main {
public static final int SERVER_PORT = 8888;

public static void main(String[] args) throws ServletException {
      DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(Main.class.getClassLoader())
					  .setDeploymentName("myapp").setContextPath("/myapp")
					  .addServlets(Servlets.servlet("myservlet",
					      new HttpServlet() {
						    @Override
						    @SneakyThrows
						    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
							  response.getWriter().write("Hello World!");
						    }
					      }.getClass()).addMapping("/myservlet"));
      DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
      manager.deploy();
      PathHandler path = Handlers.path(Handlers.redirect("/myapp")).addPrefixPath("/myapp", manager.start());
      Undertow server = Undertow.builder().addHttpListener(SERVER_PORT, "localhost").setHandler(path).build();
      server.start();

}
}
