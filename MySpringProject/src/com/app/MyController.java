package com.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;



@Controller
public class MyController {
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/")
	public String indexPage(){
		return "login";
	}
	
	@RequestMapping("/login")
	public String userLogin(HttpServletRequest req){
		String userid=req.getParameter("username");
		String password=req.getParameter("passwd");
		
		Map<String,Object> map=jdbcTemplate.queryForMap("select * from users where userid=? and password=?",new String[]{userid,password} );
		if(map.size()>0){
			return "main";
		}
		else{
			return "error";
		}
	}
	
	@RequestMapping("/addnew")
	public String addEmployeePage(){
		return "addEmp";
	}
	
	@RequestMapping("/saveEmplRecord")
	public String saveEmloyeeRecord(HttpServletRequest request){
		
		    String name=request.getParameter("name");
		    String department=request.getParameter("department");
		    String designation=request.getParameter("designation");
		    String mobile=request.getParameter("mobile");
		    
		    Employee employee = new Employee();
		    employee.setName(name);
		    employee.setDepartment(department);
		    employee.setDesignation(designation);
		    employee.setMobile(mobile);
		    hibernateTemplate.persist(employee);
		    return "message";
		    
	}
	
	@RequestMapping("/displayAll")
	public ModelAndView  getAllEmployes(){
		List<Employee>  emps= hibernateTemplate.find("from Employee");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("list");
		mav.addObject("list", emps);
		return mav;
	}
	@RequestMapping("search")
	public String searchPage(){
		
		return "search";
	}
	
	public ModelAndView searchEmployeeById(HttpServletRequest req){
		
		ModelAndView mav = new ModelAndView();
		
		String eid=req.getParameter("eid");
			int id=Integer.parseInt(eid);
			
			List<Employee> emp=hibernateTemplate.find("from Employee where eid="+id);
		return mav;	
		}
	
	@RequestMapping("/mainPage")	
	public String  mainPage(){
		return "main";
	
	}
	
	
	

}
