package com.sia.main.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/error")
public class HttpErrorController {

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public ModelAndView notFound() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404");
		return modelAndView;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("403");
		return modelAndView;
	}
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public ModelAndView serverError() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("500");
		return modelAndView;
	}
	
}
