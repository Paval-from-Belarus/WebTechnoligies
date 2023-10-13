package by.bsuir.poit;

import by.bsuir.poit.servlets.BeanFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;


/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
@WebServlet("app")
public class ApplicationController extends HttpServlet {
public static final int SERVER_PORT = 8888;

public static void main(String[] args) throws ServletException {
      BeanFactory factory = new BeanFactory();
//      DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(Main.class.getClassLoader())
//					  .setDeploymentName("api").setContextPath("/api")
//					  .addServlets(Servlets.servlet("myservlet",
//					      MainPage.class).addMapping("/myservlet"));
//      DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
//      manager.deploy();
//      ServletContextImpl context = manager.getDeployment().getServletContext();
//      factory.setBeanDefinitions(context);
//      ResourceHandler resourceHandler = new ResourceHandler(new PathResourceManager(Paths.get("/"), 100));
//      PathHandler path = Handlers.path(
//			      Handlers.redirect("index.html")) //the default main page that should be redirected by any attempt to server request
//			     .addPrefixPath("/api", manager.start())
//			     .addPrefixPath("/static", resourceHandler);
//      Undertow server = Undertow.builder()
//			    .addHttpListener(SERVER_PORT, "localhost")
//			    .setHandler(path)
//			    .build();
//
//      server.start();

}
}
