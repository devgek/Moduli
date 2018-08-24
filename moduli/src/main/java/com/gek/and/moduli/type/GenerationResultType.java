package com.gek.and.moduli.type;

public enum GenerationResultType {
	R_OK(0, "Anmeldung generiert."), R_MORE_THAN_3_PER_SEMESTER(1, "Mehr als 3 Module pro Semester."), R_MORE_THAN_5_PER_YEAR(2, "Mehr als 5 Module pro Jahr."), R_MORE_THAN_1_PER_BLOCK(3, "Block mehrfach belegt.");
	
	private int result;
	private String message;
	
	private GenerationResultType(int result, String message) {
		this.result = result;
		this.message = message;
	}
	
	public int getResult() {
		return this.result;
	}
	
	public String getMessage() {
		return this.message;
	}
}
