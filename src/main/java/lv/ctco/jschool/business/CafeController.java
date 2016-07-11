package lv.ctco.jschool.business;

import lv.ctco.jschool.db.CafeRepository;
import lv.ctco.jschool.entities.Cafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    @Autowired
    CafeRepository cafeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> cafeGet() {
        List<Cafe> cafe = cafeRepository.findAll();
        return new ResponseEntity<>(cafe, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> cafePost(@RequestBody Cafe cafe) {
        cafeRepository.save(cafe);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
