package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Appointment;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.ServiceDirection;

import java.util.List;

public interface AppointmentService {
    public List<Appointment> getAllAppointments();
    public void saveAppointment(Appointment appointment);
    public Appointment getAppointment(int id);
    public void deleteAppointment(int id);
    public List<Appointment> getAppointmentsByPatient(int patientId);
    public List<Appointment> getAppointmentsByDoctor(int doctorId);
    public List<Appointment> getUpcomingAppointments(int patientId);
    public List<Appointment> getUpcomingAppointmentsByDoctor(int doctorId);
    public List<Appointment> getTodayAppointmentsByDoctor(int doctorId);
    public void updateAppointmentStatus(int appointmentId, boolean status, String diagnosis);
    public List<ServiceDirection> getServiceDirectionsForAppointment(int appointmentId);
    public void addServiceDirectionToAppointment(int appointmentId, int serviceId, String note);
    public List<Appointment> getTodayAppointments();
    public List<Appointment> getUpcomingAppointments();

    // НОВЫЕ МЕТОДЫ ДЛЯ ПОДЗАПРОСОВ
    public List<Patient> getDistinctPatientsLastWeek();      // DISTINCT
    public List<Object[]> getTop5DoctorsByAppointments();    // ORDER BY + LIMIT
    public List<Patient> getPatientsWithOffset(int offset, int limit);  // OFFSET + LIMIT
    public long getTotalPatientsWithAppointments();
    public List<Patient> getPatientsByDoctor(int doctorId);
}