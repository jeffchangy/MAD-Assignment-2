package com.example.madassignment2.activities;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.example.madassignment2.R;
        import com.example.madassignment2.object.GameData;
        import com.example.madassignment2.object.Setting;

public class SettingsMenu extends AppCompatActivity {

    private EditText editWidth, editHeight, editMoney, editCityName;
    private Button exitBtn;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.setting_menu);

        editWidth = (EditText) findViewById(R.id.width_edit);
        editHeight = (EditText) findViewById(R.id.height_edit);
        editMoney = (EditText) findViewById(R.id.money_edit);
        editCityName = (EditText) findViewById(R.id.city_name_edit);

        exitBtn = (Button) findViewById(R.id.setting_exit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(editCityName);
                Intent intent = new Intent(SettingsMenu.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void saveSettings(EditText editCityName) {

        try
        {
            int intWidth = Integer.parseInt(editWidth.getText().toString());
            int intHeight = Integer.parseInt(editHeight.getText().toString());
            int intMoney = Integer.parseInt(editMoney.getText().toString());

            GameData.get().getSetting().setMapWidth(intWidth);
            GameData.get().getSetting().setMapHeight(intHeight);
            GameData.get().setMoney(intMoney);


            //increment the id by +1
            //GameData.get().getSetting().setId();

            //add to database
            Setting set = new Setting(GameData.get().getSetting().getMapWidth(),
                    GameData.get().getSetting().getMapHeight(), GameData.get().getSetting().getInitialMoney());

            //Setting set = new Setting(intWidth, intHeight, intMoney);

            //load setting from database
            GameData.get().addSetting(set);
        }
        //catches input that is not an integer
        catch (NumberFormatException e) {
            int val = 0;
            Toast.makeText(SettingsMenu.this, "Invalid input, using default settings.", Toast.LENGTH_LONG).show();
        }

        //change name
        String cityName = editCityName.getText().toString();
        GameData.get().getSetting().setCityName(cityName);

    }
}
