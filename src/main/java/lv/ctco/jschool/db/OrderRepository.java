package lv.ctco.jschool.db;

import lv.ctco.jschool.entities.Order;
import lv.ctco.jschool.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.user = ?1")
    List<Order> findByUser(User user);
}
