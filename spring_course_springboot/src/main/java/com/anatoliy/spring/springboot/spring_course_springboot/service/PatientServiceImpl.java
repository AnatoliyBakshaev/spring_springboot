package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.dao.PatientDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.dao.PatientDAO;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientDAO patientDAO;

    @Override
    @Transactional
    public List<Patient> getAllPatient(){

        return patientDAO.getAllPatient();
    }


    @Override
    @Transactional
    public void savePatient(Patient employee) {
        patientDAO.savePatient(employee);
    }


    @Override
    @Transactional
    public Patient getPatient(int id) {
        return patientDAO.getEPatient(id);
    }

    @Override
    @Transactional
    public void deletePatient(int patient) {
        patientDAO.deletePatient(patient);
    }
    @Override
    @Transactional
    public long getPatientsCount() {
        return patientDAO.getPatientsCount();
    }
    @Override
    @Transactional
    public Patient authenticate(String login, String password) {
        return patientDAO.authenticate(login, password);
    }


}
