package ca.bds.edu.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
{
    // Layout Control
    private TextView EntryZone;
    private TextView ResultZone;
    private Button TypeStates[];
    private int CurrentState;
    private int StateIds[];
    private View LayoutContent;
    private ViewGroup LayoutRoot;

    // Simple Calculator Internals
    private Button SimpleButtons[];
    private String SimpleStrings[];
    private Button SimpleOperationButtons[];
    private String SimpleOperationStrings[];
    private String expression = "";
    private Expression calcExpression = new Expression();
    private double valueOne = Double.NaN;
    private double valueTwo;
    private double evalOne, evalTwo, subEvalOne, subEvalTwo, getSubEvalThree, getSubEvalFour;
    DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize member variables
        TypeStates = new Button[]{findViewById(R.id.button_type_simple),
                findViewById(R.id.button_type_scientific),
                findViewById(R.id.button_type_graphing)};
        LayoutRoot = findViewById(R.id.layout_button_zone);
        StateIds = new int[]{R.layout.simple_calc,
                R.layout.scientific_calc,
                R.layout.graphing_calc};

        // Set initial state to simple calculator
        ChangeTypeState(0);

        // Top nav bar button listeners
        for (int index = 0; index < TypeStates.length; index++)
        {
            final int ButtonIndex = index;
            TypeStates[index].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ChangeTypeState(ButtonIndex);
                }
            });
        }
    }

    private void ChangeTypeState(int State)
    {
        CurrentState = State;
        ChangeLayout();
        InitiateButtons();
        ChangeStateIdentifier();
    }

    private void ChangeStateIdentifier()
    {
        // Change selected nav-bar entry
        for (int i = 0; i < 3; i++)
        {
            if (i == CurrentState)
            {
                TypeStates[i].setTextColor(getResources().getColor(R.color.colorAccentLight));
            } else
            {
                TypeStates[i].setTextColor(getResources().getColor(R.color.colorAccentDark));
            }
        }
    }

    private void ChangeLayout()
    {
        // Reset expression text
        expression = "";
        // Reset layout content
        if (LayoutContent != null)
        {
            LayoutRoot.removeView(LayoutContent);
        }
        // Get new layout content
        LayoutContent = getLayoutInflater().inflate(StateIds[CurrentState], null);
        // Set new layout content
        LayoutRoot.addView(LayoutContent);
    }

    private void InitiateButtons()
    {
        if (SimpleButtons != null)
        {
            ResetSimpleButtons();
        }
        if (SimpleOperationButtons != null)
        {
            ResetSimpleOperationButtons();
        }
        switch (CurrentState)
        {
            case 0:
                InitiateSimpleButtons();
                InitiateSimpleOperationButtons();
                findViewById(R.id.simple_button_del).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        DeleteLast();
                    }
                });
                findViewById(R.id.simple_button_del).setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        ClearText();
                        return false;
                    }
                });
                EntryZone = findViewById(R.id.text_entry);
                ResultZone = findViewById(R.id.text_result);
                break;
            case 1:
                InitiateSimpleButtons();
                InitiateSimpleOperationButtons();
                findViewById(R.id.simple_button_del).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        DeleteLast();
                    }
                });
                findViewById(R.id.scientific_button_clr).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ClearText();
                    }
                });
                findViewById(R.id.scientific_button_ac).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        ClearText();
                    }
                });
                EntryZone = findViewById(R.id.text_entry);
                ResultZone = findViewById(R.id.text_result);
                break;
            case 2:
                break;
        }
    }

    private void EditText(String Operation)
    {
        expression += Operation;
        calcExpression.setExpression(expression);
        calcExpression.Evaluate();
        EntryZone.setText(expression);
        ResultZone.setText(calcExpression.getResult());
    }

    private void ClearText()
    {
        expression = "";
        calcExpression.Reset();
        ResultZone.setText(calcExpression.getResult());
        EntryZone.setText(expression);
    }

    private void DeleteLast()
    {
        if (expression.length() > 0)
        {
            expression = expression.substring(0, expression.length() - 1);
        }
        calcExpression.DeleteLast();
        calcExpression.Evaluate();
        ResultZone.setText(calcExpression.getResult());
        EntryZone.setText(expression);
    }

    private void ResetSimpleButtons()
    {
        for (int index = 0; index < SimpleButtons.length; index++)
        {
            SimpleButtons[index].setClickable(false);
        }
    }

    private void ResetSimpleOperationButtons()
    {
        for (int index = 0; index < SimpleOperationButtons.length; index++)
        {
            SimpleOperationButtons[index].setClickable(false);
        }
    }

    private void InitiateSimpleButtons()
    {
        SimpleButtons = new Button[]{findViewById(R.id.simple_button_0), findViewById(R.id.simple_button_1),
                findViewById(R.id.simple_button_2), findViewById(R.id.simple_button_3),
                findViewById(R.id.simple_button_4), findViewById(R.id.simple_button_5),
                findViewById(R.id.simple_button_6), findViewById(R.id.simple_button_7),
                findViewById(R.id.simple_button_8), findViewById(R.id.simple_button_9)};
        SimpleStrings = new String[]{getResources().getString(R.string.button_simple_0), getResources().getString(R.string.button_simple_1), getResources().getString(R.string.button_simple_2), getResources().getString(R.string.button_simple_3), getResources().getString(R.string.button_simple_4), getResources().getString(R.string.button_simple_5), getResources().getString(R.string.button_simple_6), getResources().getString(R.string.button_simple_7), getResources().getString(R.string.button_simple_8), getResources().getString(R.string.button_simple_9)};
        for (int index = 0; index < SimpleButtons.length; index++)
        {
            final int ButtonIndex = index;
            SimpleButtons[index].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    EditText(SimpleStrings[ButtonIndex]);
                }
            });
        }
    }

    private void InitiateSimpleOperationButtons()
    {
        SimpleOperationButtons = new Button[]{findViewById(R.id.simple_button_add),
                findViewById(R.id.simple_button_subtract),
                findViewById(R.id.simple_button_divide),
                findViewById(R.id.simple_button_multiply)};
        SimpleOperationStrings = new String[]{getResources().getString(R.string.button_simple_add), getResources().getString(R.string.button_simple_subtract), getResources().getString(R.string.button_simple_divide), getResources().getString(R.string.button_simple_multiply)};
        for (int index = 0; index < SimpleOperationButtons.length; index++) {
            final int ButtonIndex = index;
            SimpleOperationButtons[index].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    EditText(SimpleOperationStrings[ButtonIndex]);
                }
            });
        }
    }
}
