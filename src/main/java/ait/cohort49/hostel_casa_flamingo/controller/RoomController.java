package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rooms")
@Tag(name = "Room controller", description = "Controller for operations with rooms")

public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


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
            )
    })
    /**
     * Создать новую комнату (POST /rooms).
     */
    @PostMapping
    public RoomDto createRoom(@RequestBody CreateOrUpdateRoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }

    /**
     * Удалить комнату (DELETE /rooms/{id}).
     */
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
