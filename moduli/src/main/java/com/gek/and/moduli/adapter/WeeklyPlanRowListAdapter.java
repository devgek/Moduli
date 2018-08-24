package com.gek.and.moduli.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gek.and.moduli.R;
import com.gek.and.moduli.db.service.ModuleService;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.util.SemesterUtil;

public class WeeklyPlanRowListAdapter extends ArrayAdapter<Module> {
	static class PlanRowViewHolder {
		ImageView moduleSignView;
		TextView moduleTitleView;
		ImageButton moduleDeleteButton;
		int position;
	}

	private Activity activity;
	
	public WeeklyPlanRowListAdapter(Activity activity, int resource) {
		super(activity, resource);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View moduleSelectionView = convertView;
		PlanRowViewHolder viewHolder;
		if (moduleSelectionView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			moduleSelectionView = inflater.inflate(R.layout.plan_list_row, parent, false);
			
			viewHolder = new PlanRowViewHolder();
			viewHolder.moduleSignView = (ImageView) moduleSelectionView.findViewById(R.id.selected_module_sign_plan);
			viewHolder.moduleTitleView = (TextView) moduleSelectionView.findViewById(R.id.selected_module_title_plan);
			viewHolder.moduleDeleteButton = (ImageButton) moduleSelectionView.findViewById(R.id.selected_module_delete_plan);
			viewHolder.moduleDeleteButton.setTag(Integer.valueOf(position));
			viewHolder.position = position;
			viewHolder.moduleSignView.setBackgroundColor(SemesterUtil.getSemesterColor(activity));
			
			handleButtonClick(viewHolder.moduleDeleteButton);
			
			moduleSelectionView.setTag(viewHolder);
		} else {
			viewHolder = (PlanRowViewHolder) moduleSelectionView.getTag();
		}
		
		Module module = getItem(position);

		viewHolder.moduleTitleView.setText(module.getTitle());
		
		return moduleSelectionView;
	}

	private void handleButtonClick(ImageButton moduleDeleteButton) {
		moduleDeleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Integer positionInt = (Integer) v.getTag();
				Module module = getItem(positionInt.intValue());
		    	ModuleService moduleService = ModuleService.getInstance(getContext());
		    	boolean unbooked = moduleService.bookUnbookModule(module, false);
		    	if (unbooked) {
					remove(getItem(positionInt.intValue()));
					notifyDataSetChanged();
					Toast.makeText(getContext(), "Modul gelöscht.", Toast.LENGTH_SHORT).show();
		    	}
		    	else {
					Toast.makeText(getContext(), "Modul konnte nicht gelöscht werden.", Toast.LENGTH_SHORT).show();
		    	}

			}
		});
	}

	@Override		
	public Module getItem(int position) {
		return super.getItem(position);
	}

}
