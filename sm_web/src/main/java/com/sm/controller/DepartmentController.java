package com.sm.controller;

import com.sm.entity.Department;
import com.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller("departmentController")
public class DepartmentController {
    // /department/list.do /department_list.jsp

    @Autowired
    private DepartmentService departmentService;


    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Department d1=new Department();
//        d1.setName("aa");
//        d1.setAddress("zhongguo");
//        d1.setId(1);
//        List<Department> list =new ArrayList<>();
//        list.add(d1);
        List<Department> list=departmentService.getAll();
        request.setAttribute("LIST", list);
        request.getRequestDispatcher("../department_list.jsp").forward(request, response);
    }

    /**
     *  添加不需要传进去内容，修改需要
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../department_add.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name=request.getParameter("name");
        String address=request.getParameter("address");

        Department department=new Department();
        department.setName(name);
        department.setAddress(address);

        departmentService.add(department);
        //回控制器，而不是页面
        response.sendRedirect("list.do");
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Department department=departmentService.get(id);
        request.setAttribute("OBJ", department);
        request.getRequestDispatcher("../department_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        String address=request.getParameter("address");

        Department department=new Department();
        department.setId(id);
        department.setName(name);
        department.setAddress(address);

        departmentService.edit(department);
        //回控制器，而不是页面
        response.sendRedirect("list.do");
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));

        departmentService.remove(id);
        //回控制器，而不是页面
        response.sendRedirect("list.do");
    }

}
