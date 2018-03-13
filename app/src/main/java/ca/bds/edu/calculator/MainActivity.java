package ca.bds.edu.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private View ButtonZone;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            ViewGroup ButtonZoneContainer = findViewById(R.id.layout_button_zone);

            switch (item.getItemId()) {
                case R.id.navigation_simple:
                    mTextMessage.setText(R.string.title_simple);
                    ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.simple_calc);
                    return true;
                case R.id.navigation_scientific:
                    mTextMessage.setText(R.string.title_scientific);
                    ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.scientific_calc);
                    return true;
                case R.id.navigation_graphing:
                    mTextMessage.setText(R.string.title_graphing);
                    ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.graphing_calc);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup ButtonZoneContainer = findViewById(R.id.layout_button_zone);

        ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.simple_calc);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void ChangeTargetLayout(ViewGroup TargetViewSpace, View TargetView, int TargetId) {
        if (TargetView != null) {
            TargetViewSpace.removeView(TargetView);
        }
        TargetView = getLayoutInflater().inflate(TargetId, null);
        TargetViewSpace.addView(TargetView);
    }

}
