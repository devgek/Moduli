package com.gek.and.moduli.db;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gek.and.moduli.model.Category;

public class DBCategoryAdapter {
	final Context context;
	public static final String TABLE_NAME = "category";

	ModuliDBOpenHelper DBHelper;

	public DBCategoryAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new ModuliDBOpenHelper(context);
	}

	private SQLiteDatabase openForRead() {
		return DBHelper.getReadableDatabase();
	}
	private SQLiteDatabase openForUpdate() {
		return DBHelper.getWritableDatabase();
	}

	public void close() {
		DBHelper.close();
	}

	public long insertCategory(Category category) {
		ContentValues row = new ContentValues();
		row.put("key", category.getKey());
		row.put("name", category.getName());
		row.put("winter", category.isWinter() ? 1 : 0);
		row.put("summer", category.isSummer() ? 1 : 0);
		
		return openForUpdate().insert(TABLE_NAME, null, row);
	}

	public List<Category> getCategoriesWinter() {
		List<Category> categories = new ArrayList<Category>();
		Cursor cursor = openForRead().query(TABLE_NAME, new String[] { "_id", "key", "name", "winter", "summer" }, "winter=1", null, null, null, null);

		while (cursor.moveToNext()) {
			categories.add(convert(cursor));
		}
		
		cursor.close();
		return categories;
	}
	
	public List<Category> getCategoriesSummer() {
		List<Category> categories = new ArrayList<Category>();
		Cursor cursor = openForRead().query(TABLE_NAME, new String[] { "_id", "key", "name", "winter", "summer" }, "summer=1", null, null, null, null);

		while (cursor.moveToNext()) {
			categories.add(convert(cursor));
		}
		
		cursor.close();
		return categories;
	}
	
	private Category convert(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getInt(0));
		category.setKey(cursor.getString(1));
		category.setName(cursor.getString(2));
		category.setWinter(cursor.getInt(3) == 1);
		category.setSummer(cursor.getInt(4) == 1);
		
		return category;
	}

	public static String getStatementCreate() {
		StringBuffer buffer = new StringBuffer("create table ");
		buffer.append(TABLE_NAME);
		buffer.append("(_id integer primary key autoincrement, key text not null, name text not null, winter int not null, summer int not null);");
		
		return buffer.toString();
	}
	
	public static String getStatementDrop() {
		StringBuffer buffer = new StringBuffer("drop table if exists ");
		buffer.append(TABLE_NAME);
		
		return buffer.toString();
	}
}
