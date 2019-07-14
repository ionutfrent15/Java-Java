import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import utils.IServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StartServer {

    public static void main(String[] args) {
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}

