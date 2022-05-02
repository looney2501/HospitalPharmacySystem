package services;

import domain.entities.Medication;
import domain.entities.User;
import domain.enums.UserType;
import repository.MedicationRepository;
import repository.UserRepository;
import services.exceptions.ServicesException;
import services.validators.MedicationValidator;

import java.util.List;

/**
 * 
 */
public class Services {

    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;

    public Services(UserRepository userRepository, MedicationRepository medicationRepository) {
        this.userRepository = userRepository;
        this.medicationRepository = medicationRepository;
    }

    /**
     * @param username 
     * @param password
     */
    public User login(String username, String password) throws ServicesException {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new ServicesException("User does not exist!");
        }
        else {
            return user;
        }
    }

    /**
     * @return
     */
    public List<Medication> getAllMedications() {
        return medicationRepository.getAll();
    }

    /**
     * @param name 
     * @param producer 
     * @param stock 
     * @param description
     */
    public void addMedication(String name, String producer, Integer stock, String description) {
        Medication medication = new Medication(name, producer, stock, description);
        MedicationValidator.validate(medication);
        medicationRepository.add(medication);
    }

}