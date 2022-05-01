
import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.UserRepository;
import services.Services;

import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class MainClass extends Application {

    /**
     * 
     */
    @Override
    public void start(Stage primaryStage) {
        Services services = initializeServices();
        LoginController loginController = new LoginController(services, primaryStage);
        try {
            loginController.initiateLoginProcedure();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SessionFactory initializeSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return null;
    }

    private Services initializeServices() {
        SessionFactory sessionFactory = initializeSessionFactory();
        UserRepository userRepository = new UserRepository(sessionFactory);
        return new Services(userRepository);
    }
}