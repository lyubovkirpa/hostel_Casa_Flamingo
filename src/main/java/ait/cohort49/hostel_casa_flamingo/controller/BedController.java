package ait.cohort49.hostel_casa_flamingo.controller;


import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beds")
public class BedController {

    // POST  /beds
    @PostMapping
    public Bed saveBed(@RequestBody Bed bed) {
        return bed;
    }

    // GET /beds/id
    @GetMapping("/{id}")
    public Bed getById(@PathVariable("id") Long id) {
        return null;
    }

    // GET /beds
    @GetMapping()
    public List<Bed> getAll() {
        return List.of();
    }

    // PUT  /beds
    @PostMapping("/{id}")
    public Bed update(@PathVariable Long id, @RequestBody Bed bed) {
        return bed;
    }

    //DELETE  /beds/id
    @DeleteMapping("/{id}")
    public Bed remove(@PathVariable Long id) {
        return null;
    }
}
