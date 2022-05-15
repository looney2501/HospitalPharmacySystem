
import controller.LoginController;
import javafx.application.Application;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.MedicationRepository;
import repository.OrderRepository;
import repository.UserRepository;
import services.Services;

import java.io.IOException;

/**
 * 
 */
public class MainClass extends Application {

    /**
     * 
     */
    @Override
    public void start(javafx.stage.Stage primaryStage) {
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
        OrderRepository orderRepository = new OrderRepository(sessionFactory);
        MedicationRepository medicationRepository = new MedicationRepository(sessionFactory);
        return new Services(userRepository, medicationRepository, orderRepository);
    }
}