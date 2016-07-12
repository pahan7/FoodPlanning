package lv.ctco.jschool.db;

import lv.ctco.jschool.entities.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Integer> {

}
