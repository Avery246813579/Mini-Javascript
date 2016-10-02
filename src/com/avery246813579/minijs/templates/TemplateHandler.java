package com.avery246813579.minijs.templates;

import java.util.HashMap;
import java.util.Map;

public class TemplateHandler {
	public static final String TEMPLATE_LOCATOIN = "/Templates/Sections/";
	public static final String[] TEMPLATES = {"ignoreMin", "compilationLevel", "active", "export", "compressing"};
	private static Map<String, Template> templates = new HashMap<String, Template>();
	
	
	public TemplateHandler(){
		for(String section : TEMPLATES){
			templates.put(section.toUpperCase(), new Template(section));
		}
	}
	
	public static Template getTemplate(String name){
		Template template = templates.get(name.toUpperCase());
		template.load();
		
		return template;
	}
}
