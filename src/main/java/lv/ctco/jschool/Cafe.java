package lv.ctco.jschool;

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
}
