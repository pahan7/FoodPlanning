package lv.ctco.jschool.controllers;

import lv.ctco.jschool.entities.CafeDTO;
import lv.ctco.jschool.repository.CafeRepository;
import lv.ctco.jschool.entities.Cafe;
import lv.ctco.jschool.entities.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;

import static lv.ctco.jschool.Consts.*;

@RestController
@CrossOrigin
@RequestMapping(CAFE_PATH)
@Secured("ROLE_ADMIN")
public class CafeController {
    @Autowired
    CafeRepository cafeRepository;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCafes() {
        List<Cafe> cafe = cafeRepository.findAll();
        return new ResponseEntity<>(cafe, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> createCafe(@ModelAttribute CafeDTO cafeDTO, UriComponentsBuilder b) {
       // if (cafeRepository.findByName(cafe.getCafeName()) == ) {
        Cafe cafe = new Cafe();
            cafe.setCafeName(cafeDTO.getCafeTitle());
            cafe.setPhoneNr(cafeDTO.getPhoneNr());
            cafeRepository.save(cafe);
            UriComponents uriComponents =
                    b.path(CAFE_PATH + "/{id}").buildAndExpand(cafe.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
