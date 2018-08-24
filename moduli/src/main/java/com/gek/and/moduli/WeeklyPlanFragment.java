package com.gek.and.moduli;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gek.and.moduli.adapter.WeeklyPlanRowListAdapter;
import com.gek.and.moduli.db.DBModuleAdapter;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.util.SemesterUtil;
import com.gek.and.moduli.view.LinearLayoutList;

public class WeeklyPlanFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.weekly_plan, container,  false);
	    
	    View headerView = view.findViewById(R.id.planTableRow1);
//	    headerView.setBackgroundColor(SemesterUtil.getSemesterColor(getActivity()));
//	    headerView.setBackgroundColor(getResources().getColor(R.color.my_dark_grey));
	    
	    DBModuleAdapter dbModuleAdapter = new DBModuleAdapter(getActivity());
	    List<Module> bookedModules = dbModuleAdapter.getBookedModules(ModuliApp.getApp(getActivity()).isWinter());
	    
	    if (!bookedModules.isEmpty()) {
	    	WeeklyPlanCell[][] cellArray = new WeeklyPlanCell[5][2];
	    	cellArray[0][0] = new WeeklyPlanCell();
	    	cellArray[0][0].layoutCellId = R.id.planTableCell11;
	    	cellArray[0][1] = new WeeklyPlanCell();
	    	cellArray[0][1].layoutCellId = R.id.planTableCell12;
	    	cellArray[1][0] = new WeeklyPlanCell();
	    	cellArray[1][0].layoutCellId = R.id.planTableCell21;
	    	cellArray[1][1] = new WeeklyPlanCell();
	    	cellArray[1][1].layoutCellId = R.id.planTableCell22;
	    	cellArray[2][0] = new WeeklyPlanCell();
	    	cellArray[2][0].layoutCellId = R.id.planTableCell31;
	    	cellArray[2][1] = new WeeklyPlanCell();
	    	cellArray[2][1].layoutCellId = R.id.planTableCell32;
	    	cellArray[3][0] = new WeeklyPlanCell();
	    	cellArray[3][0].layoutCellId = R.id.planTableCell41;
	    	cellArray[3][1] = new WeeklyPlanCell();
	    	cellArray[3][1].layoutCellId = R.id.planTableCell42;
	    	cellArray[4][0] = new WeeklyPlanCell();
	    	cellArray[4][0].layoutCellId = R.id.planTableCell51;
	    	cellArray[4][1] = new WeeklyPlanCell();
	    	cellArray[4][1].layoutCellId = R.id.planTableCell52;
	    	
	    	populateWeeklyPlan(cellArray, bookedModules, view);
	    }
	    
	    return view;
	}

	private void populateWeeklyPlan(WeeklyPlanCell[][] cellArray, List<Module> bookedModules, View parent) {
		for (Module module : bookedModules) {
			int i = module.getWeekDay().getLayoutCellIndex();
			int j = module.getBlock().getLayoutCellIndex();
			
			WeeklyPlanRowListAdapter cellListAdapter = null;
			if (cellArray[i][j].cellLayout == null) {
				cellArray[i][j].cellLayout = (LinearLayoutList) parent.findViewById(cellArray[i][j].layoutCellId);
				cellListAdapter = new WeeklyPlanRowListAdapter(getActivity(), R.layout.plan_list_row);
				cellArray[i][j].cellLayout.setAdapter(cellListAdapter);
			}
			else {
				cellListAdapter = (WeeklyPlanRowListAdapter) cellArray[i][j].cellLayout.getAdapter();
			}
			
			cellListAdapter.add(module);
		}
	}
	
	private class WeeklyPlanCell {
		private int layoutCellId;
		private LinearLayoutList cellLayout;
	}

}
