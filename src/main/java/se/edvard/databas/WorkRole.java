package se.edvard.databas;

public class WorkRole {
    private int roleID;
    private String title;
    private String description;
    private double salary;
    private java.sql.Date creationDate;
    public WorkRole(int roleID, String title, String description, double salary, java.sql.Date creationDate) {
        this.roleID = roleID;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }
    public WorkRole(String title, String description, double salary, java.sql.Date creationDate) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }
    public int getRoleID() {return roleID;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public double getSalary() {return salary;}
    public java.sql.Date getCreationDate() {return creationDate;}
    public void setRoleID(int roleID) {this.roleID = roleID;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setSalary(double salary) {this.salary = salary;}
    public void setCreationDate(java.sql.Date creationDate) {this.creationDate = creationDate;}

    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }


        if (!(obj instanceof WorkRole wr)) {
            return false;
        }

        return wr.getRoleID() == this.roleID;
    }
    public int hashCode()
    {
        return this.roleID;
    }
    public String toString()
    {
        return "ID: " + roleID + ", title: " + title + ", description: " + description + ", salary: " + salary + ", creationDate: " + creationDate;
    }
}
