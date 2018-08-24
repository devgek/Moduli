package com.gek.and.moduli.db.service;

import java.util.List;

import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.type.GenerationResultType;
import com.gek.and.moduli.type.SeasonType;

public class GenerationResult {
	private GenerationResultType result;
	private SeasonType showSeason;
	private List<Module> modulesWinter;
	private List<Module> modulesSummer;
	
	public GenerationResultType getResult() {
		return result;
	}
	public void setResult(GenerationResultType result) {
		this.result = result;
	}
	public SeasonType getShowSeason() {
		return showSeason;
	}
	public void setShowSeason(SeasonType showSeason) {
		this.showSeason = showSeason;
	}
	public List<Module> getModulesWinter() {
		return modulesWinter;
	}
	public void setModulesWinter(List<Module> modulesWinter) {
		this.modulesWinter = modulesWinter;
	}
	public List<Module> getModulesSummer() {
		return modulesSummer;
	}
	public void setModulesSummer(List<Module> modulesSummer) {
		this.modulesSummer = modulesSummer;
	}
}
