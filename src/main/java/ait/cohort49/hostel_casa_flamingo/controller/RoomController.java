package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/rooms")
@Tag(name = "Room controller", description = "Controller for operations with rooms")

public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @Operation(summary = "Creates list of rooms with given input array", tags = {"room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = RoomDto.class))
            )
    })

    /**
     * Получить список всех комнат (GET /rooms).
     */
    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }


    @Operation(summary = "Get room by id", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = RoomDto.class))),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)
    })
    /**
     * Найти комнату по ID (GET /rooms/{id}).
     */
    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }


    @Operation(summary = "Create room", description = "Add new room", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't has rights",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User doesn't has rights"))
            )
    })
    /**
     * Создать новую комнату (POST/rooms).
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoomDto createRoom(@RequestBody CreateOrUpdateRoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }


    @Operation(summary = "Delete room", description = "ID of the room that needs to be deleted", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't has rights",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User doesn't has rights"))
            ),
    })
    /**
     * Удалить комнату (DELETE /rooms/{id}).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }

    /**
     * Получить общую стоимость кроватей по комнате (GET /rooms/{id}/total_price).
     */
    @GetMapping("/{id}/total_price")
    public BigDecimal getTotalBedPrice(@PathVariable Long id) {
        return roomService.getTotalBedPriceForRoom(id);
    }
}
