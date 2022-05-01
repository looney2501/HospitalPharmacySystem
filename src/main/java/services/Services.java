package services;

import domain.entities.User;
import domain.enums.UserType;
import repository.UserRepository;
import services.exceptions.ServicesException;

import javax.sql.rowset.serial.SerialException;
import java.util.*;

/**
 * 
 */
public class Services {

    private final UserRepository userRepository;

    public Services(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param username 
     * @param password
     */
    public UserType login(String username, String password) throws ServicesException {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new ServicesException("User does not exist!");
        }
        else {
            return user.getUserType();
        }
    }

    /**
     * 
     */
    public void getAllMedications() {
        // TODO implement here
    }

    /**
     * @param name 
     * @param producer 
     * @param stock 
     * @param description
     */
    public void addMedication(String name, String producer, String stock, String description) {
        // TODO implement here
    }

}