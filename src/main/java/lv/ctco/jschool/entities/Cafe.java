package lv.ctco.jschool.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Cafe {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false,unique = true)
    private String name;

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
        return name;
    }

    public void setCafeName(String name) {
        this.name = name;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean addToMeals(Meal meal){
        Optional<Meal>mealExists = mealList.stream()
                .filter(m-> m.getMealName().equals(meal.getMealName()))
                .findAny();
        if (!mealExists.isPresent()){
            mealList.add(meal);
            return true;
        }
        return false;
    }
    public boolean deleteMeal(int mealId){
        Optional<Meal>mealExists = mealList.stream()
                .filter(m -> m.getId()==mealId)
                .findAny();
        if (mealExists.isPresent()){
            Meal mealToDelete = mealExists.get();
            mealList.remove(mealToDelete);
            return true;
        }
        return false;
    }

    public boolean updateMeal(Meal meal,int mealId){
        Optional<Meal>mealExists = mealList.stream()
                .filter(m -> m.getId() == mealId)
                .findAny();
        if (mealExists.isPresent()){
            Meal mealToUpdate = mealExists.get();
            mealToUpdate.setMealName(meal.getMealName());
            mealToUpdate.setPrice(meal.getPrice());
            mealToUpdate.setCafeId(meal.getCafeId());
            return true;
        }
        return false;
    }
}
