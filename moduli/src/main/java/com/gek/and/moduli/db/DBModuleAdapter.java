package com.gek.and.moduli.db;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.type.BlockType;
import com.gek.and.moduli.type.RatingType;
import com.gek.and.moduli.type.SeasonType;
import com.gek.and.moduli.type.WeekDayType;

public class DBModuleAdapter {
	final Context context;
	public static final String TABLE_NAME = "module";

	ModuliDBOpenHelper DBHelper;

	public DBModuleAdapter(Context ctx) {
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

	public long insertModule(Module module) {
		ContentValues row = new ContentValues();
		row.put("categoryKey", module.getCategoryKey());
		row.put("title", module.getTitle());
		row.put("courseNumber", module.getNumber());
		row.put("season", module.getSeason().getCode());
		row.put("leader", module.getLeader());
		row.put("day", module.getWeekDay().getCode());
		row.put("block", module.getBlock().getCode());
		row.put("rating", module.getRating().name());
		row.put("description", module.getDescription());
		row.put("annotation", module.getAnnotation());
		row.put("precondition", module.getPrecondition());
		row.put("grading", module.getGrading());
		row.put("booked", module.isBooked() ? 1 : 0);
		
		
		return openForUpdate().insert(TABLE_NAME, null, row);
	}
	
	public long updateModuleBooked(int id, boolean setBooked) {
		ContentValues row = new ContentValues();
		row.put("booked", setBooked ? 1 : 0);
		
		
		return openForUpdate().update(TABLE_NAME, row, "_id=?", new String[]{Integer.toString(id)});
	}

	public List<Module> getModules(String categoryKey, boolean winter) {
		List<Module> modules = new ArrayList<Module>();
		String seasonPar = winter ? SeasonType.WINTER.getCode() : SeasonType.SUMMER.getCode();
		Cursor cursor = openForRead().query(TABLE_NAME, new String[] { "_id", "categoryKey", "title", "courseNumber", "season", "leader", "day", "block", "rating", "description", "annotation", "precondition", "grading", "booked" }, "categoryKey=? and season=?", new String[] {categoryKey, seasonPar}, null, null, null);

		while (cursor.moveToNext()) {
			modules.add(convert(cursor));
		}
		
		cursor.close();
		return modules;
	}

	public List<Module> getBookedModules(boolean winter) {
		List<Module> modules = new ArrayList<Module>();
		String seasonPar = winter ? SeasonType.WINTER.getCode() : SeasonType.SUMMER.getCode();
		Cursor cursor = openForRead().query(TABLE_NAME, new String[] { "_id", "categoryKey", "title", "courseNumber", "season", "leader", "day", "block", "rating", "description", "annotation", "precondition", "grading", "booked" }, "booked=1 and season=?", new String[] {seasonPar}, null, null, null);

		while (cursor.moveToNext()) {
			modules.add(convert(cursor));
		}
		
		cursor.close();
		return modules;
	}

	private Module convert(Cursor cursor) {
		Module module = new Module();
		
		module.setId(cursor.getInt(0));
		module.setCategoryKey(cursor.getString(1));
		module.setTitle(cursor.getString(2));
		module.setNumber(cursor.getString(3));
		module.setSeason(SeasonType.parse(cursor.getString(4)));
		module.setLeader(cursor.getString(5));
		module.setWeekDay(WeekDayType.parse(cursor.getString(6)));
		module.setBlock(BlockType.parse(cursor.getString(7)));
		module.setRating(RatingType.valueOf(cursor.getString(8)));
		module.setDescription(cursor.getString(9));
		module.setAnnotation(cursor.getString(10));
		module.setPrecondition(cursor.getString(11));
		module.setGrading(cursor.getString(12));
		module.setBooked(cursor.getInt(13)==1);
		
		return module;
	}
	
	public static String getStatementCreate() {
		StringBuffer buffer = new StringBuffer("create table ");
		buffer.append(TABLE_NAME);
		buffer.append("(_id integer primary key autoincrement, categoryKey text not null, title text not null, courseNumber text not null, season int not null, leader text not null, day text not null, block text not null, rating text not null, description text, annotation text, precondition text, grading text, booked int not null);");
		
		return buffer.toString();
	}
	
	public static String getStatementDrop() {
		StringBuffer buffer = new StringBuffer("drop table if exists ");
		buffer.append(TABLE_NAME);
		
		return buffer.toString();
	}

}
