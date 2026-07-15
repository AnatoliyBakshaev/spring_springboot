package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Specialization;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SpecializationDAOImpl implements SpecializationDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Specialization> getAllSpecializations() {
        Session session = entityManager.unwrap(Session.class);
        Query<Specialization> query = session.createQuery("from Specialization", Specialization.class);
        return query.getResultList();
    }

    @Override
    public Specialization getSpecialization(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Specialization.class, id);
    }
}
