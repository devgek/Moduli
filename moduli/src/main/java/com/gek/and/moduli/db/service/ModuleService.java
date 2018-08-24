package com.gek.and.moduli.db.service;

import java.util.List;

import android.content.Context;

import com.gek.and.moduli.db.DBModuleAdapter;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.type.GenerationResultType;
import com.gek.and.moduli.type.SeasonType;

public class ModuleService {
	private static ModuleService instance = null;
	
	private DBModuleAdapter moduleDao;
	
	private ModuleService(Context context) {
		this.moduleDao = new DBModuleAdapter(context);
	}
	
	public boolean insertModule(Module module) {
		boolean inserted = false;
		try {
			long rows = moduleDao.insertModule(module);
			if (rows > 0) {
				inserted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inserted;
	}
	
	public boolean bookUnbookModule(Module module, boolean setBooked) {
		boolean booked = false;
		try {
			long updatedRows = moduleDao.updateModuleBooked(module.getId(), setBooked);
			if (updatedRows > 0) {
				booked = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return booked;
	}

	public GenerationResult generateRegistration() {
		GenerationResult res = new GenerationResult();
		res.setResult(GenerationResultType.R_OK);
		
		List<Module> modulesWinter = moduleDao.getBookedModules(true);
		if (modulesWinter.size() > 3) {
			res.setResult(GenerationResultType.R_MORE_THAN_3_PER_SEMESTER);
			res.setShowSeason(SeasonType.WINTER);
			return res;
		}
		
		if (!checkBlocks(modulesWinter)) {
			res.setResult(GenerationResultType.R_MORE_THAN_1_PER_BLOCK);
			res.setShowSeason(SeasonType.WINTER);
			return res;
		}
		
		List<Module> modulesSummer = moduleDao.getBookedModules(false);
		if (modulesSummer.size() > 3) {
			res.setResult(GenerationResultType.R_MORE_THAN_3_PER_SEMESTER);
			res.setShowSeason(SeasonType.SUMMER);
			return res;
		}
		
		if (!checkBlocks(modulesSummer)) {
			res.setResult(GenerationResultType.R_MORE_THAN_1_PER_BLOCK);
			res.setShowSeason(SeasonType.SUMMER);
			return res;
		}
		
		if (modulesWinter.size() + modulesSummer.size() > 5) {
			res.setResult(GenerationResultType.R_MORE_THAN_5_PER_YEAR);
			res.setShowSeason(SeasonType.SUMMER);
			return res;
		}
		
		res.setModulesWinter(modulesWinter);
		res.setModulesSummer(modulesSummer);
		
		return res;
	}
	
	private boolean checkBlocks(List<Module> modules) {
		if (modules.size() < 2) {
			return true;
		}
		boolean[] usedBlocks = new boolean[10];
		
		for (Module m : modules) {
			int blockIndex = m.getWeekDay().getLayoutCellIndex();
			if (m.getBlock().getLayoutCellIndex() == 1) {
				blockIndex += 5;
			}
			if (usedBlocks[blockIndex]) {
				return false;
			}
			usedBlocks[blockIndex] = true;
		}
		
		return true;
	}

	public static synchronized ModuleService getInstance(Context context) {
		if (instance == null) {
			instance = new ModuleService(context);
		}
		
		return instance;
	}
}
