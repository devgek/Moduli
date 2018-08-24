package com.gek.and.moduli.model;

import com.gek.and.moduli.type.BlockType;
import com.gek.and.moduli.type.RatingType;
import com.gek.and.moduli.type.SeasonType;
import com.gek.and.moduli.type.WeekDayType;

public class Module {
	private int id;
	private String categoryKey;
	private String title;
	private String number;
	private SeasonType season;
	private String leader;
	private WeekDayType weekDay;
	private BlockType block;
	private RatingType rating;
	private String description;
	private String annotation;
	private String precondition;
	private String grading;
	private boolean booked;
	
	public boolean isComment() {
		return categoryKey != null && categoryKey.equals("99");
	}
	
	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public String getGrading() {
		return grading;
	}
	public void setGrading(String grading) {
		this.grading = grading;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryKey() {
		return categoryKey;
	}
	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public SeasonType getSeason() {
		return season;
	}
	public void setSeason(SeasonType season) {
		this.season = season;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public WeekDayType getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(WeekDayType weekDay) {
		this.weekDay = weekDay;
	}
	public BlockType getBlock() {
		return block;
	}
	public void setBlock(BlockType block) {
		this.block = block;
	}
	public RatingType getRating() {
		return rating;
	}
	public void setRating(RatingType rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public String getPrecondition() {
		return precondition;
	}
	public void setPrecondition(String precondition) {
		this.precondition = precondition;
	}
	
}
