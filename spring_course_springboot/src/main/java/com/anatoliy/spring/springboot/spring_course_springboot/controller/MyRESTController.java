package com.anatoliy.spring.springboot.spring_course_springboot.controller;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import com.anatoliy.spring.springboot.spring_course_springboot.service.PatientService;
import com.anatoliy.spring.springboot.spring_course_springboot.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patient")
    public List<Patient> showAllPatient(){
        List<Patient> allPatient = patientService.getAllPatient();
        return allPatient;
    }

    @GetMapping("/patient/{id}")
   public Patient getPatient(@PathVariable("id") int id){
        Patient patient = patientService.getPatient(id);
        return patient;
   }

   @PostMapping("/patient")
    public Patient postPatient(@RequestBody Patient patient){
       patientService.savePatient(patient);
        return patient;
   }
   @PutMapping("/patient")
    public Patient putPatient(@RequestBody Patient patient){
       patientService.savePatient(patient);
        return patient;
   }
   @DeleteMapping("/patient/{id}")
    public String deleteEmployee(@PathVariable("id") int id){

       patientService.deletePatient(id);
        return "Employee with ID = "+ id + " was deleted";
   }
}
