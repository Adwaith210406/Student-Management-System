
package com.example.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Convert Student entity to StudentDTO
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getName());
    }

    // Get all students
    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Get a student by ID
    @GetMapping("/{id}")
    public StudentDTO getStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        return convertToDTO(student);
    }

    // Add a new student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getName());

        Student savedStudent = studentRepository.save(student);

        return convertToDTO(savedStudent);
    }

    // Update an existing student
    @PutMapping("/{id}")
    public StudentDTO updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO updatedStudentDTO) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        student.setName(updatedStudentDTO.getName());

        Student savedStudent = studentRepository.save(student);

        return convertToDTO(savedStudent);
    }

    // Delete a student
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student not found");
        }

        studentRepository.deleteById(id);
    }

    // Health check
    @GetMapping("/health")
    public String health() {
        return "Student Management API is running!";
    }
}
