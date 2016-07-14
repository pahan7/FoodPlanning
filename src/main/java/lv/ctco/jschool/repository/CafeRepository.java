package lv.ctco.jschool.repository;

import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CafeRepository extends JpaRepository<Cafe, Integer> {

    Cafe findByName(String email);
}
