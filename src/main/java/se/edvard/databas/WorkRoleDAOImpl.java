package se.edvard.databas;

import java.sql.*;
import java.util.ArrayList;

public class WorkRoleDAOImpl implements WorkRoleDAO{
    private Connection conn = null;

    public WorkRoleDAOImpl() {
        conn = JDBCUtil.createConnection();
    }

    public void closeConnection() {
        JDBCUtil.closeConnection(conn);
    }

    @Override
    public void insertWorkRole(WorkRole workRole) throws SQLException {
        String sql = "INSERT INTO work_role (title, description, salary, creation_date)" +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try{
            pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, workRole.getTitle());
            pstmt.setString(2, workRole.getDescription());
            pstmt.setDouble(3, workRole.getSalary());
            pstmt.setDate(4, workRole.getCreationDate());
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);
        }
        catch(SQLException e){
            JDBCUtil.rollback(conn);
            e.printStackTrace();
            throw e;
        }
        finally{
            JDBCUtil.closeStatement(pstmt);
        }

    }

    @Override
    public ArrayList<WorkRole> getAllWorkRoles() throws SQLException {
        ArrayList<WorkRole> workRoles = new ArrayList<>();
        String sql = "SELECT * FROM work_role";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement(sql);rs = pstmt.executeQuery();
            while(rs.next())
            {
                workRoles.add(new WorkRole(rs.getInt("role_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("salary"), rs.getDate("creation_date")));
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
        finally{
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return workRoles;
    }

    @Override
    public WorkRole getWorkRoleById(int id) throws SQLException {
        WorkRole workRole = null;
        String sql = "SELECT * FROM work_role WHERE role_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                workRole = new WorkRole(rs.getInt("role_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("salary"), rs.getDate("creation_date"));
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
        finally{
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return workRole;
    }

    @Override
    public void updateWorkRole(int id, WorkRole newWorkRole) throws SQLException {
        String sql = "UPDATE work_role SET title = ?, description = ?, salary = ?, creation_date = ? WHERE role_id = ?";
        PreparedStatement pstmt = null;

        try{
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newWorkRole.getTitle());
            pstmt.setString(2, newWorkRole.getDescription());
            pstmt.setDouble(3, newWorkRole.getSalary());
            pstmt.setDate(4, newWorkRole.getCreationDate());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);
        } catch(SQLException e){
            JDBCUtil.rollback(conn);
            e.printStackTrace();
            throw e;
        }
        finally{
            JDBCUtil.closeStatement(pstmt);
        }
    }

    @Override
    public void deleteWorkRole(int id) throws SQLException {
        String sql = "DELETE FROM work_role WHERE role_id = ?";
        PreparedStatement pstmt = null;

        try{
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);
        } catch(SQLException e){
            JDBCUtil.rollback(conn);
            e.printStackTrace();
            throw e;
        }
        finally{
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
