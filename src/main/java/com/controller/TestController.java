package com.controller;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.domain.User;
import com.ycf.fun.annotation.Method;
import com.ycf.fun.annotation.Method.method;
import com.ycf.fun.annotation.Router;
import com.ycf.fun.controller.Controller;

@Router(uri="test")
@Method(requestMethod=method.POST)
public class TestController extends Controller{
	public void index(){
		render("index");
	}
	public void find(int page,int rows){
		System.out.println("find...");
		List<User> users=this.find("select * from user limit "+(page-1)*rows+","+rows, User.class);
		this.out(JSON.toJSONString(users));
		
	}
	
	public void upload(User user,File file){
		System.out.println(user);
		this.copyFile(file, new File("d://1.docx"));
	}
}
