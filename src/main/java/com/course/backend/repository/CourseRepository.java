/* (C)2025 */
package com.course.backend.repository;

import com.course.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The interface Course repository.
 * This interface is used to interact with the database.
 */
@RepositoryRestResource(exported = false)
public interface CourseRepository extends JpaRepository<Course, Long> {}
