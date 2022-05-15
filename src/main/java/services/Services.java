package services;

import domain.entities.Medication;
import domain.entities.Order;
import domain.entities.User;
import domain.enums.OrderStatus;
import repository.MedicationRepository;
import repository.OrderRepository;
import repository.UserRepository;
import services.exceptions.ServicesException;
import services.validators.MedicationValidator;

import java.util.ArrayList;
import java.util.List;

public class Services {

    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;
    private final OrderRepository orderRepository;

    public Services(UserRepository userRepository, MedicationRepository medicationRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.medicationRepository = medicationRepository;
        this.orderRepository = orderRepository;
    }

    public User login(String username, String password) throws ServicesException {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new ServicesException("User does not exist!");
        } else {
            return user;
        }
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.getAll();
    }

    public void addMedication(String name, String producer, Integer stock, String description) {
        Medication medication = new Medication(name, producer, stock, description);
        MedicationValidator.validate(medication);
        medicationRepository.add(medication);
    }

    public void deleteMedication(Integer id) {
        if (id == null) {
            throw new ServicesException("ID cannot be null!");
        }
        Medication medication = medicationRepository.find(id);
        if (medication == null) {
            throw new ServicesException("Medication does not exist!");
        }
        medicationRepository.delete(medication);
    }

    public List<Order> getAllOrdersBySection(String section) {
        List<Order> allOrders = new ArrayList<>();
        allOrders.addAll(orderRepository.getAllOrdersByStatusAndSection(OrderStatus.Honored, section));
        allOrders.addAll(orderRepository.getAllOrdersByStatusAndSection(OrderStatus.PlacedPriority, section));
        allOrders.addAll(orderRepository.getAllOrdersByStatusAndSection(OrderStatus.Placed, section));
        allOrders.addAll(orderRepository.getAllOrdersByStatusAndSection(OrderStatus.Confirmed, section));
        allOrders.addAll(orderRepository.getAllOrdersByStatusAndSection(OrderStatus.Cancelled, section));
        return allOrders;
    }

}