package com.rx.service;

import com.github.pagehelper.PageInfo;
import com.rx.entity.Student;
import com.rx.entity.StudentExample;
import com.rx.vo.ResultVO;
import com.rx.vo.StudentVO;

import java.util.List;

public interface StudentService {

    PageInfo<StudentVO> page(Integer page, Integer pageSize);

    List<Student> findByName(String findStudentByName, Integer page, Integer pageSize);

    ResultVO add(Student student);

    StudentExample findById(Integer id);

    int updateById(Object userid, StudentExample studentExample);

    int removeById(Integer id);
}
