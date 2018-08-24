package com.gek.and.moduli.type;

public enum WeekDayType {
	MON("MO", "Mo", 0), TUE("DI", "Di", 1), WED("MI", "Mi", 2), THU("DO", "Do", 3), FR("FR", "Fr", 4);
	
	private String code;
	private String shortDesc;
	private int layoutCellIndex;
	
	private WeekDayType(String code, String shortDesc, int layoutCellIndex) {
		this.code = code;
		this.shortDesc = shortDesc;
		this.layoutCellIndex = layoutCellIndex;
	}

	public String getCode() {
		return this.code;
	}

	public String getShort() {
		return this.shortDesc;
	}
	
	public int getLayoutCellIndex() {
		return layoutCellIndex;
	}

	public static WeekDayType parse(String code) {
		if (WeekDayType.MON.getCode().equals(code)) return WeekDayType.MON;
		if (WeekDayType.TUE.getCode().equals(code)) return WeekDayType.TUE;
		if (WeekDayType.WED.getCode().equals(code)) return WeekDayType.WED;
		if (WeekDayType.THU.getCode().equals(code)) return WeekDayType.THU;
		if (WeekDayType.FR.getCode().equals(code)) return WeekDayType.FR;
		throw new IllegalArgumentException("Illegal WeekDayType with code " + code);
	}

}
