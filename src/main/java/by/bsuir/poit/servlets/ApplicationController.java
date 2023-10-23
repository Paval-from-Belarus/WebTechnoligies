package by.bsuir.poit.servlets;

import by.bsuir.poit.context.BeanFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;



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
