package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;

import java.util.List;

public interface PatientService {
    public List<Patient> getAllPatient();
    public void savePatient(Patient patient);
    public Patient getPatient(int id);
    public void deletePatient(int patient);
    public Patient authenticate(String login, String password);
    public long getPatientsCount();
}
