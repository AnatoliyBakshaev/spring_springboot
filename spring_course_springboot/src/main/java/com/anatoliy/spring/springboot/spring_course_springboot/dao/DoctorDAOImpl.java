package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Doctor;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DoctorDAOImpl implements DoctorDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Doctor> getAllDoctors() {
        Session session = entityManager.unwrap(Session.class);
        Query<Doctor> query = session.createQuery("from Doctor", Doctor.class);
        return query.getResultList();
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(doctor);
    }

    @Override
    public Doctor getDoctor(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Doctor.class, id);
    }

    @Override
    public void deleteDoctor(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query<Doctor> query = session.createQuery("delete from Doctor where kodSotrudnika =:doctorId");
        query.setParameter("doctorId", id);
        query.executeUpdate();
    }

    @Override
    public Doctor authenticate(String login, String password) {
        Session session = entityManager.unwrap(Session.class);
        Query<Doctor> query = session.createQuery("from Doctor where login =:login and parol =:password", Doctor.class);
        query.setParameter("login", login);
        query.setParameter("password", password);

        List<Doctor> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(int specializationId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Doctor> query = session.createQuery("from Doctor where kodSpecializatsii =:specId", Doctor.class);
        query.setParameter("specId", specializationId);
        return query.getResultList();
    }

    @Override
    public long getDoctorsCount() {
        Session session = entityManager.unwrap(Session.class);
        Query<Long> query = session.createQuery("select count(*) from Doctor", Long.class);
        return query.uniqueResult();
    }
}