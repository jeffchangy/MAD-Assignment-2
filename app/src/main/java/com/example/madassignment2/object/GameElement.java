package com.example.madassignment2.object;

import android.graphics.Bitmap;
import android.widget.Chronometer;

import com.example.madassignment2.R;

public class GameElement {

    private Structure structure;
    private Bitmap image;
    private String ownerName;

    public GameElement(Structure structure, Bitmap image, String ownerName) {
        this.structure = structure;
        this.image = image;
        this.ownerName = ownerName;
    }

    public Structure getStructure()
    {
        return structure;
    }
    public Bitmap getImage() {
        return image;
    }
    public String getOwnerName() {
        return ownerName;
    }

    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
