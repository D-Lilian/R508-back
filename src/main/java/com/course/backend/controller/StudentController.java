/* (C)2025 */
package com.course.backend.controller;

import com.course.backend.model.Student;
import com.course.backend.request.StudentAddRequest;
import com.course.backend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Student Controller
 */
@Tag(name = "Students")
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    /**
     * Constructor
     *
     * @param studentService Student Service
     */
    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

    /**
     * Get all students
     *
     * @return List of students
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Liste des étudiants"),
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
    @Operation(summary = "Récupère la liste des étudiants")
    @GetMapping(value = "/students", produces = "application/json")
    public ResponseEntity<List<Student>> getStudents() {
        try {
            return ResponseEntity.ok(studentService.getAllStudents());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Add a student
     *
     * @param addStudent Student to add
     * @return Response
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Étudiant créé",
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
    @Operation(summary = "Crée un nouvel étudiant")
    @PostMapping(value = "/students", produces = "application/json")
    public ResponseEntity<?> addStudent(@RequestBody StudentAddRequest addStudent) {
        try {
            studentService.addStudent(addStudent);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a student by id
     *
     * @param id Student id
     * @return Student
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Etudiant trouvé"),
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
                        description = "Etudiant non trouvé",
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
    @Operation(summary = "Récupère un étudiant par son id")
    @GetMapping(value = "/students/{id}", produces = "application/json")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.getStudentById(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
