package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Patient;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public class PatientDAOImpl implements PatientDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Patient> getAllPatient() {

        Session session = entityManager.unwrap(Session.class);

        Query<Patient> query = session.createQuery("from Patient", Patient.class);
        List<Patient> allPatient = query.getResultList();
        return allPatient ;
    }

    @Override
    public void savePatient(Patient patient) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(patient);
    }

    @Override
    public Patient getEPatient(int id) {
        Session session = entityManager.unwrap(Session.class);
        Patient patient = session.get(Patient.class,id);
        return patient;
    }

    @Override
    public void deletePatient(int patient) {
        Session session = entityManager.unwrap(Session.class);
        Query<Patient> query = session.createQuery("delete  from Patient " +
                "where id =:patientId");
        query.setParameter("patientId",id);
        query.executeUpdate();
    }
    @Override
    public long getPatientsCount() {
        Session session = entityManager.unwrap(Session.class);
        Query<Long> query = session.createQuery("select count(*) from Patient", Long.class);
        return query.uniqueResult();
    }
    @Override
    public Patient authenticate(String login, String password) {
        Session session = entityManager.unwrap(Session.class);
        Query<Patient> query = session.createQuery("from Patient where login =:login and parol =:password", Patient.class);
        query.setParameter("login", login);
        query.setParameter("password", password);

        List<Patient> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }


}
