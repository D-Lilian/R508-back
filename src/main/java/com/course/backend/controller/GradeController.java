/* (C)2025 */
package com.course.backend.controller;

import com.course.backend.model.StudentGrade;
import com.course.backend.model.StudentInfo;
import com.course.backend.request.GradeAddRequest;
import com.course.backend.response.GradeGetResponse;
import com.course.backend.service.GradeService;
import com.course.backend.service.PDFService;
import com.course.backend.service.StudentService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Grade Controller
 */
@Tag(name = "Grades")
@RestController
@RequestMapping("/api")
public class GradeController {

    private final GradeService gradeService;
    private final PDFService pdfService;
    private final StudentService studentService;

    public GradeController(
            GradeService gradeService, PDFService pdfService, StudentService studentService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.pdfService = pdfService;
    }

    /**
     * Get all grades
     *
     * @return List of grades
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Liste des notes avec détails"),
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
    @Operation(summary = "Récupère toutes les notes")
    @GetMapping(value = "/grades", produces = "application/json")
    public ResponseEntity<List<GradeGetResponse>> getGrades() {
        try {
            return ResponseEntity.ok(gradeService.getAllGrades());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a specific grade
     *
     * @return a grade
     */
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Liste des notes avec détails"),
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
                            description = "Grade non trouvé",
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
    @Operation(summary = "Récupère toutes les notes")
    @GetMapping(value = "/grades/{id}", produces = "application/json")
    public ResponseEntity<GradeGetResponse> getGrade(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gradeService.getGrade(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all grades for a student
     *
     * @param id Student ID
     * @return List of grades for the student
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Liste des notes d'un étudiant"),
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
                        description = "Etudiant non trouvée",
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
    @Operation(summary = "Récupère toutes les notes d'un étudiant")
    @GetMapping(value = "/grades/student/{id}", produces = "application/json")
    public ResponseEntity<List<GradeGetResponse>> getGradesByStudent(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gradeService.getGradesByStudent(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Add a new grade
     *
     * @param addGrade Grade to add
     * @return Response
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Note créée",
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
                        responseCode = "409",
                        description = "Note déjà présente",
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
    @Operation(summary = "Ajoute une nouvelle note")
    @PostMapping(value = "/grades", produces = "application/json")
    public ResponseEntity<?> addGrade(@RequestBody GradeAddRequest addGrade) {
        try {
            gradeService.addGrade(
                    addGrade.getStudentId(),
                    addGrade.getCourseId(),
                    addGrade.getGrade(),
                    addGrade.getSemester(),
                    addGrade.getAcademicYear());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update a grade
     *
     * @param id          Grade ID
     * @param updatedGrade Updated grade
     * @return Response
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Note mise à jour",
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
                        description = "Note non trouvée",
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
    @Operation(summary = "Met à jour une note")
    @PutMapping(value = "/grades/{id}", produces = "application/json")
    public ResponseEntity<?> updateGrade(@PathVariable Long id, @RequestBody double updatedGrade) {
        if (updatedGrade < 0 || updatedGrade > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            gradeService.updateGrade(id, updatedGrade);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete a grade
     *
     * @param id Grade ID
     * @return Response
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Note supprimée",
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
                        description = "Note non trouvée",
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
    @Operation(summary = "Supprime une note")
    @DeleteMapping(value = "/grades/{id}", produces = "application/json")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            gradeService.deleteGrade(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generate transcript for a student for a specific academic year
     *
     * @param studentId    Student ID
     * @param academicYear Academic year
     * @return PDF file
     */
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Transcript generated successfully"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Student or grades not found",
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
    @Operation(summary = "Generate transcript for a student for a specific academic year")
    @GetMapping(value = "/grades/student/{studentId}/transcript", produces = "application/pdf")
    public ResponseEntity<byte[]> generateTranscript(
            @PathVariable Long studentId, @RequestParam String academicYear) {
        try {
            List<StudentGrade> grades =
                    gradeService.getGradesByStudentAndAcademicYear(studentId, academicYear);
            if (grades.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            StudentInfo studentInfo = studentService.getStudentInfo(studentId);

            byte[] pdfBytes = pdfService.generateTranscript(studentInfo, grades, academicYear);
            HttpHeaders headers = new HttpHeaders();
            headers.add(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"releve_notes_"
                            + studentInfo.getFirstName()
                            + "_"
                            + studentInfo.getLastName()
                            + "_"
                            + academicYear
                            + ".pdf\"");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (DocumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
