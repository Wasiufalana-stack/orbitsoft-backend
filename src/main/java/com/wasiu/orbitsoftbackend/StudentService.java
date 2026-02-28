package com.wasiu.orbitsoftbackend;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Creating student with name: {}", student.getName());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);

        existing.setName(updatedStudent.getName());
        existing.setCourse(updatedStudent.getCourse());

        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

