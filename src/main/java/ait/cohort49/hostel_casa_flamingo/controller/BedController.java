package ait.cohort49.hostel_casa_flamingo.controller;


import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.service.BedCreateDto;
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


    @PostMapping
    public BedDto saveBed(@RequestBody BedCreateDto bedCreateDto) {
        return bedService.saveBed(bedCreateDto);
    }

    /**
     * GET /beds/id
     */

    @GetMapping("/{id}")
    public BedDto getById(@PathVariable("id") Long id) {
        return bedService.getBedById(id);
    }

    /**
     * GET /beds
     */

    @GetMapping()
    public List<BedDto> getAll() {
        return bedService.getAllBeds();
    }

    /**
     * DELETE  /beds/id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        bedService.deleteBedById(id);
    }

}
