package lv.ctco.jschool.entities;

import javax.persistence.*;

@Entity
public class Meal {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column(name = "mealName", nullable = false, unique = true)
    private String mealName;
    @Column
    private double price;

    private int cafeId;

    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
