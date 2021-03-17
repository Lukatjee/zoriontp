package eu.lukatjee.zoriontp.sql;

import eu.lukatjee.zoriontp.ZorionTP;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static MySQL SQL;

    FileConfiguration configuration = ZorionTP.plugin.getConfig();

    // SQL login data

    private String host = configuration.getString("host");
    private String port = configuration.getString("port");
    private String database = configuration.getString("database");
    private String username = configuration.getString("username");
    private String password = configuration.getString("password");

    private String arguments = configuration.getString("arguments");

    private Connection connection;

    // Check if there is a connection

    public boolean isConnected() {

        return (connection != null);

    }

    // Connect if there's no connection

    public void connect() throws ClassNotFoundException, SQLException {

        if (!isConnected()) {

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?" + arguments, username, password);

        }

    }

    // Disconnect

    public void disconnect() {

        if (isConnected()) {

            try {

                connection.close();

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    // Get the connection

    public Connection getConnection() {

        return connection;

    }

}
