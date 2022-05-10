package com.rx.controller;

import com.github.pagehelper.PageInfo;
import com.rx.entity.*;
import com.rx.service.CollegeService;
import com.rx.service.StudentService;
import com.rx.service.UserloginService;
import com.rx.vo.ResultVO;
import com.rx.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserloginService userloginService;
    @Autowired
    private CollegeService collegeService;

    //学生信息显示
    @PostMapping("showStudent")
    public ModelAndView showStudent(@RequestParam(value = "page" , defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize" ,defaultValue = "4") Integer pageSize,
                                    ModelAndView mv){

        PageInfo<StudentVO> pageInfo = studentService.page(page, pageSize);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("forward:/WEB-INF/pages/admin/showStudent.jsp");

        return  mv;
    }

    //学生搜索
    @PostMapping("selectStudent")
    public ModelAndView selectStudent(HttpServletRequest request,
                                      @RequestParam(value = "page" , defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize" ,defaultValue = "4") Integer pageSize,
                                         ModelAndView mv){

        String  findStudentByName = (String) request.getSession().getAttribute("findStudentByName");

        List<Student> list = studentService.findByName(findStudentByName,page,pageSize);

        PageInfo selectStudentInfo=new PageInfo(list);

        mv.addObject("selectStudentInfo",selectStudentInfo);

        mv.setViewName("forward:/WEB-INF/pages/admin/showStudent.jsp");

        return mv;
    }

    //学生验证（添加学生时，看学号是否存在）
    @PostMapping(value = "checkStudentId")
    public CheckUserId checkStudentId( CheckUserId checkUserId) {

        Integer userid=checkUserId.getUserid();
        StudentExample studentExample = studentService.findById(userid);
        if(studentExample!=null){
            //学号已经存在
            checkUserId.setFlag(false);
            checkUserId.setErrorMsg("该学号已经存在，请重新输入！");
        } else {
            //学号不存在，添加
            checkUserId.setFlag(true);
            checkUserId.setErrorMsg("");
        }
        return checkUserId;
    }

    //添加学生信息界面
    @GetMapping("addStudent")
    public ModelAndView addStudentShow(ModelAndView mv){

        List<College> list = collegeService.finAll();

        mv.addObject("collegeList",list);

        mv.setViewName("forward:/WEB-INF/pages/admin/addStudent.jsp");

        return mv;
    }

    //添加学生信息
    @PostMapping("addStudent")
    public ModelAndView addStudent(Student student, ModelAndView mv){
        ResultVO vo = null;

        if(student.getUserid() != null){
            vo = studentService.add(student);
        }

       if (vo.getFlag().equals(true)){
           Userlogin userlogin = new Userlogin();;

           userlogin.setUsername(student.getUserid().toString());
           userlogin.setPassword("123");
           userlogin.setRole(2);
           userloginService.save(userlogin);

           //添加成功后重定向
           mv.setViewName("redirect:/admin/showStudent");

           return mv;
       }else {
           mv.addObject("message", "学号重复");

           //添加失败后转发到指定位置
           mv.setViewName("forward:/WEB-INF/pages/error.jsp");

           return mv;
       }
    }

    //修改学生信息显示
    @GetMapping("editStudent")
    public ModelAndView editStudentShow(Integer id , ModelAndView mv) throws Exception {
        if (id == null){
            mv.setViewName("redirect:/admin/showStudent");

            return mv;
        }

        StudentExample studentExample = studentService.findById(id);

        if (id != null){
            throw new Exception("未找到该名学生");
        }

        List<College> list = collegeService.finAll();

        mv.addObject("collegeList", list);
        mv.addObject("student", studentExample);

        mv.setViewName("forward:/WEB-INF/pages/admin/editStudent.jsp");

        return mv;
    }

    //修改学生信息
    @PostMapping("editStudent")
    public ModelAndView editStudent(StudentExample studentExample, ModelAndView mv)  {

        studentService.updateById(studentExample.getUserid(), studentExample);

        mv.setViewName("redirect:/admin/showStudent");

        return mv;
    }

    //删除学生
    @GetMapping("removeStudent")
    public ModelAndView removeStudent(Integer id , ModelAndView mv) {
        if (id == null) {
            mv.addObject("redirect:/admin/showStudent");

            return mv;
        }

        studentService.removeById(id);
        userloginService.removeByName(id.toString());

        mv.addObject("redirect:/admin/showStudent");

        return mv;
    }

    //学生姓名保存
    @PostMapping("searchStudentName")
    public void searchStudentName(Student student, HttpServletRequest request){
        String username = student.getUsername();

        request.getSession().setAttribute("findStudentByName" , username);
    }
}
