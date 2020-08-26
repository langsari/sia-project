package com.sia.main.web.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.service.services.PenggunaService;

@Controller
public class HomeController {
	
	@Autowired
	private PenggunaService penggunaService;
	
	@RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
	public ModelAndView home(HttpSession session, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		if(principal == null) {
			modelAndView.setViewName("redirect:/account/login");
		} else {
			modelAndView.setViewName("Welcome");
		}
		return modelAndView;
	}
	 
}
