package lv.ctco.jschool.repository;

import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    @Query("select m from Meal m where m.cafe = ?1")
    List<Meal> findByCafe(Cafe cafe);
}
