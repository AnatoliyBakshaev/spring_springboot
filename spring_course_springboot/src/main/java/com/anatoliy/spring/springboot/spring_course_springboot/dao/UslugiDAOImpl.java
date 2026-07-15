package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Uslugi;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UslugiDAOImpl implements UslugiDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Uslugi> getAllUslugi() {
        Session session = entityManager.unwrap(Session.class);
        Query<Uslugi> query = session.createQuery("from Uslugi", Uslugi.class);
        return query.getResultList();
    }

    @Override
    public Uslugi getUsluga(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Uslugi.class, id);
    }

    @Override
    public List<Uslugi> getUslugiByType(String type) {
        Session session = entityManager.unwrap(Session.class);
        Query<Uslugi> query = session.createQuery("from Uslugi where tip =:type", Uslugi.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
    @Override
    public void saveUsluga(Uslugi usluga) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(usluga);
    }
}