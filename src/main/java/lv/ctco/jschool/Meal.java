package lv.ctco.jschool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Meal {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String mealName;


}
