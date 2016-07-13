
package lv.ctco.jschool.entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MyOrder")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int orderId;

    @OneToOne(cascade = CascadeType.ALL)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Meal getMeal() {
        return meal;}

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
