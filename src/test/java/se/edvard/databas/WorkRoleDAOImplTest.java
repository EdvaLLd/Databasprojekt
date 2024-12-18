package se.edvard.databas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkRoleDAOImplTest {
    @AfterEach
    public void cleanUp()
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = JDBCUtil.createConnection();
            stmt = conn.prepareStatement("DROP TABLE IF EXISTS work_role");
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeStatement(stmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    @Test
    void insertWorkRoleTest() throws SQLException {
        WorkRoleDAOImpl dao = new WorkRoleDAOImpl();
        dao.insertWorkRole(new WorkRole("test", "desc", 1, java.sql.Date.valueOf("2024-12-12")));
        ArrayList<WorkRole> allWorkRoles = dao.getAllWorkRoles();

        Assertions.assertEquals( 1, allWorkRoles.size());
    }
}
