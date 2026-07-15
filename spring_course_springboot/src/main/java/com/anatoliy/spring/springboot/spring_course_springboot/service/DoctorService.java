package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Doctor;
import java.util.List;

public interface DoctorService {
    public List<Doctor> getAllDoctors();
    public void saveDoctor(Doctor doctor);
    public Doctor getDoctor(int id);
    public void deleteDoctor(int id);
    public Doctor authenticate(String login, String password);
    public List<Doctor> getDoctorsBySpecialization(int specializationId);
    public long getDoctorsCount();
    public long getPatientsCount();
}
