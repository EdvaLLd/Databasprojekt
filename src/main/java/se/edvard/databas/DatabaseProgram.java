package se.edvard.databas;

import java.sql.*;
import java.util.Scanner;

public class DatabaseProgram {
    private Scanner scanner;
    private WorkRoleDAOImpl dao;

    //dekonstruktor
    public void end()
    {
        scanner.close();
        dao.closeConnection();
    }
    //metoden som styr spel-loopen
    public void start()
    {
        scanner = new Scanner(System.in);
        dao = new WorkRoleDAOImpl();
        boolean run = true;

        while(run)
        {
            System.out.println("\n\n\n");
            System.out.println("Kommandon");
            System.out.println("avsluta: avslutar programmet");
            System.out.println("skapa roll: skapar en ny roll");
            System.out.println("skapa användare: skapar en ny användare");
            System.out.println("ta bort: tar bort en roll");
            System.out.println("visa alla: visar alla roller");
            System.out.println("visa en: visar en roll");
            System.out.println("uppdatera: uppdaterar en existerande roll");
            System.out.println("logga in: ser all information om en användare");
            String input = scanner.nextLine();
            switch (input)
            {
                case "avsluta" -> run=false;
                case "skapa roll" -> createRole();
                case "skapa användare" -> createUser();
                case "ta bort" -> removeRole();
                case "visa alla" -> showAllRoles();
                case "visa en" -> showRole();
                case "uppdatera" -> updateRole();
                case "logga in" -> logIn();
            }
        }
    }

    //skriver ut en text och returnerar anvädnarens
    private String queryInput(String query)
    {
        System.out.println(query);
        return scanner.nextLine().toLowerCase();
    }

    //gör en ny användare
    private void createUser()
    {
        String name = queryInput("Vad ska användaren heta?");
        String email = queryInput("Vad har användaren för email?");
        String password = queryInput("Vad har användaren för lösenord?");
        String roleId = queryInput("Vad har användaren för roll (id)");
        int roleIdInt;
        try{
            roleIdInt = Integer.parseInt(roleId);
        }catch(NumberFormatException e)
        {
            System.out.println("Du måste skriva in en siffra");
            return;
        }

        Connection conn = JDBCUtil.createConnection();
        String sql = "INSERT INTO employee (name, email, password, role_id)" +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try{
            pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4, roleIdInt);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);
        }
        catch(SQLException e){
            JDBCUtil.rollback(conn);
            e.printStackTrace();
        }
        finally{
            JDBCUtil.closeStatement(pstmt);
        }
    }

    //gör en ny roll
    private void createRole()
    {
        String title = queryInput("Vad är yrkets titel?");
        String desc = queryInput("Beskriv yrket");
        String salary = queryInput("Vad är yrkets lön?");
        String cDate = queryInput("När skapades yrket (åååå-mm-dd)");
        WorkRole wr = null;
        try {
            wr = new WorkRole(title, desc, Integer.parseInt(salary), java.sql.Date.valueOf(cDate));
            try {
                dao.insertWorkRole(wr);
            } catch (SQLException e) {
                System.out.println("Något gick fel vid implementationen av arbetsrollen!");
            }
        } catch (IllegalArgumentException e)
        {
            System.out.println("Någon av inputsen är i felaktig form");
        }
    }

    //tar bort en roll
    private void removeRole()
    {
        String id = queryInput("Vilket rollID vill du ta bort?");
        try {
            dao.deleteWorkRole(Integer.parseInt(id));
        } catch(SQLException e) {
            System.out.println("Något gick fel");
        }catch (IllegalArgumentException e) {
            System.out.println("RollIDt måste vara en siffra!");
        }
    }
    //visar alla roller
    private void showAllRoles()
    {
        try {
            for(WorkRole wr: dao.getAllWorkRoles())
            {
                System.out.println(wr);
            }
        } catch (SQLException e) {
            System.out.println("Något gick fel");
        }
    }

    //visar en roll
    private void showRole()
    {
        String id = queryInput("Vilket rollID vill du visa?");
        try {
            System.out.println(dao.getWorkRoleById(Integer.parseInt(id)));
        } catch (SQLException e) {
            System.out.println("Något gick fel vid hämntningen av rollen; antagligen skrevs fel id in");
        } catch(IllegalArgumentException e)
        {
            System.out.println("IDt måste vara en siffra");
        }
    }

    //uppdaterar en roll
    private void updateRole()
    {
        String id = queryInput("Vilket rollID vill du ändra?");

        System.out.println("Vill du uppdatera \"hela\" rollen eller bara \"ett\" fält?");
        if(scanner.nextLine().equals("hela"))
        {
            try {
                if (dao.getWorkRoleById(Integer.parseInt(id)) == null) {
                    System.out.println("Det finns ingen roll med det IDt");
                    return;
                }
            }catch(IllegalArgumentException e)
            {
                System.out.println("IDt måste vara en siffra");
                return;
            } catch (SQLException e) {
                System.out.println("något gick fel");
            }
            String title = queryInput("Vad är yrkets nya titel?");
            String desc = queryInput("Beskriv yrket");
            String salary = queryInput("Vad är yrkets nya lön?");
            String cDate = queryInput("När skapades det nya yrket (åååå-mm-dd)");
            WorkRole wr = null;
            try{
                wr = new WorkRole(title, desc, Integer.parseInt(salary),java.sql.Date.valueOf(cDate));
                try {
                    dao.updateWorkRole(Integer.parseInt(id), wr);
                } catch (SQLException e) {
                    System.out.println("Rollen existerar inte!");
                } catch (NumberFormatException e)
                {
                    System.out.println("Du måste skriva in en siffra");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Någon av inputsen har fel format");
            }
        }
        else
        {
            WorkRole newWr = null;
            try {
                newWr = dao.getWorkRoleById(Integer.parseInt(id));
            } catch (Exception e) {
                System.out.println("Rollen existerar inte!");
                return;
            }
            if(newWr == null)
            {
                System.out.println("Rollen existerar inte!");
            }
            else
            {
                String field = queryInput("Vilket fält vill du uppdatera? (titel, beskrivning, lön, datum)");
                String newValue = queryInput("Vad är fältets nya värde?");
                switch (field) {
                    case "titel" -> newWr.setTitle(newValue);
                    case "beskrivning" -> newWr.setDescription(newValue);
                    case "lön" -> {
                        try {
                            newWr.setSalary(Integer.parseInt(newValue));
                        }catch(IllegalArgumentException e)
                        {
                            System.out.println("Värdet måste vara en siffra!");
                            return;
                        }
                    }
                    case "datum" -> {
                        try{newWr.setCreationDate(java.sql.Date.valueOf(newValue));
                        }catch(IllegalArgumentException e){
                            System.out.println("Värdet måste vara i formen ÅÅÅÅ-MM-DD");
                        }
                    }
                    default -> System.out.println("du skrev inte in ett existerande fält");
                }
                try {
                    dao.updateWorkRole(Integer.parseInt(id), newWr);
                } catch (SQLException e) {
                    System.out.println("Något gick fel vid uppdateringen av arbetsrollen!");
                } catch(IllegalArgumentException e)
                {
                    System.out.println("IDt måste vara en siffra!");
                }
            }
        }
    }

    //logga in på en användare och se dens information
    private void logIn()
    {
        String name = queryInput("Email på kontot du vill logga in på: ");
        String password = queryInput("Lösenord: ");


        Connection conn = JDBCUtil.createConnection();
        String sql = "SELECT * FROM employee e JOIN work_role w ON e.role_id = w.role_id WHERE email = ? AND password = ?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try{
            pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            resultSet = pstmt.executeQuery();

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    System.out.print(rsmd.getColumnName(i)+ ": " + resultSet.getString(i));
                }
                System.out.println("");
            }
        }
        catch(SQLException e){
            JDBCUtil.rollback(conn);
            e.printStackTrace();
        }
        finally{
            JDBCUtil.closeResultSet(resultSet);
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
