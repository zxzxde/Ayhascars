package space.arstar.httpwww.ayhascars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Pattern;

public class CountActivity extends AppCompatActivity {
    private static final String TAG = "CalculateActivity";
    private static final String ADD_CHARACTER = "+";
    private static final String SUB_CHARACTER = "-";
    private static final String MUL_CHARACTER = "*";
    private static final String DIV_CHARACTER = "/";
    private static final String EXT_CHARACTER = "^";
    private static final String POINT_CHARACTER = ".";
    private static final String LEFT_BRACKET_CHARACTER = "(";
    private static final String RIGHT_BRACKET_CHARACTER = ")";
    private static final String EQU_CHARACTER = "#";

    StringBuilder editBuffer = new StringBuilder();
    StringBuilder textBuffer = new StringBuilder();
    Stack<BigDecimal> operandStack = new Stack<BigDecimal>();
    Stack<String> operatorStack = new Stack<String>();
    Button calculateequ;
    Button backButton;
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        calculateequ = (Button) findViewById(R.id.calculateequ);
        editText = (EditText) findViewById(R.id.showresult);
        textView = (TextView) findViewById(R.id.showinputdata);
        backButton = (Button) findViewById(R.id.back);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void init() {
        operatorStack.push("#");
        backButton.setText("<--");
        Log.i(

                TAG, "<---------------Calculate Start--------------->");
    }

    /**
     *

     *

     * handle the number

     *

     *

     @param v
     */

    public void btn0(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("0");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());
    }

    public void btn1(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("1");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());
    }

    public void btn2(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("2");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());
    }

    public void btn3(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("3");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());
    }

    public void btn4(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("4");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());
    }

    public void btn5(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("5");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());


    }

    public void btn6(View v) {
        String textString =editText.getText().toString();
        editBuffer.append("6");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());


    }

    public void btn7(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("7");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());


    }

    public void btn8(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("8");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());


    }

    public void btn9(View v) {
        String textString = editText.getText().toString();
        editBuffer.append("9");
        editText.setText(editBuffer);
        editText.setSelection(editText.getText().length());


    }

    public void PlusMinus(View v) {
        String value = editText.getText().toString();
        try {
            if (Float.valueOf(value) < 0) {
                editBuffer.delete(0, 1);
                editText.setText(editBuffer);
            }

            else {
                if (!value.equals("0")) {
                    editBuffer.insert(0, SUB_CHARACTER);
                    editText.setText(editBuffer);
                }
            }
        }catch (Exception e){
            textView.setText("请输入正确计算格式");
        }
    }

    public void btnpoint(View v) {
        String textString = editText.getText().toString();
        try {
            if (textString.equals("0")) {
                if (editBuffer.toString().equals("0")) {
                    editBuffer.append(POINT_CHARACTER);
                }

                else {
                    editBuffer.append("0.");
                }

                editText.setText(editBuffer);
            }

            else {
                editBuffer.append(POINT_CHARACTER);
                editText.setText(editBuffer);
            }
        }catch (Exception e){
            textView.setText("请输入正确计算格式");
        }

        editText.setSelection(editText.getText().length());
    }

    public void btnpercent(View v) {
        String textString = editText.getText().toString();
        try{
            editBuffer.delete(0, editBuffer.length());
            String percentvalue = String.valueOf(Float.parseFloat(textString) / 100);
            editBuffer.append(percentvalue);
            editText.setText(editBuffer);
        }catch (Exception e){
            textView.setText("请输入正确计算格式");
        }
    }

    /**
     *

     * cancel the last number

     *

     *

     @param v
     */

    public void back(View v) {
        if (editBuffer.length() > 0) {
            editBuffer.delete(editBuffer.length() - 1, editBuffer.length());
            editText.setText(editBuffer);
        }

        else {
            editText.setText("0");
        }

        editText.setSelection(editText.getText().length());
    }

    /**
     *

     *

     * Operate the character

     *

     *

     @param v
     */

    public void LeftBracket(View v) {
        textBuffer.append(LEFT_BRACKET_CHARACTER);
        editText.setText(LEFT_BRACKET_CHARACTER);
        textView.setText(textBuffer);
        if (operatorStack == null) {
            operatorStack.push("#");
        }

        else {
            operatorStack.push(LEFT_BRACKET_CHARACTER);
        }

    }

    public void RightBracket(View v) {
        textBuffer.append(editBuffer);
        textBuffer.append(RIGHT_BRACKET_CHARACTER);
        textView.setText(textBuffer);
    }

    /**
     *

     *

     * operate the number and calculate the result

     *

     *

     @param v
     */

    public void add(View v) {
        String valueString = editText.getText().toString();
        try {

            operandStack.push(new BigDecimal(Double.valueOf(valueString).toString()));
            editBuffer.delete(0, editBuffer.length());
// set the value of input textview
            if (textBuffer.length() != 0) {
                String chString = String.valueOf(textBuffer.charAt(textBuffer.length() - 1));
                if (chString.equals("=")) {
                    textBuffer.delete(0, textBuffer.length());
                    textBuffer.append(valueString);
                    textBuffer.append(ADD_CHARACTER);
                    textView.setText(textBuffer);
                }

                else if (chString.equals(RIGHT_BRACKET_CHARACTER)) {
                    textBuffer.append(ADD_CHARACTER);
// textBuffer.append(valueString);
                    textView.setText(textBuffer);
                }

                else {
                    if (Double.valueOf(valueString) < 0) {
                        valueString = LEFT_BRACKET_CHARACTER + valueString + RIGHT_BRACKET_CHARACTER;
                    }

                    textBuffer.append(valueString);
                    textBuffer.append(ADD_CHARACTER);
                    textView.setText(textBuffer);
                }

            }


            else {
                textBuffer.append(valueString);
                textBuffer.append(ADD_CHARACTER);
                textView.setText(textBuffer);
            }
        }catch (Exception e){
            textView.setText("请输入正确的计算格式");
        }

    }

    public void sub(View v) {
        String valueString = editText.getText().toString();
        try {
            operandStack.push(new BigDecimal(Double.valueOf(valueString).toString()));
            editBuffer.delete(0, editBuffer.length());

            if (textBuffer.length() != 0) {
                String chString = String.valueOf(textBuffer.charAt(textBuffer.length() - 1));
                if (chString.equals("=")) {
                    textBuffer.delete(0, textBuffer.length());
                    textBuffer.append(valueString);
                    textBuffer.append(SUB_CHARACTER);
                    textView.setText(valueString);
                } else if (chString.equals(RIGHT_BRACKET_CHARACTER)) {
                    textBuffer.append(SUB_CHARACTER);
                    textView.setText(textBuffer);
                } else {
                    if (Double.valueOf(valueString) < 0) {
                        valueString = LEFT_BRACKET_CHARACTER + valueString + RIGHT_BRACKET_CHARACTER;
                    }

                    textBuffer.append(valueString);
                    textBuffer.append(SUB_CHARACTER);
                    textView.setText(textBuffer);
                }

            } else {
                textBuffer.append(valueString);
                textBuffer.append(SUB_CHARACTER);
                textView.setText(textBuffer);
            }
        } catch(Exception e){
            textView.setText("请输入正确计算格式");
        }

    }

    public void mul(View v) {
        String valueString = editText.getText().toString();
        try{
            operandStack.push(new BigDecimal(Double.valueOf(valueString).toString()));
            editBuffer.delete(0, editBuffer.length());
            if (textBuffer.length() != 0) {
                String chString = String.valueOf(textBuffer.charAt(textBuffer.length() - 1));
                if (chString.equals("=")) {
                    textBuffer.delete(0, textBuffer.length());
                    textBuffer.append(valueString);
                    textBuffer.append(MUL_CHARACTER);
                    textView.setText(valueString);
                } else if (chString.equals(RIGHT_BRACKET_CHARACTER)) {
                    textBuffer.append(MUL_CHARACTER);
// textBuffer.append(valueString);
                    textView.setText(textBuffer);
                } else {
                    if (Double.valueOf(valueString) < 0) {
                        valueString = LEFT_BRACKET_CHARACTER + valueString + RIGHT_BRACKET_CHARACTER;
                    }

                    textBuffer.append(valueString);
                    textBuffer.append(MUL_CHARACTER);
                    textView.setText(textBuffer);
                }

            }

            else {
                textBuffer.append(valueString);
                textBuffer.append(MUL_CHARACTER);
                textView.setText(textBuffer);
            }
        }catch(Exception e){
            textView.setText("请输入正确格式");
        }

    }

    public void div(View v) {
        String valueString = editText.getText().toString();
        try{
            operandStack.push(new BigDecimal(Double.valueOf(valueString).toString()));
            editBuffer.delete(0, editBuffer.length());
            if (textBuffer.length() != 0) {
                String chString = String.valueOf(textBuffer.charAt(textBuffer.length() - 1));
                if (chString.equals("=")) {
                    textBuffer.delete(0, textBuffer.length());
                    textBuffer.append(valueString);
                    textBuffer.append(DIV_CHARACTER);
                    textView.setText(valueString);
                }

                else if (chString.equals(RIGHT_BRACKET_CHARACTER)) {
                    textBuffer.append(DIV_CHARACTER);
// textBuffer.append(valueString);
                    textView.setText(textBuffer);
                }

                else {
                    if (Double.valueOf(valueString) < 0) {
                        valueString = LEFT_BRACKET_CHARACTER + valueString + RIGHT_BRACKET_CHARACTER;
                    }

                    textBuffer.append(valueString);
                    textBuffer.append(DIV_CHARACTER);
                    textView.setText(textBuffer);
                }

            }

            else {
                textBuffer.append(valueString);
                textBuffer.append(DIV_CHARACTER);
                textView.setText(textBuffer);
            }
        }catch(Exception e){
            textView.setText("请输入正确格式");
        }

    }

    public void equalcharacter(View v) {
        String valueString = editText.getText().toString();
        String teString = textView.getText().toString();
// operandStack.push(new
// BigDecimal(Double.valueOf(valueString).toString()));
        editBuffer.delete(0, editBuffer.length());
        try {

            if (!teString.endsWith("=")) {
                if (!teString.endsWith(RIGHT_BRACKET_CHARACTER)) {
                    if (Double.valueOf(valueString) < 0) {
                        valueString = LEFT_BRACKET_CHARACTER + valueString + RIGHT_BRACKET_CHARACTER;
                    }

                    textBuffer.append(valueString);
// textBuffer.append(EQU_CHARACTER);
                    textView.setText(textBuffer);
                }

                String result = evaluateExpression(textBuffer.toString());
                editText.setText(result);
                textBuffer.append("=");
                textView.setText(textBuffer);
            }

        }catch (Exception e){
            textView.setText("请输入正确的计算格式");
        }
        editText.setSelection(editText.getText().length());
    }

    /**
     *

     * the result of x^y

     *

     *

     @param v
     */

    public void ext(View v) {
        String textString = editText.getText().toString();
        editBuffer.delete(0, editBuffer.length());
        textBuffer.append(textString);
        textBuffer.append(EXT_CHARACTER);
        textView.setText(textBuffer);
    }

    /**
     *

     *

     * calculate the result of 1/x

     *

     *

     @param v
     */

    public void dimx(View v) {
        String textString = editText.getText().toString();
        editBuffer.delete(0, editBuffer.length());
        try {
            if (!textString.equals("0")) {
                String percentvalue = String.valueOf(1 / Float.parseFloat(textString));

                editBuffer.append(percentvalue);
                editText.setText(editBuffer);
            } else {
                editText.setText("输入有误！");
                textView.setText("分母不能为0!");
            }
        }catch (Exception e){
            textView.setText("请输入正确的计算格式");
        }
    }

    /**
     *

     *

     *

     *

     @param v
     */

    public void sqrt(View v) {
        String textString = editText.getText().toString();
        try {
            editBuffer.delete(0, editBuffer.length());
            textString = String.valueOf(Math.sqrt(Double.valueOf(textString)));
            editText.setText(textString);

        }catch (Exception e){
            textView.setText("请输入正确的计算格式");
        }
    }

    public void Clear(View v) {
        textBuffer.delete(0, textBuffer.length());
        editBuffer.delete(0, editBuffer.length());
        editText.setText("0");
        textView.setText(null);
        editText.setSelection(editText.getText().length());
    }

    public static String evaluateExpression(String ex) {
// 在表达式首尾加上字符'#'以方便比较运算符
        StringBuffer exB = new StringBuffer(ex);
        exB.insert(0, '#');
        exB.append('#');
        ex = exB.toString();

        StringBuffer operandBuffer = new StringBuffer(); // 运算数的字符缓冲区
        Stack<BigDecimal> operandStack = new Stack<BigDecimal>();
        Stack<String> operatorStack = new Stack<String>();
        int count = 1; // 从ex的序号为1开始，即‘#’后
        int num = 0; // 调试用
        operatorStack.push("#");
        while (count < ex.length()) {
            String ch = String.valueOf(ex.charAt(count));

            if (Pattern.matches("[0-9\\.]", ch) // 当前字符如果是数字或.就把它放到运算数缓冲区
                    || (ch.equals("-") // "-"看成是负号的条件：在表达式的首位或在”（“之后；
                    && (count == 1 || ex.charAt(count - 1) == '('))) {
                operandBuffer.append(ch);
                ++count;

            }

            else {
// 把运算数放入栈
                if (Pattern.matches("[\\+\\-\\*\\/\\)\\^\\#]", ch) && operandBuffer.length() != 0) {
                    operandStack.push(new BigDecimal(Double.valueOf(operandBuffer.toString()).toString()));
                    operandBuffer.delete(0, operandBuffer.length());

                }

// 比较运算符，并根据它进行计算
                switch (compareOperator(operatorStack.peek(), ch)) {
// ch优先级高，将ch压入运算符栈
                    case '<':
                        operatorStack.push(ch);

                        ++count;

                        break;
// 优先级相等时，去掉（）或前后的#；
                    case '=':
                        operatorStack.pop();

                        ++count;

                        break;
// ch优先级低，从运算数栈取出两个数，从运算符栈取出运算符，进行计算其结果放入运算数栈；
                    case '>':
                        BigDecimal b = operandStack.pop();

                        BigDecimal a = operandStack.pop();

                        String curOperator = operatorStack.pop();

                        try {
                            operandStack.push(operate(a, curOperator, b));

                        }

                        catch (ArithmeticException e) {
                            return "除数不能为0！";
                        }

                        break;
// 运算符输入错误的处理：终止计算，在屏幕显示input error!
                    default:
                        return "输入有误！";
                }

            }

        }

// End 0f while
        for (BigDecimal e : operandStack)
            System.out.println(e.toString());
        return operandStack.peek().toString();
    }

    /**
     *

     * 比较前后运算符的优先级

     *

     * */

    public static char compareOperator(String operator1, String operator2) {
        char result = 0; // 局部内部类的实例方法的局部变量能自动初始化为'/u0000'吗？不得行！！！
        char o1 = operator1.charAt(0);
        char o2 = operator2.charAt(0);
        switch (o1) {
            case '+':
                switch (o2) {
                    case '+':
                        result = '>';
                        break;
                    case '-':
                        result = '>';
                        break;
                    case '*':
                        result = '<';
                        break;
                    case '/':
                        result = '<';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '>';
                        break;
                    case '^':
                        result = '<';
                        break;
                    case '#':
                        result = '>';
                        break;
                }

                break; // 跳出case '+';
            case '-':
                switch (o2) {
                    case '+':
                        result = '>';
                        break;
                    case '-':
                        result = '>';
                        break;
                    case '*':
                        result = '<';
                        break;
                    case '/':
                        result = '<';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '>';
                        break;
                    case '^':
                        result = '<';
                        break;
                    case '#':
                        result = '>';
                        break;
                }

                break; // 跳出case '-';
            case '*':
                switch (o2) {
                    case '+':
                        result = '>';
                        break;
                    case '-':
                        result = '>';
                        break;
                    case '*':
                        result = '>';
                        break;
                    case '/':
                        result = '>';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '>';
                        break;
                    case '^':
                        result = '<';
                        break;
                    case '#':
                        result = '>';
                        break;
                }

                break; // 跳出case '*';
            case '/':
                switch (o2) {
                    case '+':
                        result = '>';
                        break;
                    case '-':
                        result = '>';
                        break;
                    case '*':
                        result = '>';
                        break;
                    case '/':
                        result = '>';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '>';
                        break;
                    case '^':
                        result = '<';
                        break;
                    case '#':
                        result = '>';
                        break;
                }

                break; // 跳出case '/';
            case '^':
                switch (o2) {
                    case '+':
                        result = '>';
                        break;
                    case '-':
                        result = '>';
                        break;
                    case '*':
                        result = '>';
                        break;
                    case '/':
                        result = '>';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '>';
                        break;
                    case '^':
                        result = '>';
                        break;
                    case '#':
                        result = '>';
                        break;
                }

                break; // 跳出case '/';
            case '(':
                switch (o2) {
                    case '+':
                        result = '<';
                        break;
                    case '-':
                        result = '<';
                        break;
                    case '*':
                        result = '<';
                        break;
                    case '/':
                        result = '<';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '=';
                        break;
                    case '^':
                        result = '<';
                        break;
                    case '#':
                        result = '?'; // （后不能是#，如果是则是错误输入；
                        break;
                }

                break; // 跳出case '(';
            case ')':
                switch (o2) {
                    case '+':
                        result = '>';
                        break;
                    case '-':
                        result = '>';
                        break;
                    case '*':
                        result = '>';
                        break;
                    case '/':
                        result = '>';
                        break;
                    case '(':
                        result = '?'; // )后不能接（；
                        break;
                    case ')':
                        result = '>';
                        break;
                    case '^':
                        result = '>';
                        break;
                    case '#':
                        result = '>';
                        break;
                }

                break; // 跳出case ')';
            case '#':
                switch (o2) {
                    case '+':
                        result = '<';
                        break;
                    case '-':
                        result = '<';
                        break;
                    case '*':
                        result = '<';
                        break;
                    case '/':
                        result = '<';
                        break;
                    case '(':
                        result = '<';
                        break;
                    case ')':
                        result = '?'; // #后不能接）；
                        break;
                    case '^':
                        result = '<';
                        break;
                    case '#':
                        result = '=';
                        break;
                }

                break; // 跳出case '#';
        }

// End Of switch
        return result;
    }

    /**
     * 根据运算符进行二元计算，

     *

     *

     @param //BigDecimal类型
      * a ? b

     *

     @return： BigDecimal类型的结果
     */

    public static BigDecimal operate(BigDecimal a, String operator, BigDecimal b) {
        final int DEF_DIV_SCALE = 20;
        BigDecimal result =

                null;
        switch (operator.charAt(0)) {
            case '+':
                result = a.add(b);

                break;
            case '-':
                result = a.subtract(b);

                break;
            case '*':
                result = a.multiply(b);

                break;
            case '/':
                result = a.divide(b, DEF_DIV_SCALE, BigDecimal.

                        ROUND_HALF_UP);
                break;
            case '^':
                double result1 = Math.pow(a.doubleValue(), b.doubleValue());
                result = BigDecimal.valueOf(result1);

                break;
        }

        return result;
    }

}