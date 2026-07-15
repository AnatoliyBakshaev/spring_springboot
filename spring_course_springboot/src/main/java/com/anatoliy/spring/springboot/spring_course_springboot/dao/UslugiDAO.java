package com.anatoliy.spring.springboot.spring_course_springboot.dao;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Uslugi;
import java.util.List;

public interface UslugiDAO {
    public List<Uslugi> getAllUslugi();
    public Uslugi getUsluga(int id);
    public List<Uslugi> getUslugiByType(String type);
    public void saveUsluga(Uslugi usluga);
}