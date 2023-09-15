package by.bsuir.poit;

import by.bsuir.poit.servlets.BeanFactory;
import by.bsuir.poit.servlets.MainPage;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.spec.ServletContextImpl;
import jakarta.servlet.ServletException;


/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class Main {
public static final int SERVER_PORT = 8888;

public static void main(String[] args) throws ServletException {
      BeanFactory factory = new BeanFactory();
      DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(Main.class.getClassLoader())
					  .setDeploymentName("myapp").setContextPath("/myapp")
					  .addServlets(Servlets.servlet("myservlet",
					      MainPage.class).addMapping("/myservlet"));
      DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
      manager.deploy();
      ServletContextImpl context = manager.getDeployment().getServletContext();
      factory.setBeanDefinitions(context);
      PathHandler path = Handlers.path(Handlers.redirect("/myapp")).addPrefixPath("/myapp", manager.start());
      Undertow server = Undertow.builder().addHttpListener(SERVER_PORT, "localhost").setHandler(path).build();
      server.start();

}
}
