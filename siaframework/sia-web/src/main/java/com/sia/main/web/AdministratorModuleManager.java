package com.sia.main.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.sia.main.domain.Menu;
import com.sia.main.domain.Modul;

public class AdministratorModuleManager {

	private static AdministratorModuleManager instance;
	
	public static AdministratorModuleManager getInstance() {
		if(instance == null) {
			instance = new AdministratorModuleManager();
		}
		return instance;
	}
	
	private List<Modul> modules;
	
	public List<Modul> getModules() {
		return modules;
	}

	public void setModules(List<Modul> modules) {
		this.modules = modules;
	}

	private AdministratorModuleManager() {
		this.modules = new ArrayList<Modul>();
		
		AdministratorModule modul = new AdministratorModule();
		List<Menu> menus = new ArrayList<Menu>();
		modul = new AdministratorModule();
		modul.setIdModul(UUID.randomUUID());
		modul.setNamaModul("Modul");
		modul.setLokasiKonfigServlet("/WEB-INF/spring-beans/servlet/sia-servlet.xml");
		modul.setUrlMapping("/admin/*");
		modul.setNamaIconTemplate("fa fa-puzzle-piece");
		try {
			BufferedImage img = ImageIO.read(new URL("http://10.151.44.20:9191/sia-web/resources/images/modul.png"));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, "png", os);
			os.flush();
			modul.setGambar(os.toByteArray());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		menus.add(new Menu(UUID.randomUUID(), modul, "Tambah Modul", "/module/uploadWizard/1", null));
		menus.add(new Menu(UUID.randomUUID(), modul, "Kelola Modul", "/module", null));
		modul.setMenus(menus);
		modules.add(modul);
		
		modul = new AdministratorModule();
		menus = new ArrayList<Menu>();
		modul.setIdModul(UUID.randomUUID());
		modul.setNamaModul("Hak Akses");
		modul.setLokasiKonfigServlet("/WEB-INF/spring-beans/servlet/sia-servlet.xml");
		modul.setUrlMapping("/admin/*");
		modul.setNamaIconTemplate("fa fa-key");
		try {
			BufferedImage img = ImageIO.read(new URL("http://10.151.44.20:9191/sia-web/resources/images/hak%20akses.png"));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, "png", os);
			os.flush();
			modul.setGambar(os.toByteArray());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		menus.add(new Menu(UUID.randomUUID(), modul, "Kelola Pengguna", "/user", null));
		menus.add(new Menu(UUID.randomUUID(), modul, "Kelola Peran", "/role", null));
		modul.setMenus(menus);
		modules.add(modul);
	}
	
}
