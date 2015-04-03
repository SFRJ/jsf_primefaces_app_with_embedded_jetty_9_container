import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.InetSocketAddress;

public class Main {



    public static void main(String[] args) throws Exception {
        String webappDirLocation = "./src/main/webapp/";
        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", 8080);
        Server server = new Server(address);

        Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration"
        );

        WebAppContext context = new WebAppContext();
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");
        context.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        context.setBaseResource(new ResourceCollection(new String[] { webappDirLocation, "./target" }));
        context.setResourceAlias("/WEB-INF/classes/", "/classes/");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        server.setStopAtShutdown(true);
        server.start();
        server.join();

    }


}
