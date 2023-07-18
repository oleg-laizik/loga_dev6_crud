package loga.dev6.crud;

import loga.dev6.crud.dto.Client;
import loga.dev6.crud.service.ClientService;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class LauncherMain {

        public static void main(String[] args) {

            String url = "jdbc:mariadb://localhost:3306/myfirstdatabase";
            String user = "myuser";
            String password = "goitpass";

            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .load();


            flyway.migrate();
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                ClientService clientService = new ClientService(connection);

                long clientId = clientService.create("New Client");
                System.out.println("Created client ID: " + clientId);

                String clientName = clientService.getById(clientId);
                System.out.println("Client name: " + clientName);

                clientService.setName(clientId, "Updated Client");

                List<Client> clients = clientService.listAll();
                for (Client client : clients) {
                    System.out.println("Client ID: " + client.getId() + ", Name: " + client.getName());
                }

                clientService.deleteById(clientId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

