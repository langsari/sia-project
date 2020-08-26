package com.sia.main.service.module;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sia.main.domain.Menu;
import com.sia.main.domain.Modul;

public class ModuleXmlParseHandler extends DefaultHandler {

	private Modul generatedModule;
	
	private Menu generatedMenu;
	
	private boolean inTagModule = false;
	
	private boolean inTagName = false;
	
	private boolean inTagUrlMapping = false;
	
	private boolean inTagConfigLocations = false;

	private boolean inTagMenues = false;
	
	private boolean inTagMenu = false;
	
	private boolean inTagUrl = false;
	
	public Modul getGeneratedModule() {
		return this.generatedModule;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("module")) {
			this.generatedModule = new Modul();
			inTagModule = true;
		}
		
		if(qName.equalsIgnoreCase("name") && inTagModule) {
			inTagName = true;
		} else if(qName.equalsIgnoreCase("name") && !inTagModule) {
			throw new SAXException("<name> harus berada di dalam <module>");
		}
		
		if(qName.equalsIgnoreCase("url-mapping") && inTagModule) {
			inTagUrlMapping = true;
		} else if(qName.equalsIgnoreCase("url-mapping") && !inTagModule) {
			throw new SAXException("<url-mapping> harus berada di dalam <module>");
		}
		
		if(qName.equalsIgnoreCase("config-locations") && inTagModule) {
			inTagConfigLocations = true;
		} else if(qName.equalsIgnoreCase("config-locations") && !inTagModule) {
			throw new SAXException("<config-locations> harus berada di dalam <module>");
		}
		
		if(qName.equalsIgnoreCase("menues")  && inTagModule) {
			inTagMenues = true;
		} else if(qName.equalsIgnoreCase("menues") && !inTagModule) {
			throw new SAXException("<menues> harus berada di dalam <module>");
		}
		
		if(qName.equalsIgnoreCase("menu") && inTagMenues) {
			this.generatedMenu = new Menu();
			inTagMenu = true;
		} else if(qName.equalsIgnoreCase("menu") && !inTagMenues) {
			throw new SAXException("<menu> harus berada di dalam <menues>");
		}
		
		if(qName.equalsIgnoreCase("url") && inTagMenu) {
			inTagUrl = true;
		} else if(qName.equalsIgnoreCase("url") && !inTagMenu) {
			throw new SAXException("<url> harus berada di dalam <menu>");
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("module")) {
			inTagModule = false;
		}
		if(qName.equalsIgnoreCase("name")) {
			inTagName = false;
		}
		if(qName.equalsIgnoreCase("url-mapping")) {
			inTagUrlMapping = false;
		}
		if(qName.equalsIgnoreCase("config-locations")) {
			inTagConfigLocations = false;
		}
		if(qName.equalsIgnoreCase("menues")) {
			inTagMenues = false;
		}
		if(qName.equalsIgnoreCase("menu")) {
			this.generatedModule.addMenu(this.generatedMenu);
			this.generatedMenu = null;
			inTagMenu = false;
		}
		if(qName.equalsIgnoreCase("url")) {
			inTagUrl = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if(inTagName) {
			if(inTagMenu) {
				this.generatedMenu.setNamaMenu(new String(ch, start, length));
			} else {
				this.generatedModule.setNamaModul(new String(ch, start, length));
			}
		}
		if(inTagUrlMapping) {
			this.generatedModule.setUrlMapping(new String(ch, start, length));
		}
		if(inTagConfigLocations) {
			this.generatedModule.setLokasiKonfigServlet(new String(ch, start, length));
		}
		if(inTagUrl) {
			this.generatedMenu.setUrlMenu(new String(ch, start, length));
		}
	}
	
}
