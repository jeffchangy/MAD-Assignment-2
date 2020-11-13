package com.example.madassignment2.object;

public class Structure {
    private int drawableId;
    private String label, type;

    public static final String ROAD = "road";
    public static final String COMM = "commercial";
    public static final String RES = "residential";

    public Structure(String type, int drawableId, String label) {
        this.type = type;
        this.drawableId = drawableId;
        this.label = label;
    }

    public Structure clone() {
        return new Structure(this.type, this.drawableId, this.label);
    }
    public String getType() { return type; }
    public int getDrawableId() {
        return drawableId;
    }
    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }
    public void setType(String type) { this.type = type; }
    public void setDrawableId(int drawableId) { this.drawableId = drawableId; }
}
