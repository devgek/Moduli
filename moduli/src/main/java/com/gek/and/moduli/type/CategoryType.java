package com.gek.and.moduli.type;

public enum CategoryType {
	C1("Bewegung und Sport"),
	C2("Bildnerische Erziehung"),
	C3("Darstellende Geometrie"),
	C4("Darstellendes Spiel"),
	C5("Deutsch"),
	C6("Englisch"),
	C7("Geographie und Wirtschaftskunde"),
	C8("Geschichte und Sozialkunde"),
	C9("Informatik"),
	C10("Italienisch"),
	C11("Mathematik"),
	C12("Musikerziehung"),
	C13("Physik"),
	C14("Psychologie und Philosophie"),
	C15("Spanisch");

	public static CategoryType[] winter = new CategoryType[]{C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15};
	public static CategoryType[] summer = new CategoryType[]{C1, C2, C3, C4};
	
	private String title;
	
	CategoryType(String title){
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
