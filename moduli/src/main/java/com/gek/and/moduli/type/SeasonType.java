package com.gek.and.moduli.type;

public enum SeasonType {
	WINTER("W", "WS"), SUMMER("S", "SS");
	
	private String code;
	private String shortDesc;
	
	private SeasonType(String code, String shortDesc) {
		this.code = code;
		this.shortDesc = shortDesc;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getShort() {
		return this.shortDesc;
	}
	
	public static SeasonType parse(String code) {
		if (SeasonType.WINTER.getCode().equals(code)) return SeasonType.WINTER;
		if (SeasonType.SUMMER.getCode().equals(code)) return SeasonType.SUMMER;
		throw new IllegalArgumentException("Illegal SeasonType with code " + code);
	}
}
