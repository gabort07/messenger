package org.progmatic.messenger.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class sqlController {
    private Connection myCon;

    public sqlController() {
        myCon = getConnection();
    }

    private Connection getConnection() {
        Connection myCon = null;
        try {
            String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "nemtudom11");
            System.out.println("Connection ready");
            myCon = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return myCon;
    }
}
