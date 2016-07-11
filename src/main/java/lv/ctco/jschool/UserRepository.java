package lv.ctco.jschool;

import lv.ctco.jschool.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

