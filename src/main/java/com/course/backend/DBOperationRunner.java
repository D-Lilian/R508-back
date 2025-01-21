/* (C)2025 */
package com.course.backend;

import com.course.backend.model.Course;
import com.course.backend.model.Grade;
import com.course.backend.model.Professor;
import com.course.backend.model.Student;
import com.course.backend.repository.CourseRepository;
import com.course.backend.repository.GradeRepository;
import com.course.backend.repository.ProfessorRepository;
import com.course.backend.repository.StudentRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The DBOperationRunner class.
 * This class is used to populate the database with some example data.
 */
@Component
@Profile("!test")
public class DBOperationRunner implements CommandLineRunner {

    @Value("${database.populate.enabled:true}")
    private boolean populateEnabled;

    ProfessorRepository professorRepository;
    StudentRepository studentRepository;
    CourseRepository courseRepository;
    GradeRepository gradeRepository;
    PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new DB operation runner.
     *
     * @param professorRepository the professor repository
     * @param studentRepository   the student repository
     * @param courseRepository    the course repository
     * @param gradeRepository     the grade repository
     */
    public DBOperationRunner(
            ProfessorRepository professorRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            GradeRepository gradeRepository) {
        super();
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Run.
     * This method is used to populate the database with some example data.
     *
     * @param args the args
     */
    @Override
    public void run(String... args) {
        if (!populateEnabled) {
            return;
        }

        if (professorRepository.count() <= 0) {
            Professor professor =
                    new Professor(
                            "John",
                            "Doe",
                            "prof@example.com",
                            "Informatique",
                            "$2a$10$yfPLBKnYK/whjcSCiPYpsO8uY3bgu.O79UZ4pzpyPix0ppMAaUIwy");
            professor.setId(1L);
            professorRepository.save(professor);
        }

        if (studentRepository.count() <= 0) {
            List<Student> students =
                    Arrays.asList(
                            new Student(
                                    "Lilian",
                                    "Daurat",
                                    "lilian.daurat@gmail.com",
                                    LocalDate.of(2000, 01, 01),
                                    "i2200001"),
                            new Student(
                                    "Alexandre",
                                    "Grasteau",
                                    "alexandre.grasteau@gmail.com",
                                    LocalDate.of(2000, 01, 01),
                                    "i2200002"),
                            new Student(
                                    "Killian",
                                    "Le Henaff",
                                    "killian.le-henaff@gmail.com",
                                    LocalDate.of(2000, 01, 01),
                                    "i2200003"),
                            new Student(
                                    "Alan",
                                    "Pertue",
                                    "alan.pertue@gmail.com",
                                    LocalDate.of(2000, 01, 01),
                                    "i2200004"));
            long index = 1L;
            for (Student student : students) {
                student.setId(index++);
            }
            studentRepository.saveAll(students);
        }

        if (courseRepository.count() <= 0) {
            List<Course> courses =
                    Arrays.asList(
                            new Course(
                                    "R1.01",
                                    "Initiation au dev.",
                                    3,
                                    "Introduction to development"),
                            new Course(
                                    "R1.02",
                                    "Dev. interfaces web",
                                    3,
                                    "Development of web interfaces"),
                            new Course("R1.03", "Intro. archi.", 3, "Introduction to architecture"),
                            new Course("R1.04", "Intro. systèmes", 3, "Introduction to systems"),
                            new Course("R1.05", "Introduction BD", 3, "Introduction to databases"),
                            new Course("R1.06", "Maths discrètes", 3, "Discrete mathematics"),
                            new Course("R1.07", "Outils fondamentaux", 3, "Fundamental tools"),
                            new Course(
                                    "R1.08",
                                    "Intro Gestion orga.",
                                    3,
                                    "Introduction to organizational management"),
                            new Course("R1.09", "Intro. Économie", 3, "Introduction to economics"),
                            new Course("R1.10", "Anglais", 3, "English"),
                            new Course("R1.11", "Bases de la comm.", 3, "Basics of communication"),
                            new Course("R1.12", "PPP", 3, "Personal and professional project"),
                            new Course("S1.01", "Implémentation", 3, "Implementation"),
                            new Course(
                                    "S1.02", "Comparaison d'algo.", 3, "Comparison of algorithms"),
                            new Course(
                                    "S1.03", "Installation poste", 3, "Workstation installation"),
                            new Course("S1.04", "Création BD", 3, "Database creation"),
                            new Course("S1.05", "Recueil de besoins", 3, "Requirements gathering"),
                            new Course("S1.06", "Environnement éco.", 3, "Economic environment"),
                            new Course("S2.01", "Dév. d'application", 3, "Application development"),
                            new Course("S2.02", "Exploration algo.", 3, "Algorithm exploration"),
                            new Course(
                                    "S2.03",
                                    "Inst. services rés.",
                                    3,
                                    "Network services installation"),
                            new Course("S2.04", "Exploitation BD", 3, "Database exploitation"),
                            new Course("S2.05", "Gestion d'un projet", 3, "Project management"),
                            new Course("S2.06", "Travail d'équipe", 3, "Teamwork"),
                            new Course("P2.01", "Portfolio", 3, "Portfolio"),
                            new Course("R2.01", "Dev. objets", 3, "Object-oriented development"),
                            new Course(
                                    "R2.02",
                                    "Dev. d'apps avec IHM",
                                    3,
                                    "Development of applications with GUI"),
                            new Course("R2.03", "Qualité de dev.", 3, "Development quality"),
                            new Course(
                                    "R2.04",
                                    "Réseau & bas niveau",
                                    3,
                                    "Network and low-level programming"),
                            new Course("R2.05", "Services réseau", 3, "Network services"),
                            new Course("R2.06", "Exploitation BD", 3, "Database exploitation"),
                            new Course("R2.07", "Graphes", 3, "Graphs"),
                            new Course("R2.08", "Stats descriptives", 3, "Descriptive statistics"),
                            new Course("R2.09", "Méthodes numériques", 3, "Numerical methods"),
                            new Course(
                                    "R2.10",
                                    "Intro. GSI",
                                    3,
                                    "Introduction to information systems management"),
                            new Course("R2.11", "Droit", 3, "Law"),
                            new Course("R2.12", "Anglais", 3, "English"),
                            new Course("R2.13", "Comm. technique", 3, "Technical communication"),
                            new Course("R2.14", "PPP", 3, "Personal and professional project"),
                            new Course(
                                    "S3.A.01",
                                    "Développement appli",
                                    5,
                                    "Description for Développement appli"),
                            new Course("P3.01", "Portfolio", 5, "Description for Portfolio"),
                            new Course(
                                    "R3.01",
                                    "Développement web",
                                    5,
                                    "Description for Développement web"),
                            new Course(
                                    "R3.02", "Dév. efficace", 5, "Description for Dév. efficace"),
                            new Course("R3.03", "Analyse", 5, "Description for Analyse"),
                            new Course(
                                    "R3.04",
                                    "Qualité de développement",
                                    5,
                                    "Description for Qualité de développement"),
                            new Course(
                                    "R3.05", "Prog. système", 5, "Description for Prog. système"),
                            new Course(
                                    "R3.06", "Archi. Réseaux", 5, "Description for Archi. Réseaux"),
                            new Course(
                                    "R3.07",
                                    "SQL et programmation",
                                    5,
                                    "Description for SQL et programmation"),
                            new Course("R3.08", "Probabilités", 5, "Description for Probabilités"),
                            new Course(
                                    "R3.09", "Cryptographie", 5, "Description for Cryptographie"),
                            new Course(
                                    "R3.10", "Management SI", 5, "Description for Management SI"),
                            new Course(
                                    "R3.11",
                                    "Droit contrats et num.",
                                    5,
                                    "Description for Droit contrats et num."),
                            new Course("R3.12", "Anglais", 5, "Description for Anglais"),
                            new Course("R3.13", "Comm. pro.", 5, "Description for Comm. pro."),
                            new Course("R3.14", "PPP", 5, "Description for PPP"),
                            new Course(
                                    "S4.A.01",
                                    "Dév. d'application",
                                    5,
                                    "Description for Dév. d'application"),
                            new Course("S4.St", "Stage", 5, "Description for Stage"),
                            new Course("P4.01", "Portfolio", 5, "Description for Portfolio"),
                            new Course(
                                    "R4.01",
                                    "Architecture logicielle",
                                    5,
                                    "Description for Architecture logicielle"),
                            new Course(
                                    "R4.02",
                                    "Qualité de développement",
                                    5,
                                    "Description for Qualité de développement"),
                            new Course(
                                    "R4.03",
                                    "Qualité & non-relationnel",
                                    5,
                                    "Description for Qualité & non-relationnel"),
                            new Course(
                                    "R4.04",
                                    "Méthodes d'optimisation",
                                    5,
                                    "Description for Méthodes d'optimisation"),
                            new Course("R4.05", "Anglais", 5, "Description for Anglais"),
                            new Course(
                                    "R4.06", "Comm. interne", 5, "Description for Comm. interne"),
                            new Course("R4.07", "PPP", 5, "Description for PPP"),
                            new Course(
                                    "R4.A.08",
                                    "Virtualisation",
                                    5,
                                    "Description for Virtualisation"),
                            new Course(
                                    "R4.A.09",
                                    "Management avancé SI",
                                    5,
                                    "Description for Management avancé SI"),
                            new Course(
                                    "R4.A.10",
                                    "Complément web",
                                    5,
                                    "Description for Complément web"),
                            new Course(
                                    "R4.A.11",
                                    "Développement mobile",
                                    5,
                                    "Description for Développement mobile"),
                            new Course("R4.A.12", "Automates", 5, "Description for Automates"),
                            new Course("S5.A.01", "Dév. avancé", 5, "Description for Dév. avancé"),
                            new Course("P5.A.01", "Portfolio", 5, "Description for Portfolio"),
                            new Course(
                                    "R5.01",
                                    "Initiation au management",
                                    5,
                                    "Description for Initiation au management"),
                            new Course("R5.02", "PPP", 5, "Description for PPP"),
                            new Course(
                                    "R5.03", "Communication", 5, "Description for Communication"),
                            new Course(
                                    "R5.A.04",
                                    "Qualité algorithmique",
                                    5,
                                    "Description for Qualité algorithmique"),
                            new Course(
                                    "R5.A.05",
                                    "Programmation avancée",
                                    5,
                                    "Description for Programmation avancée"),
                            new Course(
                                    "R5.A.06",
                                    "Programmation multimédia",
                                    5,
                                    "Description for Programmation multimédia"),
                            new Course(
                                    "R5.A.07",
                                    "Automatisation",
                                    5,
                                    "Description for Automatisation"),
                            new Course(
                                    "R5.A.08",
                                    "Qualité de développement",
                                    5,
                                    "Description for Qualité de développement"),
                            new Course(
                                    "R5.A.09",
                                    "Virtualisation avancée",
                                    5,
                                    "Description for Virtualisation avancée"),
                            new Course(
                                    "R5.A.10", "Nouvelles BD", 5, "Description for Nouvelles BD"),
                            new Course(
                                    "R5.A.11",
                                    "Aide à la décision",
                                    5,
                                    "Description for Aide à la décision"),
                            new Course(
                                    "R5.A.12",
                                    "Modélisations math.",
                                    5,
                                    "Description for Modélisations math."),
                            new Course(
                                    "R5.A.13",
                                    "Éco. durable et num.",
                                    5,
                                    "Description for Éco. durable et num."),
                            new Course("R5.A.14", "Anglais", 5, "Description for Anglais"),
                            new Course(
                                    "S6.A.01",
                                    "Évolution d'une appli.",
                                    5,
                                    "Description for Évolution d'une appli."),
                            new Course("S6.A.St", "Stage", 5, "Description for Stage"),
                            new Course("P6.A.01", "Portfolio", 5, "Description for Portfolio"),
                            new Course(
                                    "R6.01",
                                    "Entrepreneuriat",
                                    5,
                                    "Description for Entrepreneuriat"),
                            new Course(
                                    "R6.02",
                                    "Droit du numérique et PI",
                                    5,
                                    "Description for Droit du numérique et PI"),
                            new Course(
                                    "R6.03",
                                    "Comm. : information",
                                    5,
                                    "Description for Comm. : information"),
                            new Course("R6.04", "PPP", 5, "Description for PPP"),
                            new Course(
                                    "R6.A.05",
                                    "Développement avancé",
                                    5,
                                    "Description for Développement avancé"),
                            new Course(
                                    "R6.A.06",
                                    "Maintenance applicative",
                                    5,
                                    "Description for Maintenance applicative"));
            long index = 1L;
            for (Course course : courses) {
                course.setId(index++);
            }
            courseRepository.saveAll(courses);
        }

        if (gradeRepository.count() <= 0) {
            List<Student> students = studentRepository.findAll();
            List<Course> courses = courseRepository.findAll();
            String[] semesters = {"S1", "S2", "S3", "S4", "S5", "S6"};
            int startYear = 2022;
            for (Student student : students) {
                for (Course course : courses) {
                    String academicYear = startYear + "-" + (startYear + 1);
                    String semester = semesters[0];
                    if (course.getCode().contains("2.")) {
                        academicYear = startYear + "-" + (startYear + 1);
                        semester = semesters[1];
                    } else if (course.getCode().contains("3.")) {
                        academicYear = (startYear + 1) + "-" + (startYear + 2);
                        semester = semesters[2];
                    } else if (course.getCode().contains("4.")) {
                        academicYear = (startYear + 1) + "-" + (startYear + 2);
                        semester = semesters[3];
                    } else if (course.getCode().contains("5.")) {
                        academicYear = (startYear + 2) + "-" + (startYear + 3);
                        semester = semesters[4];
                    } else if (course.getCode().contains("6.")) {
                        academicYear = (startYear + 2) + "-" + (startYear + 3);
                        semester = semesters[5];
                    }
                    Grade newGrade =
                            new Grade(
                                    student,
                                    course,
                                    BigDecimal.valueOf(8 + Math.random() * 12)
                                            .setScale(2, RoundingMode.HALF_UP)
                                            .doubleValue(),
                                    semester,
                                    academicYear);
                    gradeRepository.save(newGrade);
                }
            }
        }
    }
}
