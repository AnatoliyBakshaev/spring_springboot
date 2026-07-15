package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.dao.SpecializationDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationDAO specializationDAO;

    @Override
    @Transactional
    public List<Specialization> getAllSpecializations() {
        return specializationDAO.getAllSpecializations();
    }

    @Override
    @Transactional
    public Specialization getSpecialization(int id) {
        return specializationDAO.getSpecialization(id);
    }

    @Override
    @Transactional
    public String getSpecializationName(int id) {
        Specialization spec = specializationDAO.getSpecialization(id);
        return spec != null ? spec.getNazvanieDolzhnosti() : "Не указана";
    }

    @Override
    @Transactional
    public boolean isChiefDoctor(int specializationId) {
        Specialization spec = specializationDAO.getSpecialization(specializationId);
        if (spec == null) return false;
        String nazvanie = spec.getNazvanieDolzhnosti().toLowerCase();
        // Проверяем, является ли должность главным врачом
        return nazvanie.contains("главный") ||
                nazvanie.contains("главврач") ||
                nazvanie.equals("главный врач") ||
                nazvanie.equals("главный врач") ||
                // Добавьте явную проверку для вашего случая
                (specializationId == 2 && nazvanie.contains("главный"));
    }
}