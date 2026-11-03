
package com.example.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void shouldReturnAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(
                List.of(new Student("Adwaith"), new Student("Rahul"))
        );

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Adwaith"));
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(new Student("Adwaith")));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Adwaith"));
    }

    @Test
    void shouldReturnNotFoundForMissingStudent() throws Exception {
        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/students/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddStudent() throws Exception {
        when(studentRepository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Kiran\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Kiran"));
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        Student existingStudent = new Student("Adwaith");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingStudent() throws Exception {
        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/students/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/students/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Student Management API is flying!"
                ));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        when(studentRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());

        verify(studentRepository).deleteById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingStudent() throws Exception {
        when(studentRepository.existsById(99L)).thenReturn(false);

        mockMvc.perform(delete("/students/99"))
                .andExpect(status().isNotFound());

        verify(studentRepository, never()).deleteById(99L);
    }
}
