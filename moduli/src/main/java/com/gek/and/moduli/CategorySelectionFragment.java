package com.gek.and.moduli;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gek.and.moduli.adapter.CategoryGridAdapter;
import com.gek.and.moduli.db.DBCategoryAdapter;
import com.gek.and.moduli.model.Category;
import com.gek.and.moduli.type.CategoryType;

public class CategorySelectionFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    GridView gv = (GridView) inflater.inflate(R.layout.category_grid, container,  false);
	    
//	    gv.setBackgroundColor(SemesterUtil.getSemesterColor(getResources(), getArguments().getBoolean(ARG_WINTER)));
	    CategoryGridAdapter adapter = new CategoryGridAdapter(getActivity(), R.layout.category);
	    adapter.addAll(getCategoryList());
	    gv.setAdapter(adapter);

	    return gv;
	}

	private List<Category> getCategoryList() {
		boolean isWinter = ModuliApp.getApp(getActivity()).isWinter();
		
		DBCategoryAdapter categoryAdapter = new DBCategoryAdapter(getActivity());
		if (isWinter) {
			return categoryAdapter.getCategoriesWinter();
		}
		else {
			return categoryAdapter.getCategoriesSummer();
		}
	}
}
