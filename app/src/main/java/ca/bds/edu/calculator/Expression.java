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
        public Operator(int precedenceValue, boolean assoc, String operatorCharacter)
        {
            precedence = precedenceValue;
            isLeftAssociated = assoc;
            character = operatorCharacter;
        }
    }

    private class stackToken
    {
        public boolean isOperator;
        public Operator operator;
        public int number;
        public String numberString;

        public stackToken(boolean state)
        {
            isOperator = state;
            if (!isOperator)
            {
                number = 0;
            }
        }

        public stackToken(Operator o)
        {
            operator = o;
            isOperator = true;
        }

        public stackToken(String digits)
        {
            isOperator = false;
            number = Integer.parseInt(digits);
        }

        public void expandNumber(String digit)
        {
            if (number == 0)
            {
                numberString = "";
            } else
            {
                numberString = String.valueOf(number);
            }
            Log.d("Number: ", numberString);
            Log.d("Adding: ", digit);
            numberString += digit;
            Log.d("Result: ", numberString);
            number = Integer.parseInt(numberString);
            numberString = "";
        }
    }

    private String expression;
    private String result;
    private Map<String, Operator> Operators;
    private ArrayDeque<Operator> operatorStack;
    private ArrayDeque<stackToken> outputQueue;

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

    public String getResult() { return result; }
    
    public void Evaluate()
    {
        for (char token: expression.toCharArray())
        {
            if (isOperator(token))
            {
                if (!outputQueue.isEmpty())
                {
                    stackToken number = new stackToken(false);
                    stackToken first = outputQueue.pop();
                    if (!first.isOperator && !outputQueue.isEmpty())
                    {
                        stackToken tmp = outputQueue.pop();
                        if (outputQueue.isEmpty())
                        {
                            first.expandNumber(String.valueOf(tmp.number));
                        }
                        while ((!outputQueue.isEmpty()) && (!tmp.isOperator))
                        {
                            first.expandNumber(String.valueOf(tmp.number));
                            tmp = outputQueue.pop();
                        }
                    }
                    number.expandNumber(new StringBuilder(String.valueOf(first.number)).reverse().toString());
                    outputQueue.addFirst(number);
                }
                if (!operatorStack.isEmpty())
                {
                    for (Operator entry: operatorStack)
                    {
                        if ((entry.precedence > getOperator(token).precedence) || ((entry.precedence == getOperator(token).precedence) && (entry.isLeftAssociated)))
                        {
                            Operator operator = operatorStack.pop();
                            String removed = operator.character;
                            outputQueue.addFirst(new stackToken(operator));
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
                outputQueue.addFirst(new stackToken(String.valueOf(token)));
            }
        }
        while (!operatorStack.isEmpty())
        {
            Operator operator = operatorStack.removeLast();
            String removed = operator.character;
            outputQueue.addFirst(new stackToken(operator));
            Log.d("Moved: ", removed + " from operator stack to output queue");
        }
        result = "";
        while (!outputQueue.isEmpty())
        {
            result += "_";
            stackToken nextToken = outputQueue.pop();
            if (nextToken.isOperator)
            {
                result += nextToken.operator.character;
            } else
            {
                result += String.valueOf(nextToken.number);
            }
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
