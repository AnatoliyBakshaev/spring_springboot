package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.dao.UslugiDAO;
import com.anatoliy.spring.springboot.spring_course_springboot.entity.Uslugi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UslugiServiceImpl implements UslugiService {

    @Autowired
    private UslugiDAO uslugiDAO;

    @Override
    @Transactional
    public List<Uslugi> getAllUslugi() {
        return uslugiDAO.getAllUslugi();
    }

    @Override
    @Transactional
    public Uslugi getUsluga(int id) {
        return uslugiDAO.getUsluga(id);
    }

    @Override
    @Transactional
    public List<Uslugi> getUslugiByType(String type) {
        return uslugiDAO.getUslugiByType(type);
    }

    @Override
    @Transactional
    public void saveUsluga(Uslugi usluga) {
        uslugiDAO.saveUsluga(usluga);
    }
}