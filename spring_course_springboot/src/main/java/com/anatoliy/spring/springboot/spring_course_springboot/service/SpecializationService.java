package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Specialization;
import java.util.List;

public interface SpecializationService {
    public List<Specialization> getAllSpecializations();
    public Specialization getSpecialization(int id);
    public String getSpecializationName(int id);
    public boolean isChiefDoctor(int specializationId);

}