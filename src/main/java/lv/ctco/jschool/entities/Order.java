
package lv.ctco.jschool.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    @Column
    private boolean submited;
    @Column
    private Date submissionDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    public Order() {
        this.submited = false;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public boolean isSubmited() {
        return submited;
    }

    public void setSubmited(boolean submited) {
        this.submited = submited;
    }

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

    public boolean removeMeal(int mealId) {
        boolean isOK = false;
        Meal mealToDelete = null;
        //TODO //if no such meal??
        for (Meal m : mealList) {
            if (m.getId() == mealId) {
                mealToDelete = m;
            }
        }
        mealList.remove(mealToDelete);
        return isOK;

    }

    public void addMeal(Meal meal) {

        mealList.add(meal);
    }

    public void setMealList(Meal meal) {
        mealList.add(meal);
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}
