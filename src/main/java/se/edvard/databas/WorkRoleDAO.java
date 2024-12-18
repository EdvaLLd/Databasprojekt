package se.edvard.databas;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkRoleDAO {
    public void insertWorkRole(WorkRole workRole) throws SQLException;
    public ArrayList<WorkRole> getAllWorkRoles() throws SQLException;
    public WorkRole getWorkRoleById(int id) throws SQLException;
    public void updateWorkRole(int id, WorkRole newWorkRole) throws SQLException;
    public void deleteWorkRole(int id) throws SQLException;
}
