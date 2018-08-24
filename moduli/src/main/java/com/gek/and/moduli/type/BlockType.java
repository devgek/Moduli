package com.gek.and.moduli.type;

public enum BlockType {
	NB1("NB1", "1.NB", 0), NB2("NB2", "2.NB", 1);
	
	private String code;
	private String shortDesc;
	private int layoutCellIndex;
	
	private BlockType(String code, String shortDesc, int layoutCellIndex) {
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

	public static BlockType parse(String code) {
		if (BlockType.NB1.getCode().equals(code)) return BlockType.NB1;
		if (BlockType.NB2.getCode().equals(code)) return BlockType.NB2;
		throw new IllegalArgumentException("Illegal BlockType with code " + code);
	}

}
