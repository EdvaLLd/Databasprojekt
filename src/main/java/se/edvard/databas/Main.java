package se.edvard.databas;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseProgram dbp = new DatabaseProgram();

        dbp.start();
        dbp.end();
        //WorkRoleDAOImpl dao = new WorkRoleDAOImpl();

        //lägg till roll
        /*WorkRole newRole = new WorkRole("programmer", "programs things", 98, java.sql.Date.valueOf("2022-06-01"));
        try {
            dao.insertWorkRole(newRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        //hämta alla
        /*ArrayList<WorkRole> allRoles = dao.getAllWorkRoles();
        for (WorkRole role : allRoles) {
            System.out.println(role);
        }*/

        //hämta en
        //System.out.println(dao.getWorkRoleById(1));

        //Update
        /*WorkRole wr = dao.getWorkRoleById(1);
        System.out.println(wr);
        wr.setDescription("Draws pretty things");
        dao.updateWorkRole(1, wr);
        System.out.println(dao.getWorkRoleById(1));*/

        //ta bort
        //dao.deleteWorkRole(0);

        //dao.closeConnection();
    }
}
