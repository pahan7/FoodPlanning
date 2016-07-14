package lv.ctco.jschool.entities;

import javax.persistence.*;

@Entity
public class Meal {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column(nullable = false,unique = true)
    private String mealName;
    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

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

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
