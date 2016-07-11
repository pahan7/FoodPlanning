package lv.ctco.jschool.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cafe {

    @Id
    @GeneratedValue
    @Column
    private String cafeName;
    @Column
    private String phoneNr;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Meal> mealList = new ArrayList<>();

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}
