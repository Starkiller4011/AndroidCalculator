package ca.bds.edu.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button TypeStates[];// = new Button[3];
    private int StateIds[];// = new int[3];
    private View LayoutContent;
    private ViewGroup LayoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize member variables
        TypeStates = new Button[]{findViewById(R.id.button_type_simple), findViewById(R.id.button_type_scientific), findViewById(R.id.button_type_graphing)};
        StateIds = new int[]{R.layout.simple_calc, R.layout.scientific_calc, R.layout.graphing_calc};
        LayoutRoot = findViewById(R.id.layout_button_zone);

        // Set initial state to simple calculator
        ChangeTypeState(0);

        // Top nav bar button listeners
        for (int i = 0; i < 3; i++) {
            final int test = i;
            TypeStates[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeTypeState(test);
                }
            });
        }
    }

    private void ChangeTypeState(int State) {
        // Reset layout content
        if (LayoutContent != null) {
            LayoutRoot.removeView(LayoutContent);
        }
        // Get new layout content
        LayoutContent = getLayoutInflater().inflate(StateIds[State], null);
        // Set new layout content
        LayoutRoot.addView(LayoutContent);
        // Change selected nav-bar entry
        for (int i = 0; i < 3; i++) {
            if (i == State) {
                TypeStates[i].setTextColor(getResources().getColor(R.color.colorAccentLight));
            } else {
                TypeStates[i].setTextColor(getResources().getColor(R.color.colorAccentDark));
            }
        }
    }
}
