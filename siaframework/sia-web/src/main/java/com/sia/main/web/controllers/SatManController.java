package com.sia.main.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.data.dao.SatuanManajemenDAO;

@Controller
@Secured(value = { "ROLE_System Admin" })
@RequestMapping(value = "/admin/satMan")
public class SatManController {

	@Autowired
	private SatuanManajemenDAO satManDAO;
	
	@RequestMapping(value = { "", "/"} , method = RequestMethod.GET)
	public ModelAndView viewSatMan() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("satManList", this.satManDAO.getAll());
		modelAndView.setViewName("LihatSatMan");
		return modelAndView;
	}
	
}
