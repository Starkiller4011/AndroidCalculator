package ca.bds.edu.calculator;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Expression
{
    private class Operator
    {
        public int precedence;
        public boolean isLeftAssociated;
        public String character;
        public Operator(int prec, boolean assoc, String operatorCharacter)
        {
            precedence = prec;
            isLeftAssociated = assoc;
            character = operatorCharacter;
        }
    }

    private String expression;
    private String result;
    private Map<String, Operator> Operators;
    private ArrayDeque<Operator> operatorStack;
    private ArrayDeque<String> outputQueue;

    public Expression()
    {
        Operators = new HashMap<>();
        {
            Operators.put("+", new Operator(0, true, "+"));
            Operators.put("-", new Operator(0, true, "-"));
            Operators.put("*", new Operator(5, true, "*"));
            Operators.put("/", new Operator(5, true, "/"));
        }
        operatorStack = new ArrayDeque<>();
        outputQueue = new ArrayDeque<>();
    }

    public void setExpression(String newExpression)
    {
        expression = newExpression;
        Log.d("Expression:", expression);
    }

    public void Reset()
    {
        expression = "";
        result = "";
        outputQueue.clear();
        operatorStack.clear();
    }

    public void DeleteLast()
    {
        if (expression.length() > 0)
        {
            expression = expression.substring(0, expression.length() - 1);
        }
    }

    public String getResult()
    {
        return result;
    }
    
    public void Evaluate()
    {
        for (char token: expression.toCharArray())
        {
            if (isOperator(token))
            {
                outputQueue.addFirst(" ");
                if (!operatorStack.isEmpty())
                {
                    for (Operator entry: operatorStack)
                    {
                        if ((entry.precedence > getOperator(token).precedence) || ((entry.precedence == getOperator(token).precedence) && (entry.isLeftAssociated)))
                        {
                            String removed = operatorStack.pop().character;
                            outputQueue.addFirst(removed);
                            Log.d("Moved: ", removed + " from operator stack to output queue");
                        } else
                        {
                            break;
                        }
                    }
                }
                operatorStack.push(getOperator(token));
            } else
            {
                outputQueue.addFirst(String.valueOf(token));
            }
        }
        while (!operatorStack.isEmpty())
        {
            String removed = operatorStack.removeLast().character;
            outputQueue.addFirst(removed);
            Log.d("Moved: ", removed + " from operator stack to output queue");
        }
        result = "";
        while (!outputQueue.isEmpty())
        {
            result += outputQueue.removeLast();
        }
    }

    private Operator getOperator(char token)
    {
        return Operators.get(String.valueOf(token));
    }

    private boolean isOperator(char token)
    {
        Iterator iterator = Operators.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            if (token == entry.getKey().toString().charAt(0))
            {
                return true;
            }
        }
        return false;
    }
}
