package org.usfirst.frc.team1533.scorpius;

public enum ButtonMapping { //UPDATE //RENAME //VALUES
	B (3),
	Y (4),
	A (2),
	X (1),
	LEFT_BUMPER (5),
	LEFT_TRIGGER (7),
	RIGHT_BUMPER (6),
	RIGHT_TRIGGER (8),
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
