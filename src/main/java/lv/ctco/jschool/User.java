package lv.ctco.jschool;

import lv.ctco.jschool.Order;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    @Column
    private int id;

    private String firstName;

    @Column
    private Order order;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
