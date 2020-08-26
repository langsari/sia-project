package com.sia.main.service.module;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.osgi.framework.Bundle;
import org.xml.sax.SAXException;

import com.sia.main.domain.Menu;
import com.sia.main.domain.Modul;
import com.sia.main.plugin.modul.ModuleReader;

public class XmlModuleReaderBean implements ModuleReader {

	private String moduleDetailXmlPath;

	private String prefix;
	
	public void setModuleDetailXmlPath(String moduleDetailXmlPath) {
		this.moduleDetailXmlPath = moduleDetailXmlPath;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public Modul readModule(Object module, Object host) throws Exception {
		Bundle moduleBundle = (Bundle) module;
		Bundle hostBundle = (Bundle) host;
		Modul modul = null;
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			String moduleDetailLocation = this.moduleDetailXmlPath + this.prefix + moduleBundle.getSymbolicName() + ".xml";
			URL fileUrl = hostBundle.getResource(moduleDetailLocation);
			if(fileUrl == null) {
				throw new NullPointerException("Berkas xml tidak ditemukan");
			}
			ModuleXmlParseHandler parseHandler = new ModuleXmlParseHandler();
			saxParser.parse(fileUrl.openStream(), parseHandler);
			modul = parseHandler.getGeneratedModule();
			if(modul.getNamaModul() == null || modul.getNamaModul().equals("")) throw new NullPointerException("Nama modul tidak boleh kosong");
			if(modul.getUrlMapping() == null || modul.getUrlMapping().equals("")) throw new NullPointerException("Pemetaan URL modul tidak boleh kosong");
			if(modul.getLokasiKonfigServlet() == null || modul.getLokasiKonfigServlet().equals("")) throw new NullPointerException("Lokasi konfigurasi servlet modul tidak boleh kosong");
			if(modul.getMenus() == null || modul.getMenus().size() == 0) throw new NullPointerException("Tidak ada menu terdaftar pada modul");
			else {
				for(Menu menu: modul.getMenus()) {
					if(menu.getNamaMenu() == null || menu.getNamaMenu().equals("")) throw new NullPointerException("Nama menu tidak boleh kosong");
					if(menu.getUrlMenu() == null || menu.getUrlMenu().equals("")) throw new NullPointerException("URL menu tidak boleh kosong");
				}
			}
			for(String configLocation: modul.getServletConfigLocationList()) {
				URL configLocUrl = hostBundle.getResource(configLocation);
				if(configLocUrl == null) {
					throw new NullPointerException("Konfigurasi servlet tidak ditemukan");
				}
			}
		} catch (ParserConfigurationException e) {
			StackTraceElement[] stackTraces = e.getStackTrace();
			ParserConfigurationException exception = new ParserConfigurationException("Pembacaan detail modul pada berkas xml gagal. Pesan Exception: " + e.getMessage());
			exception.setStackTrace(stackTraces);
			throw exception;
		} catch(SAXException e) {
			StackTraceElement[] stackTraces = e.getStackTrace();
			SAXException exception = new SAXException("Pembacaan detail modul pada berkas xml gagal. Pesan Exception: " + e.getMessage());
			exception.setStackTrace(stackTraces);
			throw exception;
		} catch(IOException e) {
			StackTraceElement[] stackTraces = e.getStackTrace();
			IOException exception = new IOException("Pembacaan detail modul pada berkas xml gagal. Pesan Exception: " + e.getMessage());
			exception.setStackTrace(stackTraces);
			throw exception;
		} catch (NullPointerException e) {
			StackTraceElement[] stackTraces = e.getStackTrace();
			NullPointerException exception = new NullPointerException("Pembacaan detail modul pada berkas xml gagal. Pesan Exception: " + e.getMessage());
			exception.setStackTrace(stackTraces);
			throw exception;
		}
		return modul;
	}

}
