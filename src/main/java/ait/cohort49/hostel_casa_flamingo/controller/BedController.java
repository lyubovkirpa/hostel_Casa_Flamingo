package ait.cohort49.hostel_casa_flamingo.controller;


import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beds")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    // POST  /beds
    @PostMapping
    public Bed saveBed(@RequestBody Bed bed) {
        return bedService.saveBed(bed);
    }

    // GET /beds/id
    @GetMapping("/{id}")
    public Bed getById(@PathVariable("id") Long id) {
        return bedService.getBedById(id);
    }

    // GET /beds
    @GetMapping()
    public List<Bed> getAll() {
        return bedService.getAllActiveBeds();
    }

    // PUT  /beds
    @PostMapping("/{id}")
    public Bed update(@PathVariable Long id, @RequestBody Bed bed) {
        return bedService.updateBed(id, bed);
    }

    //DELETE  /beds/id
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        bedService.deleteBedById(id);
    }

}
