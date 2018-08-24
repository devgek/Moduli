package com.gek.and.moduli.model;

public class Category {
	private int id;
	private String key;
	private String name;
	private boolean winter;
	private boolean summer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isWinter() {
		return winter;
	}
	public void setWinter(boolean winter) {
		this.winter = winter;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isSummer() {
		return summer;
	}
	public void setSummer(boolean summer) {
		this.summer = summer;
	}
}
