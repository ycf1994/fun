package com.controller;

import java.util.List;

import com.domain.User;
import com.ycf.fun.annotation.Router;
import com.ycf.fun.controller.Controller;

@Router(uri="user")
public class UserController extends Controller{
	public void loginPage(){
		render("login");
	}
	      public void addPage(){
		render("add");
	}
	public void login(String username,String password){
		if("admin".equals(username)&&"111".equals(password)){
			redirect("show");
		}else{
			redirect("loginPage");
		}
	}
	public void add(User user){
		boolean flag=this.save(user);
		if(flag){
			redirect("show");
		}else{
			redirect("addPage");
		}
	}
	
	public void show(){
		List<User> users=this.find("select * from user", User.class);
		setAttrToRequest("users", users);
		render("show");
	}
}
