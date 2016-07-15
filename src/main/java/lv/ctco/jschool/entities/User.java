package lv.ctco.jschool.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lv.ctco.jschool.validation.ValidEmail;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column
    private String firstName;

    @Column(name = "username")
    private String lastName;
    @Column(name = "pass")
    private String pass;
    @ValidEmail
    @Email
    @Column(name = "email")
    private String email;
    @Column
    private boolean orderIsMade;
    @Column
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserRole> userRole = new ArrayList<>();
//
//    public List<UserRole> getUserRoles() {
//        return userRoles;
//    }
//
//    public void setUserRoles(List<UserRole> userRoles) {
////        if (userRoles == null) return;
////        this.userRoles.clear();
////        this.userRoles.addAll(userRoles);
////        userRoles.forEach(u -> u.setUser(this));
//        this.userRoles = userRoles;
//    }


    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void  setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isOrderIsMade() {
        return orderIsMade;
    }

    public void setOrderIsMade(boolean orderIsMade) {
        this.orderIsMade = orderIsMade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
