package bgu.dcr.az.lab.data;
// Generated May 30, 2012 7:41:57 PM by Hibernate Tools 3.2.1.GA



/**
 * Cpu generated by hbm2java
 */
public class Cpu  implements java.io.Serializable {


     private Integer id;
     private Users users;
     private String data;

    public Cpu() {
    }

	
    public Cpu(Users users) {
        this.users = users;
    }
    public Cpu(Users users, String data) {
       this.users = users;
       this.data = data;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public String getData() {
        return this.data;
    }
    
    public void setData(String data) {
        this.data = data;
    }




}

