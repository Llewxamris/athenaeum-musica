package ca.qc.cegep_heritage.athenaeummusica;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(PreferenceCodes.SETTINGS,
                Context.MODE_PRIVATE);
        setTheme(sharedPreferences.getInt(PreferenceCodes.THEME, R.style.AppTheme));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Switch swTheme = findViewById(R.id.swTheme);

        if(sharedPreferences.getInt(PreferenceCodes.THEME, 0) == R.style.DarkAppTheme) {
            swTheme.setChecked(true);
        }

        swTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(isChecked) {
                    editor.putInt(PreferenceCodes.THEME, R.style.DarkAppTheme);
                } else {
                    editor.putInt(PreferenceCodes.THEME, R.style.AppTheme);
                }

                Snackbar.make(findViewById(R.id.settingsView), "Please restart for theme changes to take effect.", Snackbar.LENGTH_LONG).show();

                editor.apply();
            }
        });

        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.this.deleteDatabase("athenaeum_musica.db");
            }
        });
    }
}
