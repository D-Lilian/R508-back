/* (C)2025 */
package com.course.backend.controller;

import com.course.backend.model.Course;
import com.course.backend.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Course Controller
 */
@Tag(name = "Courses")
@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    /**
     * Constructor
     *
     * @param courseService Course Service
     */
    public CourseController(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    /**
     * Get all courses
     *
     * @return List of courses
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Liste des cours"),
                @ApiResponse(
                        responseCode = "401",
                        description = "Non authentifié",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erreur serveur",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Récupère la liste des cours")
    @GetMapping(value = "/courses", produces = "application/json")
    public ResponseEntity<List<Course>> getCourses() {
        try {
            return ResponseEntity.ok(courseService.getAllCourses());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a specific course
     *
     * @return a course
     */
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cours"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Non authentifié",
                            content =
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                    @io.swagger.v3.oas.annotations.media.Schema(
                                            hidden = true))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cours non trouvé",
                            content =
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                    @io.swagger.v3.oas.annotations.media.Schema(
                                            hidden = true))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erreur serveur",
                            content =
                            @io.swagger.v3.oas.annotations.media.Content(
                                    schema =
                                    @io.swagger.v3.oas.annotations.media.Schema(
                                            hidden = true)))
            })
    @Operation(summary = "Récupère un cours")
    @GetMapping(value = "/courses/{id}", produces = "application/json")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Add a course
     *
     * @param addCourse Course to add
     * @return Course
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Cours créé",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Données invalides",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "401",
                        description = "Non authentifié",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erreur serveur",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Crée un nouveau cours")
    @PostMapping(value = "/courses", produces = "application/json")
    public ResponseEntity<?> addCourse(@RequestBody Course addCourse) {
        try {
            courseService.addCourse(addCourse);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a course by ID
     *
     * @param id Course ID
     * @return Course
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Cours mis à jour",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Données invalides",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "401",
                        description = "Non authentifié",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Cours non trouvé",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erreur serveur",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Met à jour un cours existant")
    @PutMapping(value = "/courses/{id}", produces = "application/json")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id, @RequestBody Course updatedCourse) {
        try {
            courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete a course
     *
     * @param id Course ID
     * @return ResponseEntity
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Cours supprimé",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "401",
                        description = "Non authentifié",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Cours non trouvé",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "409",
                        description = "Le cours possède des notes associées",
                        content =
                        @io.swagger.v3.oas.annotations.media.Content(
                                schema =
                                @io.swagger.v3.oas.annotations.media.Schema(
                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erreur serveur",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Supprime un cours existant")
    @DeleteMapping(value = "/courses/{id}", produces = "application/json")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
