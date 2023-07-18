package loga.dev6.crud.service;

import loga.dev6.crud.dto.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String INSERT_SQL = "INSERT INTO client (name) VALUES (?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM client WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE client SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM client WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM client";

    private final Connection connection;

    public ClientService(Connection connection) {
        this.connection = connection;
    }

    public long create(String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    public String getById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    throw new SQLException("User with ID: " + id + " not found.");
                }
            }
        }
    }

    public void setName(long id, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    public void deleteById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public List<Client> listAll() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {

            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                clients.add(client);
            }
            return clients;
        }
    }
}
