package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.ServiceDirection;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.ServiceDirectionId;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DirectionDAOImpl implements DirectionDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ServiceDirection> getAllDirections() {
        Session session = entityManager.unwrap(Session.class);
        Query<ServiceDirection> query = session.createQuery("from ServiceDirection", ServiceDirection.class);
        return query.getResultList();
    }

    @Override
    public void saveDirection(ServiceDirection serviceDirection) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(serviceDirection);
    }

    @Override
    public ServiceDirection getDirection(int kodPriema, int kodUslugi) {
        Session session = entityManager.unwrap(Session.class);
        ServiceDirectionId id = new ServiceDirectionId(kodPriema, kodUslugi);
        return session.get(ServiceDirection.class, id);
    }

    @Override
    public void deleteDirection(int kodPriema, int kodUslugi) {
        Session session = entityManager.unwrap(Session.class);
        Query<ServiceDirection> query = session.createQuery(
                "delete from ServiceDirection where kodPriema =:priemaId and kodUslugi =:uslugaId");
        query.setParameter("priemaId", kodPriema);
        query.setParameter("uslugaId", kodUslugi);
        query.executeUpdate();
    }

    @Override
    public List<ServiceDirection> getDirectionsByAppointment(int kodPriema) {
        Session session = entityManager.unwrap(Session.class);
        Query<ServiceDirection> query = session.createQuery(
                "from ServiceDirection where kodPriema =:priemaId", ServiceDirection.class);
        query.setParameter("priemaId", kodPriema);
        return query.getResultList();
    }

    @Override
    public List<ServiceDirection> getDirectionsByPatient(int kodPacienta) {
        Session session = entityManager.unwrap(Session.class);
        Query<ServiceDirection> query = session.createQuery(
                "select sd from ServiceDirection sd where sd.kodPriema in " +
                        "(select a.kodPriema from Appointment a where a.kodPacienta =:patientId)",
                ServiceDirection.class);
        query.setParameter("patientId", kodPacienta);
        return query.getResultList();
    }

    @Override
    public void updateDirectionResult(int kodPriema, int kodUslugi, String results, boolean norma) {
        Session session = entityManager.unwrap(Session.class);
        Query<ServiceDirection> query = session.createQuery(
                "update ServiceDirection set podrobnyeRezultatyUslugi =:results, norma =:norma, " +
                        "statusVypolneniyaUslugi = true " +
                        "where kodPriema =:priemaId and kodUslugi =:uslugaId");
        query.setParameter("results", results);
        query.setParameter("norma", norma);
        query.setParameter("priemaId", kodPriema);
        query.setParameter("uslugaId", kodUslugi);
        query.executeUpdate();
    }
}