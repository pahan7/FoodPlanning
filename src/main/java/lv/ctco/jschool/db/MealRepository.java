package lv.ctco.jschool.db;

import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.Meal;
import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealRepository extends JpaRepository<Cafe, Integer> {

    @Query("select m from Meal m where m.cafe = ?1")
    List<Meal> findByCafe(Cafe cafe);
}
