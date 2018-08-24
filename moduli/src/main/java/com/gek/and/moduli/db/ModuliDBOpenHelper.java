package com.gek.and.moduli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ModuliDBOpenHelper extends SQLiteOpenHelper {
	public ModuliDBOpenHelper(Context context) {
		super(context, "moduli", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBCategoryAdapter.getStatementCreate());
		db.execSQL(DBModuleAdapter.getStatementCreate());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DBCategoryAdapter.getStatementDrop());
		db.execSQL(DBModuleAdapter.getStatementDrop());
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}