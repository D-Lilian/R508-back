/* (C)2025 */
package com.course.backend.controller;

import com.course.backend.dto.CourseStatisticsImpl;
import com.course.backend.dto.GlobalStatistics;
import com.course.backend.dto.StudentSemesterStatisticsImpl;
import com.course.backend.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Stat Controller
 */
@Tag(name = "Stats")
@RestController
@RequestMapping("/api")
public class StatController {

    private final StatService statService;

    /**
     * Constructor
     *
     * @param statService Stat Service
     */
    public StatController(StatService statService) {
        this.statService = statService;
    }

    /**
     * Get global statistics
     *
     * @param academicYear Academic year
     * @return Global statistics
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Statistiques globales"),
                @ApiResponse(
                        responseCode = "401",
                        description = "Authentification échouée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Année académique non trouvée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Server error",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Récupère les statistiques globales")
    @GetMapping(value = "/stats/global", produces = "application/json")
    public ResponseEntity<GlobalStatistics> getGlobalStatistics(@RequestParam String academicYear) {
        try {
            return ResponseEntity.ok(statService.getGlobalStatistics(academicYear));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get course statistics
     *
     * @param id           Course ID
     * @param academicYear Academic year
     * @return Course statistics
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Statistiques du cours"),
                @ApiResponse(
                        responseCode = "401",
                        description = "Authentification échouée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Cours ou année académique non trouvée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Server error",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Récupère les statistiques d'un cours")
    @GetMapping(value = "/stats/course/{id}", produces = "application/json")
    public ResponseEntity<CourseStatisticsImpl> getCourseStatistics(
            @PathVariable Long id, @RequestParam String academicYear) {
        try {
            CourseStatisticsImpl statistics = statService.getCourseStatistics(id, academicYear);
            return ResponseEntity.ok(statistics);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get student semester statistics
     *
     * @param id           Student ID
     * @param academicYear Academic year
     * @return Student semester statistics
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Statistiques par semestre de l'étudiant"),
                @ApiResponse(
                        responseCode = "401",
                        description = "Authentification échouée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Etudiant ou année académique non trouvée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Server error",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Récupère les statistiques par semestre d'un étudiant")
    @GetMapping(value = "/stats/student/{id}", produces = "application/json")
    public ResponseEntity<List<StudentSemesterStatisticsImpl>> getStudentSemesterStatistics(
            @PathVariable Long id, @RequestParam String academicYear) {
        try {
            List<StudentSemesterStatisticsImpl> statistics =
                    statService.getStudentSemesterStatistics(id, academicYear);
            return ResponseEntity.ok(statistics);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
