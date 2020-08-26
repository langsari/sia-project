package com.sia.main.web.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account")
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, Principal principal, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(principal != null) {
			modelAndView.setViewName("redirect:/");
		} else {
			modelAndView.setViewName("Login");
			if (error != null) {
				modelAndView.addObject("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
			}
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/login/", method = RequestMethod.GET)
	public ModelAndView login(HttpSession session, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		if(principal != null) {
			modelAndView.setViewName("redirect:/session/chooseUserRole");
		} else {
			modelAndView.setViewName("redirect:/account/login");
		}
		return modelAndView;
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Username atau password tidak terdaftar.";
		} else {
			error = "Login gagal. Pesan Exception: " + exception.getMessage();
		}
		return error;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		if(principal != null) {
			modelAndView.setViewName("redirect:/");
		} else {
			modelAndView.setViewName("redirect:/account/login");
			request.getSession().removeAttribute("userRoleSession");
			request.getSession().removeAttribute("moduleSession");
		}
		return modelAndView;
	}
	
}
