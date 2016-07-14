
package lv.ctco.jschool.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MyOrder")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int orderId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> mealList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
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
        return mealList;
    }

    public void setMealList(Meal meal) {
        mealList.add(meal);
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}
