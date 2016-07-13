package lv.ctco.jschool.business;

import lv.ctco.jschool.db.CafeRepository;
import lv.ctco.jschool.entities.Cafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import static lv.ctco.jschool.Consts.*;

@RestController
@RequestMapping(CAFE_PATH)
public class CafeController {
    @Autowired
    CafeRepository cafeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCafes() {
        List<Cafe> cafe = cafeRepository.findAll();
        return new ResponseEntity<>(cafe, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetCafeById(@PathVariable("id") int id) {
        if (!cafeRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            Cafe cafe = cafeRepository.findOne(id);
            return new ResponseEntity<>(cafe, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postCafe(@RequestBody Cafe cafe, UriComponentsBuilder b) {
        cafeRepository.save(cafe);
        UriComponents uriComponents =
                b.path(CAFE_PATH + "/{id}").buildAndExpand(cafe.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCafeById(@PathVariable("id") int id) {
        if (!cafeRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            cafeRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putCafeById(@PathVariable("id") int id, @RequestBody Cafe cafe) {
        if (!cafeRepository.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            Cafe c = cafeRepository.getOne(id);
            c.setCafeName(cafe.getCafeName());
            c.setPhoneNr(cafe.getPhoneNr());
            c.setMealList(cafe.getMealList());
            cafeRepository.save(c);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
