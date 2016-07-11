
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

    @OneToMany(cascade = CascadeType.ALL)//, fetch = FetchType.EAGER)
    private List<Meal> mealList;

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

    public List<Meal> getMealList() {
        return mealList;}

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }


}
