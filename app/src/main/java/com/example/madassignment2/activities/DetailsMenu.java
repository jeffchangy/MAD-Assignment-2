package com.example.madassignment2.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment2.R;
import com.example.madassignment2.fragment.FragmentMap;
import com.example.madassignment2.fragment.FragmentSelect;
import com.example.madassignment2.object.GameData;
import com.example.madassignment2.object.GameElement;
import com.example.madassignment2.object.Setting;

public class DetailsMenu extends AppCompatActivity {

    private static final int REQUEST_THUMBNAIL = 1;
    private Intent thumbnailPhotoIntent;

    private Bitmap thumbnail;
    private TextView row, column, structureType;
    private EditText editableName;
    private Button takePhotoBtn, applyBtn, backBtn;
    private ImageView photoView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);

        row = (TextView) findViewById(R.id.coordinate_row);
        column = (TextView) findViewById(R.id.coordinate_column);
        structureType = (TextView) findViewById(R.id.structure_type);
        editableName = (EditText) findViewById(R.id.editable_name);
        takePhotoBtn = (Button) findViewById(R.id.photo_button);
        applyBtn = (Button) findViewById(R.id.apply_button);
        photoView = (ImageView) findViewById(R.id.show_image);
        //backBtn = (Button) findViewById(R.id.back_button);


        //display all specifications
        row.setText("Row: " + GameData.get().getRow());
        column.setText("Column: " + GameData.get().getCol());
        structureType.setText("Structure: " + GameData.get().getSelectedElement().getStructure().getType());
        if (GameData.get().getSelectedElement().getOwnerName() != null)
            editableName.setText(GameData.get().getSelectedElement().getOwnerName());

        if (GameData.get().getSelectedElement().getImage() != null)
            photoView.setImageBitmap(GameData.get().getSelectedElement().getImage());

        //allow app the access the camera function on the phone
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbnailPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(thumbnailPhotoIntent, REQUEST_THUMBNAIL);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

        //set building/road image to picture taken on click
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.get().getSelectedElement().setOwnerName(editableName.getText().toString());
                Intent intent = new Intent(DetailsMenu.this, StartActivity.class);
                startActivity(intent);
                //when finish is called, goes to onActivityResult in FragmentMap.
            }
        });

/*        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.get().getSelectedElement().setOwnerName(editableName.getText().toString());
                Intent intent = new Intent(DetailsMenu.this, StartActivity.class);
                startActivity(intent);
            }
        });*/
    }

    //onSave of the photo in the camera, will call this method to change thumbnail on mapFragment.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_THUMBNAIL) {
            thumbnail = (Bitmap) result.getExtras().get("data");
            photoView.setImageBitmap(thumbnail);
            GameData.get().getSelectedElement().setImage(thumbnail);
        }
    }
}
