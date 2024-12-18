package se.edvard.databas;

public class Employee {
    int employeeId;
    String name;
    String email;
    String password;
    int roleId;

    public Employee(int employeeId, String name, String email, String password, int roleId) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
    public Employee(String name, String email, String password, int roleId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public int getEmployeeId() {return employeeId;}


    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }


        if (!(obj instanceof Employee e)) {
            return false;
        }

        return e.getEmployeeId() == this.employeeId;
    }
    public int hashCode()
    {
        return this.employeeId;
    }
    public String toString()
    {
        StringBuilder pswd = new StringBuilder();
        for(char _: password.toCharArray()){pswd.append("*");}
        return "Employee ID: " + employeeId + ", name: " + name + ", email: " + email + ", password: " + pswd + ", role ID: " + roleId;
    }
}
