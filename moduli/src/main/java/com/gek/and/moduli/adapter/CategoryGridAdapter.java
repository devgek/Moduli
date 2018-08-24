package com.gek.and.moduli.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gek.and.moduli.AppConstants;
import com.gek.and.moduli.R;
import com.gek.and.moduli.activity.ModuleSelectionActivity;
import com.gek.and.moduli.model.Category;
import com.gek.and.moduli.util.SemesterUtil;

public class CategoryGridAdapter extends ArrayAdapter<Category> {
	
	static class CategoryCardViewHolder {
		TextView line1;
		int position;
	}
	
	public CategoryGridAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public Category getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Category category = getItem(position);
		
		View view = convertView;
		CategoryCardViewHolder viewHolder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.category, parent, false);
			
			CardView cardView = (CardView)view;
			cardView.setCardBackgroundColor(SemesterUtil.getSemesterColor((Activity) getContext()));
			
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CategoryCardViewHolder holder = (CategoryCardViewHolder) v.getTag();
					startModuleSelection(holder.position);
				}
			});
			
			viewHolder = new CategoryCardViewHolder();
			viewHolder.line1 = (TextView) view.findViewById(R.id.category_line1);
			viewHolder.position = position;
			
			view.setTag(viewHolder);
		} 
		else {
			viewHolder = (CategoryCardViewHolder) view.getTag();
		}
		

		viewHolder.line1.setText(category.getName());
		return view;
	}

	protected void startModuleSelection(int position) {
//		Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
		Category category = getItem(position);
		
    	Intent intent = new Intent(getContext(), ModuleSelectionActivity.class);
    	intent.putExtra(AppConstants.INTENT_EXTRA_CATEGORY_KEY, category.getKey());
    	intent.putExtra(AppConstants.INTENT_EXTRA_CATEGORY_NAME, category.getName());
        	
		getContext().startActivity(intent);
	}

}
