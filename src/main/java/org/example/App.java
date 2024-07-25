package org.example;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final CarDAO carDAO = new CarDAOImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserChoice();
            executeChoice(choice);
        }
    }

    private static void showMenu() {
        System.out.println("1. Add Car");
        System.out.println("2. View Car");
        System.out.println("3. View All Cars");
        System.out.println("4. Update Car");
        System.out.println("5. Delete Car");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getUserChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static void executeChoice(int choice) {
        switch (choice) {
            case 1 -> addCar();
            case 2 -> viewCar();
            case 3 -> viewAllCars();
            case 4 -> updateCar();
            case 5 -> deleteCar();
            case 6 -> {
                System.exit(0);
            }
            default -> System.out.println("Invalid choice");
        }
    }

    private static void addCar() {
        Car car = getCarDetailsFromUser();
        carDAO.addCar(car);
        System.out.println("Car added successfully");
    }

    private static void viewCar() {
        System.out.print("Enter car ID: ");
        int id = scanner.nextInt();
        Car car = carDAO.getCarById(id);
        if (car != null) {
            System.out.println(car);
        } else {
            System.out.println("Car not found");
        }
    }

    private static void viewAllCars() {
        List<Car> cars = carDAO.getAllCars();
        cars.forEach(System.out::println);
    }

    private static void updateCar() {
        System.out.print("Enter car ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Car car = carDAO.getCarById(id);
        if (car != null) {
            updateCarDetails(car);
            carDAO.updateCar(car);
            System.out.println("Car updated successfully");
        } else {
            System.out.println("Car not found");
        }
    }

    private static void updateCarDetails(Car car) {
        System.out.println("Enter new details (leave blank to keep current value):");
        car.setMake(getUpdatedValue("Make", car.getMake()));
        car.setModel(getUpdatedValue("Model", car.getModel()));
        car.setYear(Integer.parseInt(getUpdatedValue("Year", String.valueOf(car.getYear()), true)));
        car.setVin(getUpdatedValue("VIN", car.getVin()));
        car.setLicensePlate(getUpdatedValue("License Plate", car.getLicensePlate()));
        car.setOwnerName(getUpdatedValue("Owner Name", car.getOwnerName()));
        car.setOwnerPhoneNumber(getUpdatedValue("Owner Phone Number", car.getOwnerPhoneNumber()));
        car.setServiceDate(getUpdatedValue("Service Date", car.getServiceDate()));
        car.setServiceDetails(getUpdatedValue("Service Details", car.getServiceDetails()));
    }

    private static String getUpdatedValue(String field, String currentValue) {
        return getUpdatedValue(field, currentValue, false);
    }

    private static String getUpdatedValue(String field, String currentValue, boolean isInteger) {
        System.out.printf("%s (%s): ", field, currentValue);
        String newValue = scanner.nextLine();
        return newValue.isEmpty() ? currentValue : (isInteger ? String.valueOf(Integer.parseInt(newValue)) : newValue);
    }

    private static void deleteCar() {
        System.out.print("Enter car ID: ");
        int id = scanner.nextInt();
        carDAO.deleteCar(id);
        System.out.println("Car deleted successfully");
    }

    private static Car getCarDetailsFromUser() {
        System.out.println("Enter car details:");
        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("VIN: ");
        String vin = scanner.nextLine();
        System.out.print("License Plate: ");
        String licensePlate = scanner.nextLine();
        System.out.print("Owner Name: ");
        String ownerName = scanner.nextLine();
        System.out.print("Owner Phone Number: ");
        String ownerPhoneNumber = scanner.nextLine();
        System.out.print("Service Date: ");
        String serviceDate = scanner.nextLine();
        System.out.print("Service Details: ");
        String serviceDetails = scanner.nextLine();

        return new Car(0, make, model, year, vin, licensePlate, ownerName, ownerPhoneNumber, serviceDate, serviceDetails);
    }
}