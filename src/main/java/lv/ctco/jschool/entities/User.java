package lv.ctco.jschool.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lv.ctco.jschool.validation.ValidEmail;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    @ValidEmail
    private String email;
    private boolean orderIsMade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserRoles> userRoles = new ArrayList<>();

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        if (userRoles == null) return;
        this.userRoles.clear();
        this.userRoles.addAll(userRoles);
        userRoles.forEach(u -> u.setUser(this));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
