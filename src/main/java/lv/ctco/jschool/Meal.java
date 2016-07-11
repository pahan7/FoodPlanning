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
    private Order order;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
