package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.dao.DirectionDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.ServiceDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DirectionServiceImpl implements DirectionService {

    @Autowired
    private DirectionDAO directionDAO;

    @Override
    @Transactional
    public List<ServiceDirection> getAllDirections() {
        return directionDAO.getAllDirections();
    }

    @Override
    @Transactional
    public void saveDirection(ServiceDirection serviceDirection) {
        directionDAO.saveDirection(serviceDirection);
    }

    @Override
    @Transactional
    public ServiceDirection getDirection(int kodPriema, int kodUslugi) {
        return directionDAO.getDirection(kodPriema, kodUslugi);
    }

    @Override
    @Transactional
    public void deleteDirection(int kodPriema, int kodUslugi) {
        directionDAO.deleteDirection(kodPriema, kodUslugi);
    }

    @Override
    @Transactional
    public List<ServiceDirection> getDirectionsByAppointment(int kodPriema) {
        return directionDAO.getDirectionsByAppointment(kodPriema);
    }

    @Override
    @Transactional
    public List<ServiceDirection> getDirectionsByPatient(int kodPacienta) {
        return directionDAO.getDirectionsByPatient(kodPacienta);
    }

    @Override
    @Transactional
    public void updateDirectionResult(int kodPriema, int kodUslugi, String results, boolean norma) {
        directionDAO.updateDirectionResult(kodPriema, kodUslugi, results, norma);
    }
}