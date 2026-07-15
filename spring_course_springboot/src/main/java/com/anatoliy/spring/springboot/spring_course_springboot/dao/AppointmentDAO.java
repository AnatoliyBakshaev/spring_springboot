package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Appointment;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;

import java.util.List;

public interface AppointmentDAO {
    // Существующие методы
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
    public List<Appointment> getTodayAppointments();
    public List<Appointment> getUpcomingAppointments();

    // НОВЫЕ МЕТОДЫ ДЛЯ ПОДЗАПРОСОВ
    public List<Patient> getDistinctPatientsLastWeek();           // DISTINCT подзапрос
    public List<Object[]> getTop5DoctorsByAppointments();         // ORDER BY + LIMIT подзапрос
    public List<Patient> getPatientsWithOffset(int offset, int limit);  // OFFSET + LIMIT подзапрос
    public long getTotalPatientsWithAppointments();               // Вспомогательный метод для пагинации
    public List<Patient> getPatientsByDoctor(int doctorId);
}