package com.sia.main.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sia.main.domain.Menu;
import com.sia.main.domain.MenuPeran;
import com.sia.main.domain.Modul;
import com.sia.main.domain.Peran;
import com.sia.main.plugin.common.Response;
import com.sia.main.service.services.MenuPeranService;
import com.sia.main.service.services.MenuService;
import com.sia.main.service.services.ModulService;
import com.sia.main.service.services.PeranService;
import com.sia.main.web.json_model.RoleMenu;

@Controller
@Secured(value = { "ROLE_System Admin" })
@RequestMapping(value = "/admin/module")
public class ModuleController {
	
	private static int imageSize = 2097152;
	
	@Autowired
	private PeranService peranService;
	
	@Autowired
	private ModulService modulService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private MenuPeranService menuPeranService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public ModelAndView mainPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("uploadWizardDone") != null) {
			modelAndView.addObject("uploadWizardDone", (Response)session.getAttribute("uploadWizardDone"));
			session.removeAttribute("uploadWizardDone");
		}
		modelAndView.addObject("moduleList", modulService.getAll());
		modelAndView.addObject("menuActive", "Kelola Modul");
		modelAndView.setViewName("PengelolaanModul");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadWizard/1", method = RequestMethod.GET)
	public ModelAndView uploadWizard1(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("uploadFailed") != null) {
			modelAndView.addObject("uploadFailed", (Response)session.getAttribute("uploadFailed"));
			session.removeAttribute("uploadFailed");
		}
		modelAndView.addObject("menuActive", "Unggah Modul");
		modelAndView.setViewName("TambahModul1");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadWizard/1/upload", method = RequestMethod.POST)
	public ModelAndView uploadModule(@RequestParam("file") Object file, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Bundle hostBundle = FrameworkUtil.getBundle(this.getClass());
		Response response = this.modulService.installModule(file, hostBundle);
		if(response.getData() != null) {
			session.setAttribute("moduleOnWizard", (Modul)response.getData());
			modelAndView.setViewName("redirect:/admin/module/uploadWizard/2");
		} else {
			session.setAttribute("uploadFailed", response);
			modelAndView.setViewName("redirect:/admin/module/uploadWizard/1");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadWizard/2",  method = RequestMethod.GET)
	public ModelAndView uploadWizard2(@RequestParam(value = "moduleId", required = false) UUID moduleId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Modul modul;
		if(moduleId != null) {
			modul = modulService.getById(moduleId);
			modelAndView.addObject("notWizard", true);
			session.setAttribute("moduleOnEdit", modul);
		} else {
			modul = (Modul)session.getAttribute("moduleOnWizard");
		}
		List<Peran> roles = peranService.getAll();
		modelAndView.addObject("roles", roles);
		modelAndView.addObject("menus", modul.getMenus());
		List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
		for(Peran role: roles) {
			RoleMenu roleMenu = new RoleMenu();
			List<String> temp = new ArrayList<String>(); 
			for(MenuPeran menuPeran: role.getMenuPeranList()) {
				if(menuPeran.getMenu().getModul().getIdModul().equals(modul.getIdModul())) {
					temp.add(menuPeran.getMenu().getIdMenu().toString());
				} else {
				}
			}
			roleMenu.setRoleId(role.getIdPeran().toString());
			roleMenu.setRoleMenus(temp.toArray(new String[temp.size()]));
			roleMenus.add(roleMenu);
		}
		modelAndView.addObject("roleMenus", roleMenus);
		modelAndView.addObject("menuActive", "Kelola Akses Menu");
		modelAndView.setViewName("TambahModul2");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadWizard/2/submit",  method = RequestMethod.POST)
	@ResponseBody
	public Response saveMenus(@RequestBody RoleMenu[] roleMenus, HttpSession session) {
		Modul modul = (Modul)session.getAttribute("moduleOnWizard");
		if(modul == null)
			modul = (Modul)session.getAttribute("moduleOnEdit");
		for(Menu menu: modul.getMenus()) {
			for(MenuPeran menuPeran: menu.getDaftarMenuPeran()) {
				this.menuPeranService.delete(menuPeran);
			}
		}
		List<MenuPeran> failures = new ArrayList<MenuPeran>();
		for(RoleMenu roleMenu: roleMenus) {
			Peran peran = this.peranService.getById(UUID.fromString(roleMenu.getRoleId()));
			for (String menuId : roleMenu.getRoleMenus()) {
				Menu menu = this.menuService.getById(UUID.fromString(menuId));
				MenuPeran menuPeran = new MenuPeran(null, peran, menu);
				MenuPeran result = (MenuPeran)this.menuPeranService.insertInto(menuPeran).getData();
				if(result == null)
					failures.add(menuPeran);
			}
		}
		session.setAttribute("moduleOnWizard", this.modulService.getById(modul.getIdModul()));
		session.removeAttribute("moduleOnEdit");
		if(failures.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("Penambahan gagal pada: \n");
			for(MenuPeran menuPeran: failures) {
				sb.append("- Hak akses peran " + menuPeran.getPeran().getNamaPeran() + " untuk menu " + menuPeran.getMenu().getNamaMenu() + ".\n");
			}
			return new Response(Response.error, sb.toString(), null);
		} else {
			return new Response(Response.ok, "Penambahan hak akses menu pada peran berhasil.", null);
		}
	}
	
	@RequestMapping(value = "/uploadWizard/3", method = RequestMethod.GET)
	public ModelAndView uploadWizard3(@RequestParam(value = "moduleId", required = false) UUID moduleId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Modul moduleShow;
		if(moduleId != null) {
			moduleShow = this.modulService.getById(moduleId);
			modelAndView.addObject("notWizard", true);
			modelAndView.addObject("moduleId", moduleId);
		} else {
			Modul modul = (Modul)session.getAttribute("moduleOnWizard");
			moduleShow = this.modulService.getById(modul.getIdModul());
		}
		if(moduleShow != null) {
			modelAndView.addObject("image", moduleShow.getBase64EncodedImage());
			modelAndView.addObject("icon", moduleShow.getNamaIconTemplate());
		}
		if(session.getAttribute("uploadImageFailed") != null) {
			modelAndView.addObject("uploadImageFailed", (Response)session.getAttribute("uploadImageFailed"));
			session.removeAttribute("uploadImageFailed");
		}
		modelAndView.addObject("menuActive", "Unggah Gambar dan Icon");
		modelAndView.setViewName("TambahModul3");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadWizard/3/submit", method = RequestMethod.POST)
	public ModelAndView saveImageAndIcon(@RequestParam("imageFile") Object imageFile, @RequestParam("iconName") String iconName, @RequestParam(value = "moduleId", required = false) UUID moduleId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Modul module;
		if(moduleId == null) {
			module = (Modul)session.getAttribute("moduleOnWizard");
		} else {
			module = this.modulService.getById(moduleId);
		}
		
		MultipartFile file = (MultipartFile)imageFile;
		if(file.getSize() <= imageSize) {
			try {
				module.setGambar(file.getBytes());
				module.setNamaIconTemplate(iconName);
				this.modulService.update(module);
				if(moduleId == null) {
					modelAndView.setViewName("redirect:/admin/module/uploadWizard/4");
				} else {
					modelAndView.setViewName("redirect:/admin/module/");
				}
				
			} catch (IOException e) {
				session.setAttribute("uploadImageFailed", new Response(Response.error, "Peunggahan gambar gagal. Pesan Exception: " + e.getMessage(), null));
				modelAndView.setViewName("redirect:/admin/module/uploadWizard/3");
				e.printStackTrace();
			}
		} else {
			session.setAttribute("uploadImageFailed", new Response(Response.error, "Peunggahan gambar gagal. Ukuran gambar tidak boleh melebihi 2MB", null));
			modelAndView.setViewName("redirect:/admin/module/uploadWizard/3");
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/uploadWizard/4", method = RequestMethod.GET)
	public ModelAndView uploadWizard4(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Modul modul = (Modul) session.getAttribute("moduleOnWizard");
		modelAndView.addObject("modul", modul);
		modelAndView.addObject("menuActive", "Unggah Selesai");
		modelAndView.setViewName("TambahModul4");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadWizard/4/end", method = RequestMethod.POST)
	public ModelAndView uploadWizardEnd(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("uploadWizardDone", new Response(Response.ok, "Modul berhasil ditambah", null));
		session.removeAttribute("moduleOnWizard");
		modelAndView.setViewName("redirect:/admin/module/");
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Response deleteModule(@RequestParam("idModul") String idModul) {
		Modul modul = modulService.getById(UUID.fromString(idModul));
		Response response = this.modulService.uninstallModule(modul);
		return response; 
	}
	
}
