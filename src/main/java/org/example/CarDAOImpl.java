package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private static final String INSERT_CAR_SQL = "INSERT INTO Car (make, model, year, vin, licensePlate, ownerName, ownerPhoneNumber, serviceDate, serviceDetails) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_CAR_BY_ID = "SELECT * FROM Car WHERE id = ?";
    private static final String SELECT_ALL_CARS = "SELECT * FROM Car";
    private static final String UPDATE_CAR_SQL = "UPDATE Car SET make = ?, model = ?, year = ?, vin = ?, licensePlate = ?, ownerName = ?, ownerPhoneNumber = ?, serviceDate = ?, serviceDetails = ? WHERE id = ?";
    private static final String DELETE_CAR_SQL = "DELETE FROM Car WHERE id = ?";

    @Override
    public void addCar(Car car) {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_CAR_SQL)) {
            setCarParameters(stmt, car);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding car", e);
        }
    }

    @Override
    public Car getCarById(int id) {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CAR_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCar(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving car by ID", e);
        }
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_CARS)) {
            while (rs.next()) {
                cars.add(mapRowToCar(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all cars", e);
        }
        return cars;
    }

    @Override
    public void updateCar(Car car) {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_CAR_SQL)) {
            setCarParameters(stmt, car);
            stmt.setInt(10, car.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating car", e);
        }
    }

    @Override
    public void deleteCar(int id) {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_CAR_SQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting car", e);
        }
    }

    private void setCarParameters(PreparedStatement stmt, Car car) throws SQLException {
        stmt.setString(1, car.getMake());
        stmt.setString(2, car.getModel());
        stmt.setInt(3, car.getYear());
        stmt.setString(4, car.getVin());
        stmt.setString(5, car.getLicensePlate());
        stmt.setString(6, car.getOwnerName());
        stmt.setString(7, car.getOwnerPhoneNumber());
        stmt.setString(8, car.getServiceDate());
        stmt.setString(9, car.getServiceDetails());
    }

    private Car mapRowToCar(ResultSet rs) throws SQLException {
        return new Car(
                rs.getInt("id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getString("vin"),
                rs.getString("licensePlate"),
                rs.getString("ownerName"),
                rs.getString("ownerPhoneNumber"),
                rs.getString("serviceDate"),
                rs.getString("serviceDetails")
        );
    }
}