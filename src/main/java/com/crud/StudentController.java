package com.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            student.setGender(rs.getString("gender"));
            return student;
        });
    }

    @SuppressWarnings("deprecation")
	@GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            student.setGender(rs.getString("gender"));
            return student;
        });
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        String sql = "INSERT INTO student (name, age, gender) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, student.getName(), student.getAge(), student.getGender());
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        String sql = "UPDATE student SET name = ?, age = ?, gender = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedStudent.getName(), updatedStudent.getAge(), updatedStudent.getGender(), id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        String sql = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
