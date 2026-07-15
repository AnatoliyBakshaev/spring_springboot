package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.dao.DoctorDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.dao.PatientDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDAO doctorDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private SpecializationService specializationService;

    @Override
    @Transactional
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        for (Doctor doctor : doctors) {
            String specName = specializationService.getSpecializationName(doctor.getKodSpecializatsii());
            doctor.setSpecializationName(specName);
        }
        return doctors;
    }

    @Override
    @Transactional
    public void saveDoctor(Doctor doctor) {
        doctorDAO.saveDoctor(doctor);
    }

    @Override
    @Transactional
    public Doctor getDoctor(int id) {
        Doctor doctor = doctorDAO.getDoctor(id);
        enrichDoctorWithSpecializationInfo(doctor);
        return doctor;
    }

    @Override
    @Transactional
    public void deleteDoctor(int id) {
        doctorDAO.deleteDoctor(id);
    }

    @Override
    @Transactional
    public Doctor authenticate(String login, String password) {
        Doctor doctor = doctorDAO.authenticate(login, password);
        if (doctor != null) {
            enrichDoctorWithSpecializationInfo(doctor);
        }
        return doctor;
    }

    @Override
    @Transactional
    public List<Doctor> getDoctorsBySpecialization(int specializationId) {
        List<Doctor> doctors = doctorDAO.getDoctorsBySpecialization(specializationId);
        for (Doctor doctor : doctors) {
            enrichDoctorWithSpecializationInfo(doctor);
        }
        return doctors;
    }

    @Override
    @Transactional
    public long getDoctorsCount() {
        return doctorDAO.getDoctorsCount();
    }

    @Override
    @Transactional
    public long getPatientsCount() {
        return patientDAO.getPatientsCount();
    }

    // Вспомогательный метод для обогащения врача информацией о специализации
    private void enrichDoctorWithSpecializationInfo(Doctor doctor) {
        if (doctor != null) {
            String specName = specializationService.getSpecializationName(doctor.getKodSpecializatsii());
            doctor.setSpecializationName(specName);

            // Определяем роль на основе специализации
            // Явно проверяем ID специализации или название
            boolean isChief = false;

            // Вариант 1: по ID специализации
            if (doctor.getKodSpecializatsii() == 2) { // если 2 - это главный врач
                isChief = true;
            }

            // Вариант 2: по названию должности
            if (specName != null && specName.toLowerCase().contains("главный")) {
                isChief = true;
            }

            doctor.setRole(isChief ? "CHIEF" : "SPECIALIST");
        }
    }
}