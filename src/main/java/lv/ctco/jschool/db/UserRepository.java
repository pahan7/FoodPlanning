package lv.ctco.jschool.db;

import lv.ctco.jschool.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

