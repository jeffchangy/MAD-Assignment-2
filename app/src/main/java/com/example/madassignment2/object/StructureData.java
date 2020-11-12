package com.example.madassignment2.object;

import com.example.madassignment2.R;

import java.util.Arrays;
import java.util.List;

public class StructureData
{
    private List<Structure> structureList = Arrays.asList(
            new Structure(Structure.RES, R.drawable.ic_building1, "Res House 1"),
            new Structure(Structure.RES, R.drawable.ic_building2, "Res House 2"),
            new Structure(Structure.RES, R.drawable.ic_building3, "Res House 3"),
            new Structure(Structure.RES, R.drawable.ic_building4, "Res House 4"),
            new Structure(Structure.COMM, R.drawable.ic_building5, "Comm House 1"),
            new Structure(Structure.COMM, R.drawable.ic_building6, "Comm House 2"),
            new Structure(Structure.COMM, R.drawable.ic_building7, "Comm House 3"),
            new Structure(Structure.COMM, R.drawable.ic_building8, "Comm House 4"),
            new Structure(Structure.ROAD, R.drawable.ic_road_ns, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_ew, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_nsew, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_ne, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_nw, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_se, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_sw, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_n, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_e, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_s, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_w, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_nse, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_nsw, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_new, "Road"),
            new Structure(Structure.ROAD, R.drawable.ic_road_sew, "Road"));

    private static StructureData instance = null;

    public static StructureData get()
    {
        if(instance == null)
        {
            instance = new StructureData();
        }
        return instance;
    }

    protected StructureData() {}

    public Structure get(int i)
    {
        return structureList.get(i);
    }

    public List<Structure> getStructureList() { return structureList; }

    public int size()
    {
        return structureList.size();
    }

    public void add(Structure s)
    {
        structureList.add(0, s);
    }

    public void remove(int i)
    {
        structureList.remove(i);
    }

}
