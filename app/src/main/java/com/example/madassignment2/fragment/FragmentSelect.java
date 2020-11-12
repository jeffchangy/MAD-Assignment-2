package com.example.madassignment2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madassignment2.R;
import com.example.madassignment2.object.Structure;
import com.example.madassignment2.object.StructureData;

import java.util.List;

public class FragmentSelect extends Fragment {

    private Structure selectedStructure;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.map_recycler_view, ui, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.MapRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        List<Structure> data = StructureData.get().getStructureList();
        StructureAdapter structureAdapter = new StructureAdapter(data);
        recyclerView.setAdapter(structureAdapter);
        return view;
    }

    public Structure getSelectedStructure() {
        return selectedStructure;
    }

    public void setSelectedStructure(Structure structure) {
        selectedStructure = structure;
    }


    private class StructureAdapter extends RecyclerView.Adapter<StructureHolder> {
        private List<Structure> data;

        public StructureAdapter(List<Structure> data) {
            this.data = data;
        }
        @Override
        public StructureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new StructureHolder(li,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StructureHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }
    }

    private class StructureHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Structure structure;

        public StructureHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.select_holder, parent, false));
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedStructure = structure;
                }
            });
        }

        public void bind(Structure structure) {
            this.structure = structure;
            textView.setText(structure.getLabel());
            imageView.setImageResource(structure.getDrawableId());
        }
    }
}