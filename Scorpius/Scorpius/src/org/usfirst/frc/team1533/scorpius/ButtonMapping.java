package org.usfirst.frc.team1533.scorpius;

public enum ButtonMapping { //UPDATE //RENAME //VALUES
	TRIANGLE (3),
	SQUARE (0),
	CIRCLE (2),
	CROSS (1),
	L1 (4),
	L2 (6),
	R1 (5),
	R2 (7),
	SELECT (8),
	START (9)
	;
	private final int mappingID;
	private static final ButtonMapping[] values = ButtonMapping.values(); //Cache for optimization
	ButtonMapping (int mappingid) {mappingID = mappingid/*+1*/;} //UPDATE
	public int GetMappingID () {return mappingID;}
	public boolean equals (ButtonMapping other) {
		if (other == null) return false;
		return other.GetMappingID() == GetMappingID();
	}
	public static ButtonMapping Button (int mapID) {
		for (ButtonMapping type : values) {
            if (type.GetMappingID() == mapID) {
                return type;
            }
        }
        return null;
	}
}
