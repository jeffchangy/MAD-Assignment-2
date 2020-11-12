package com.example.madassignment2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment2.activities.DetailsMenu;
import com.example.madassignment2.object.GameData;
import com.example.madassignment2.object.GameElement;
import com.example.madassignment2.R;
import com.example.madassignment2.object.Setting;
import com.example.madassignment2.object.Structure;

import java.sql.SQLOutput;
import java.util.List;


public class FragmentMap extends Fragment {

    //CLASSFIELD
    private Setting set;
    private FragmentSelect fs;
    private FragmentStatus fStatus;
    private MapAdapter mapAdapter;
    private MapHolder holder;
    private Bitmap thumbnail;
    int moneyPlaceHolder, nRes = 0, nComm = 0;
    int currPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.map_recycler_view, ui, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.MapRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GameData.get().getSetting().getMapHeight(), GridLayoutManager.HORIZONTAL, false));

        mapAdapter = new MapAdapter();
        recyclerView.setAdapter(mapAdapter);
        return view;
    }

    public void setSelectFragment(FragmentSelect fs) {
        this.fs = fs;
    }

    public void setStatusFragment(FragmentStatus fStatus) {
        this.fStatus = fStatus;
    }

    private class MapAdapter extends RecyclerView.Adapter<MapHolder> {
        //private GameElement[][] data;

        public MapAdapter() {
            //this.data = GameData.get().getGrid();
        }

        @Override
        public MapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MapHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MapHolder holder, int position) {
            //int row = position % GameData.get().getSetting().getMapHeight();
            //int col = position / GameData.get().getSetting().getMapHeight();
            holder.bind(GameData.get().get(position));
        }

        @Override
        public int getItemCount() {
            return GameData.get().getSetting().getMapHeight() * GameData.get().getSetting().getMapWidth();
        }
    }

    private class MapHolder extends RecyclerView.ViewHolder {

        private ImageView imageStructure, imageBackground;
        private GameElement map;

        public MapHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.grid_cell, parent, false));

            // Making the grid cells to be square
            int size = parent.getMeasuredHeight() / GameData.get().getSetting().getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();

            lp.width = size;
            lp.height = size;

            //need two layers of image otherwise background grass will be removed and will show white space
            imageStructure = (ImageView) itemView.findViewById(R.id.structure);
            imageBackground = (ImageView) itemView.findViewById(R.id.background);

            imageStructure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fs.getSelectedStructure() != null && fStatus.getClick() == false && map.getStructure() == null) {
                        //implement building must be adjacent to road before selection of other building, directly below this comment!!!
                        if (isAdjacent() == true || fs.getSelectedStructure().getType().equals(Structure.ROAD)) {
                            if (fs.getSelectedStructure().getType().equals(Structure.RES)) {
                                addResBuilding();
                                nRes++;
                            } else if (fs.getSelectedStructure().getType().equals(Structure.COMM)) {
                                addCommBuilding();
                                nComm++;
                            } else if (fs.getSelectedStructure().getType().equals(Structure.ROAD)) {
                                addRoad();
                            }
                            fs.setSelectedStructure(null);
                            fStatus.updateMoney();
                            mapAdapter.notifyItemChanged(getAdapterPosition());
                        } else {
                            Toast.makeText(getContext(), "Building must be placed next to a road", Toast.LENGTH_SHORT).show();
                        }
                    } else if (map.getStructure() != null && fStatus.getClick() == false && fStatus.getDetailClick() == false) {
                        Toast.makeText(getContext(), "cannot build on terrain", Toast.LENGTH_SHORT).show(); //if nothing or non buildable object is selected
                    }

                    //access status demolish button in fragment status
                    if (fStatus.getClick() == true && map.getStructure() != null)
                        demolishBuilding();

                    //access the detail screen depending on the seelction of objectType
                    if (fStatus.getDetailClick() == true && map.getStructure() != null)
                        enterDetailScreenOnClick();
                }
            });

        }

        public void bind(GameElement map) {
            this.map = map;
            GameData.get().setSelectedElement(map);
            //currPos = getAdapterPosition();
            imageBackground.setImageResource(R.drawable.ic_grass4);
            if (map.getImage() != null) {
                imageStructure.setImageBitmap(map.getImage());
            } else if (map.getStructure() != null) {
                imageStructure.setImageResource(map.getStructure().getDrawableId());
            } else {
                //transparent
                imageStructure.setImageResource(0);
            }
        }

        //adding certain buildings, taking in account of cost
        public void addResBuilding() {
            moneyPlaceHolder = GameData.get().getMoney();

            if (GameData.get().getMoney() >= GameData.get().getSetting().getHouseBuildingCost()) {
                moneyPlaceHolder = moneyPlaceHolder - GameData.get().getSetting().getHouseBuildingCost();
                GameData.get().setMoney(moneyPlaceHolder);
                map.setStructure(fs.getSelectedStructure());
            } else {
                Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_SHORT).show();
            }
        }

        public void addCommBuilding() {
            moneyPlaceHolder = GameData.get().getMoney();

            if (GameData.get().getMoney() >= GameData.get().getSetting().getCommBuildingCost()) {
                moneyPlaceHolder = moneyPlaceHolder - GameData.get().getSetting().getCommBuildingCost();
                GameData.get().setMoney(moneyPlaceHolder);
                map.setStructure(fs.getSelectedStructure());
            } else {
                Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_SHORT).show();
            }
        }

        public void addRoad() {
            moneyPlaceHolder = GameData.get().getMoney();

            if (GameData.get().getMoney() >= GameData.get().getSetting().getRoadBuildingCost()) {
                moneyPlaceHolder = moneyPlaceHolder - GameData.get().getSetting().getRoadBuildingCost();
                GameData.get().setMoney(moneyPlaceHolder);
                map.setStructure(fs.getSelectedStructure());

            } else {
                Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_SHORT).show();
            }
        }


        public boolean isAdjacent() {
            boolean isAdj = false;
            List<GameElement> grid = GameData.get().getGrid();
            currPos = getAdapterPosition();

            //check whether object placed is within the grid of the map.
            if (currPos <= (GameData.get().getGrid().size())) {
                int height = GameData.get().getSetting().getMapHeight();
                int width = GameData.get().getSetting().getMapWidth();

                //Checks if object is out of bounds, checks if there is a structure next to placed object, check if structure is a road

                //HOUSE BELOW ROAD
                if (calcRow() + 1 < height && grid.get(currPos + 1).getStructure() != null && grid.get(currPos + 1).getStructure().getType().equals(Structure.ROAD)) {
                    isAdj = true;
                }
                //HOUSE ABOVE ROAD
                else if (calcRow() - 1 > 0 && grid.get(currPos - 1).getStructure() != null && grid.get(currPos - 1).getStructure().getType().equals(Structure.ROAD)) {
                    isAdj = true;
                }
                //HOUSE RIGHT OF ROAD
                if (calcCol() - 1 > 0 && grid.get(currPos - height).getStructure() != null && grid.get(currPos - height).getStructure().getType().equals(Structure.ROAD)) {
                    isAdj = true;
                }
                //HOUSE LEFT OF ROAD
                else if (calcCol() + 1 < width && grid.get(currPos + height).getStructure() != null && grid.get(currPos + height).getStructure().getType().equals(Structure.ROAD)) {
                    isAdj = true;
                }
            }
            return isAdj;
        }


        public void enterDetailScreenOnClick() {
            Intent intent = new Intent();

            if (map.getStructure().getType().equals(Structure.RES) || map.getStructure().getType().equals(Structure.COMM) || map.getStructure().getType().equals(Structure.ROAD)) {
                GameData.get().setSelectedElement(map);
                GameData.get().setSelectedStructure(fs.getSelectedStructure());
                GameData.get().setRow(calcRow());
                GameData.get().setCol(calcCol());
                intent.setClass(getActivity(), DetailsMenu.class);
                //start activity and saves result of current map screen
                startActivityForResult(intent, 0000);

            }
        }

        public void demolishBuilding() {
            if (map.getStructure().getType().equals(Structure.RES)) {
                nRes--;
                map.setStructure(null);
                map.setImage(null);
                imageStructure.setImageResource(0);
                mapAdapter.notifyItemChanged(getAdapterPosition());
                //System.out.println(nRes);
            } else if (map.getStructure().getType().equals(Structure.COMM)) {
                nComm--;
                map.setStructure(null);
                map.setImage(null);
                imageStructure.setImageResource(0);
                mapAdapter.notifyItemChanged(getAdapterPosition());
                //System.out.println(nComm);
            } else if (map.getStructure().getType().equals(Structure.ROAD)) {
                map.setStructure(null);
                map.setImage(null);
                imageStructure.setImageResource(0);
                mapAdapter.notifyItemChanged(getAdapterPosition());
            }
        }


        public GameElement getMapPosition() {
            return map;
        }

        public int calcRow() {
            return GameData.get().getGrid().indexOf(getMapPosition()) % GameData.get().getSetting().getMapHeight();
        }

        public int calcCol() {
            return GameData.get().getGrid().indexOf(getMapPosition()) / GameData.get().getSetting().getMapHeight();
        }
    }

    //Updates and refreshes everything in mapScreen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        mapAdapter.notifyDataSetChanged();
    }


    //getters for nComm and nRes to use in fragment Status
    public int getNComm() {
        return nComm;
    }

    public int getNRes() {
        return nRes;
    }
}