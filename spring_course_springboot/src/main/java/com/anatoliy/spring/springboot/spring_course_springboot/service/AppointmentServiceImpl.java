package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.dao.AppointmentDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Appointment;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.ServiceDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private DirectionService directionService;

    @Override
    @Transactional
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    @Override
    @Transactional
    public void saveAppointment(Appointment appointment) {
        appointmentDAO.saveAppointment(appointment);
    }

    @Override
    @Transactional
    public Appointment getAppointment(int id) {
        return appointmentDAO.getAppointment(id);
    }

    @Override
    @Transactional
    public void deleteAppointment(int id) {
        appointmentDAO.deleteAppointment(id);
    }

    @Override
    @Transactional
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return appointmentDAO.getAppointmentsByPatient(patientId);
    }

    @Override
    @Transactional
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }

    @Override
    @Transactional
    public List<Appointment> getUpcomingAppointments(int patientId) {
        return appointmentDAO.getUpcomingAppointments(patientId);
    }

    @Override
    @Transactional
    public List<Appointment> getUpcomingAppointmentsByDoctor(int doctorId) {
        return appointmentDAO.getUpcomingAppointmentsByDoctor(doctorId);
    }

    @Override
    @Transactional
    public List<Appointment> getTodayAppointmentsByDoctor(int doctorId) {
        return appointmentDAO.getTodayAppointmentsByDoctor(doctorId);
    }

    @Override
    @Transactional
    public void updateAppointmentStatus(int appointmentId, boolean status, String diagnosis) {
        appointmentDAO.updateAppointmentStatus(appointmentId, status, diagnosis);
    }

    @Override
    @Transactional
    public List<ServiceDirection> getServiceDirectionsForAppointment(int appointmentId) {
        return directionService.getDirectionsByAppointment(appointmentId);
    }

    @Override
    @Transactional
    public void addServiceDirectionToAppointment(int appointmentId, int serviceId, String note) {
        ServiceDirection sd = new ServiceDirection();
        sd.setKodPriema(appointmentId);
        sd.setKodUslugi(serviceId);
        sd.setPrimechanie(note);
        sd.setDataUslugi(new Date());
        sd.setStatusVypolneniyaUslugi(false);
        sd.setNorma(false);
        directionService.saveDirection(sd);
    }

    @Override
    @Transactional
    public List<Appointment> getTodayAppointments() {
        return appointmentDAO.getTodayAppointments();
    }

    @Override
    @Transactional
    public List<Appointment> getUpcomingAppointments() {
        return appointmentDAO.getUpcomingAppointments();
    }

    // ========== МЕТОДЫ ДЛЯ ПОДЗАПРОСОВ ==========

    @Override
    @Transactional
    public List<Patient> getDistinctPatientsLastWeek() {
        return appointmentDAO.getDistinctPatientsLastWeek();
    }

    @Override
    @Transactional
    public List<Object[]> getTop5DoctorsByAppointments() {
        return appointmentDAO.getTop5DoctorsByAppointments();
    }

    @Override
    @Transactional
    public List<Patient> getPatientsWithOffset(int offset, int limit) {
        return appointmentDAO.getPatientsWithOffset(offset, limit);
    }

    @Override
    @Transactional
    public List<Patient> getPatientsByDoctor(int doctorId) {
        return appointmentDAO.getPatientsByDoctor(doctorId);
    }

    @Override
    @Transactional
    public long getTotalPatientsWithAppointments() {
        return appointmentDAO.getTotalPatientsWithAppointments();
    }
}