package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Appointment;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Appointment> getAllAppointments() {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("from Appointment order by dataVremyaPriema desc", Appointment.class);
        return query.getResultList();
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(appointment);
    }

    @Override
    public Appointment getAppointment(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Appointment.class, id);
    }

    @Override
    public void deleteAppointment(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("delete from Appointment where kodPriema =:appointmentId");
        query.setParameter("appointmentId", id);
        query.executeUpdate();
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("from Appointment where kodPacienta =:patientId order by dataVremyaPriema desc", Appointment.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("from Appointment where kodSotrudnika =:doctorId order by dataVremyaPriema desc", Appointment.class);
        query.setParameter("doctorId", doctorId);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getUpcomingAppointments(int patientId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("from Appointment where kodPacienta =:patientId and dataVremyaPriema > :currentDate order by dataVremyaPriema asc", Appointment.class);
        query.setParameter("patientId", patientId);
        query.setParameter("currentDate", new Date());
        return query.getResultList();
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByDoctor(int doctorId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("from Appointment where kodSotrudnika =:doctorId and dataVremyaPriema > :currentDate and statusVypolneniyaPriema = false order by dataVremyaPriema asc", Appointment.class);
        query.setParameter("doctorId", doctorId);
        query.setParameter("currentDate", new Date());
        return query.getResultList();
    }
    @Override
    public List<Patient> getPatientsByDoctor(int doctorId) {
        Session session = entityManager.unwrap(Session.class);

        String sql = """
        SELECT DISTINCT p.* 
        FROM пациент p
        WHERE p.КодПациента IN (
            SELECT DISTINCT a.КодПациента 
            FROM приём a 
            WHERE a.КодСотрудника = :doctorId
        )
        ORDER BY p.ФИО
    """;

        Query<Patient> query = session.createNativeQuery(sql, Patient.class);
        query.setParameter("doctorId", doctorId);
        return query.getResultList();
    }
    @Override
    public List<Appointment> getTodayAppointmentsByDoctor(int doctorId) {
        Session session = entityManager.unwrap(Session.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfDay = calendar.getTime();

        Query<Appointment> query = session.createQuery("from Appointment where kodSotrudnika =:doctorId and dataVremyaPriema between :startOfDay and :endOfDay order by dataVremyaPriema asc", Appointment.class);
        query.setParameter("doctorId", doctorId);
        query.setParameter("startOfDay", startOfDay);
        query.setParameter("endOfDay", endOfDay);
        return query.getResultList();
    }

    @Override
    public void updateAppointmentStatus(int appointmentId, boolean status, String diagnosis) {
        Session session = entityManager.unwrap(Session.class);
        Appointment appointment = session.get(Appointment.class, appointmentId);
        if (appointment != null) {
            appointment.setStatusVypolneniyaPriema(status);
            if (diagnosis != null && !diagnosis.isEmpty()) {
                appointment.setDiagnoz(diagnosis);
            }
            session.saveOrUpdate(appointment);
        }
    }

    @Override
    public List<Appointment> getTodayAppointments() {
        Session session = entityManager.unwrap(Session.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfDay = calendar.getTime();

        Query<Appointment> query = session.createQuery("from Appointment where dataVremyaPriema between :startOfDay and :endOfDay order by dataVremyaPriema asc", Appointment.class);
        query.setParameter("startOfDay", startOfDay);
        query.setParameter("endOfDay", endOfDay);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getUpcomingAppointments() {
        Session session = entityManager.unwrap(Session.class);
        Query<Appointment> query = session.createQuery("from Appointment where dataVremyaPriema > :currentDate and statusVypolneniyaPriema = false order by dataVremyaPriema asc", Appointment.class);
        query.setParameter("currentDate", new Date());
        return query.getResultList();
    }

    // ========== НОВЫЕ МЕТОДЫ ДЛЯ ПОДЗАПРОСОВ ==========

    @Override
    public List<Patient> getDistinctPatientsLastWeek() {
        Session session = entityManager.unwrap(Session.class);

        String sql = """
            SELECT DISTINCT p.* 
            FROM пациент p
            WHERE p.КодПациента IN (
                SELECT DISTINCT a.КодПациента 
                FROM приём a 
                WHERE a.ДатаВремяПриема >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
            )
            ORDER BY p.ФИО
        """;

        Query<Patient> query = session.createNativeQuery(sql, Patient.class);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTop5DoctorsByAppointments() {
        Session session = entityManager.unwrap(Session.class);

        String sql = """
            SELECT 
                d.КодСотрудника,
                d.ФИО,
                d.НомерКабинета,
                s.НазваниеДолжности,
                (
                    SELECT COUNT(*) 
                    FROM приём a 
                    WHERE a.КодСотрудника = d.КодСотрудника
                ) as total_appointments
            FROM врач d
            JOIN специализация s ON d.КодСпециализации = s.КодСпециализации
            ORDER BY total_appointments DESC
            LIMIT 5
        """;

        Query<Object[]> query = session.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public List<Patient> getPatientsWithOffset(int offset, int limit) {
        Session session = entityManager.unwrap(Session.class);

        String sql = """
            SELECT p.* 
            FROM пациент p
            WHERE p.КодПациента IN (
                SELECT DISTINCT a.КодПациента 
                FROM приём a
            )
            ORDER BY p.ФИО
            LIMIT :limit OFFSET :offset
        """;

        Query<Patient> query = session.createNativeQuery(sql, Patient.class);
        query.setParameter("limit", limit);
        query.setParameter("offset", offset);
        return query.getResultList();
    }

    @Override
    public long getTotalPatientsWithAppointments() {
        Session session = entityManager.unwrap(Session.class);

        String sql = """
            SELECT COUNT(DISTINCT p.КодПациента)
            FROM пациент p
            WHERE p.КодПациента IN (
                SELECT DISTINCT a.КодПациента 
                FROM приём a
            )
        """;

        Query<Long> query = session.createNativeQuery(sql);
        return query.uniqueResult();
    }
}