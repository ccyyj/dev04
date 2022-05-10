package com.rx.dao;

import com.rx.entity.Student;
import com.rx.entity.StudentExample;
import com.rx.vo.StudentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    long countByExample(StudentExample example);

    int deleteByExample(Integer example);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer userid);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKey(StudentExample record);

    List<Student> pageInfo();

    List<Student> selectByName(StudentExample student);

    StudentExample findById(Integer userid);

    //获取所有学生
    List<StudentVO> findAll(Object o);
}