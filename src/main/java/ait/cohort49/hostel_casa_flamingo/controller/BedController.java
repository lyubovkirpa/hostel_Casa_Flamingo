package ait.cohort49.hostel_casa_flamingo.controller;


import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beds")
@Tag(name = "Bed controller", description = "Controller for operations with beds")

public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @Operation(summary = "Create bed", description = "Add new bed", tags = {"Bed"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BedDto.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = BedDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't has rights",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User doesn't has rights"))
            )
    })

    /**
     * POST /beds
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BedDto saveBed(@RequestBody CreateBedDto createBedDto) {
        return bedService.saveBed(createBedDto);
    }

    @Operation(summary = "Get bed by id", tags = {"Bed"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = BedDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Bed not found", content = @Content)
    })

    /**
     * GET /beds/id
     */
    @GetMapping("/{id}")
    public BedDto getById(@PathVariable("id") Long id) {
        return bedService.getBedById(id);
    }


    @Operation(summary = "Creates list of beds with given input array", tags = {"bed"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = BedDto.class))
            )
    })
    /**
     * GET /beds
     */
    @GetMapping()
    public List<BedDto> getAll() {
        return bedService.getAllBeds();
    }


    @Operation(summary = "Delete bed", description = "ID of the bed that needs to be deleted", tags = {"bed"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = BedDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't has rights",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = String.class, example = "User doesn't has rights"))
            ),
    })
    /**
     * DELETE  /beds/id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        bedService.deleteBedById(id);
    }
}
