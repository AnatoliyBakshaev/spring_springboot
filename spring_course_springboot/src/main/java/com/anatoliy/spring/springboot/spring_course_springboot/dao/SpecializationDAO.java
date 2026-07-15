package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Specialization;
import java.util.List;

public interface SpecializationDAO {
    public List<Specialization> getAllSpecializations();
    public Specialization getSpecialization(int id);
}