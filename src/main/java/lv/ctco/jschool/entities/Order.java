
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
    private Meal userMeal;

    @OneToOne
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

    public Meal getUserMeal() {
        return userMeal;}

    public void setUserMeal(Meal meal) {
        this.userMeal = meal;
    }


}
