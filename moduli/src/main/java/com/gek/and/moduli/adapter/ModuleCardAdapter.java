package com.gek.and.moduli.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gek.and.moduli.R;
import com.gek.and.moduli.db.DBModuleAdapter;
import com.gek.and.moduli.db.service.ModuleService;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.util.SemesterUtil;

public class ModuleCardAdapter extends ArrayAdapter<Module> {
	static class CardViewHolder {
		TextView line1;
		TextView line2;
		TextView line3;
		TextView line4;
		TextView line5;
		TextView description;
		TextView rating;
		int position;
	}
	private Activity assignedActivity;

	public ModuleCardAdapter(Context context, int resource, Activity assignedActivity) {
		super(context, resource);
		this.assignedActivity = assignedActivity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View card = convertView;
		CardViewHolder viewHolder;
		if (card == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			card = inflater.inflate(R.layout.module, parent, false);
			
			handleLongClick(card);

			CardView cardView = (CardView)card;
//			Setting the cards background color to the main semester color
//			cardView.setCardBackgroundColor(SemesterUtil.getSemesterColor(assignedActivity));

			viewHolder = new CardViewHolder();
			viewHolder.line1 = (TextView) card.findViewById(R.id.module_line1);
			viewHolder.line2 = (TextView) card.findViewById(R.id.module_line2);
			viewHolder.line3 = (TextView) card.findViewById(R.id.module_line3);
			viewHolder.line4 = (TextView) card.findViewById(R.id.module_line4);
			viewHolder.line5 = (TextView) card.findViewById(R.id.module_line5);
			viewHolder.description = (TextView) card.findViewById(R.id.module_description);
			viewHolder.rating = (TextView) card.findViewById(R.id.module_rating);
			
			card.setTag(viewHolder);
		} else {
			viewHolder = (CardViewHolder) card.getTag();
		}
		
		Module module = getItem(position);

		viewHolder.line1.setText(module.getTitle());
		viewHolder.line2.setText("Kursnummer: " + module.getNumber());
		viewHolder.description.setText(module.getDescription());
		viewHolder.line3.setText("Kursleiter: " + module.getLeader());
		viewHolder.line4.setText("Termin: " + module.getSeason().getShort() + ", " + module.getWeekDay().getShort() + " " + module.getBlock().getShort());
		viewHolder.line5.setText("Anmerkungen: " + module.getAnnotation());
		viewHolder.rating.setText(module.getRating().name());
		viewHolder.position = position;
		
		return card;
	}

	private void handleLongClick(View card) {
		card.setOnLongClickListener(new View.OnLongClickListener(){
		    @Override
		    public boolean onLongClick(View v) {
		    	CardViewHolder holder = (CardViewHolder) v.getTag();
		    	Module module = getItem(holder.position);
		    	ModuleService moduleService = ModuleService.getInstance(getContext());
		    	boolean booked = moduleService.bookUnbookModule(module, true);
		    	if (booked) {
					Toast.makeText(getContext(), "Modul gebucht.", Toast.LENGTH_SHORT).show();
		    	}
		    	else {
					Toast.makeText(getContext(), "Modul konnte nicht gebucht werden.", Toast.LENGTH_SHORT).show();
		    	}
				assignedActivity.finish();
		    	return true;
		    }
		});
	}
	@Override
	public Module getItem(int position) {
		return super.getItem(position);
	}
	
	private boolean bookModule(Module module) {
		boolean booked = false;
		try {
			DBModuleAdapter dbModuleAdapter = new DBModuleAdapter(assignedActivity);
			long updatedRows = dbModuleAdapter.updateModuleBooked(module.getId(), true);
			if (updatedRows > 0) {
				booked = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return booked;
	}

}
