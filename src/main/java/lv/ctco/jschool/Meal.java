package lv.ctco.jschool;

import javax.persistence.*;

@Entity
public class Meal {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String mealName;

    @ManyToOne
    private Cafe cafe;

    @ManyToOne
    private Order order;

}
