package se.edvard.databas;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static Properties properties = new Properties();

    static
    {
        try(InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            if(input == null)
            {
                throw new IOException("Unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties");
        }
    }

    public static Connection createConnection()
    {
        Driver hsqlDriver = new org.hsqldb.jdbcDriver();
        try {
            DriverManager.registerDriver(hsqlDriver);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void closeConnection(Connection conn)
    {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeStatement(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getDatabaseProductName(Connection conn)
    {
        DatabaseMetaData metadata = null;
        try {
            metadata = conn.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            return metadata.getDatabaseProductName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
