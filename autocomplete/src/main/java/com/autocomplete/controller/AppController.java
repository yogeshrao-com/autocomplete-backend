package com.autocomplete.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autocomplete.model.Course;
import com.autocomplete.service.CourseService;
import com.autocomplete.service.CourseServiceImpl;

@RestController
public class AppController {
	
	
	@Autowired
    private CourseServiceImpl courseservice;


    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/schoology/search/courses")
    public ResponseEntity createCourse(
        @RequestBody Course course) throws Exception {

        return 
            new ResponseEntity(courseservice.createCourse(course), HttpStatus.CREATED);
    }
    
    
    @GetMapping("/schoology/search/courses/{id}")
    public Course findById(@PathVariable String id) throws Exception {

        return courseservice.getById(id);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/schoology/search/courses/searchByInstructor")
    public List<Course> search(
                @RequestParam(value = "instructor") String instructor) 
                throws Exception {
    return courseservice.searchByInstructor(instructor);
    }
}