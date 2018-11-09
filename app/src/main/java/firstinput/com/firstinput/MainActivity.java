package firstinput.com.firstinput;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int numberOfCoffee = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *This method will be called when the botton will be clicked
     */
    public void submitOrder(View view){
        CheckBox toppCream = (CheckBox) findViewById(R.id.whipp);
        boolean hasWhipp = toppCream.isChecked();
        CheckBox toppChoco = (CheckBox) findViewById(R.id.toppChoco);
        boolean hasChoco = toppChoco.isChecked();
        int price = calculatePrice(hasWhipp,hasChoco);
        String youPay = orderSummary(price,hasWhipp,hasChoco);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "You have recieved an order from " + nameField());
        intent.putExtra(Intent.EXTRA_EMAIL, "akshay_rs5@yahoo.co.in");
        intent.putExtra(Intent.EXTRA_TEXT,youPay );
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void incre(View view){
        if(numberOfCoffee >= 100){
            return;
        }
        int increment = (++numberOfCoffee);
        numberOfCoffee = increment;
        display(numberOfCoffee);

    }
    public void decre(View view){
        if(numberOfCoffee == 1){
            Context context = getApplicationContext();
            CharSequence text = "Too LOW!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        int decrement = (--numberOfCoffee);
        numberOfCoffee = decrement;
        display(numberOfCoffee);

    }
    /**
     * This method will display the given quantity value on the screen
     */
    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.unity);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(boolean addCream, boolean addChoco){
        int basePrice = 5;
        if(addChoco){
            if(addCream){
                basePrice = basePrice + 30;
            }
            else{
                basePrice = basePrice + 20;
            }
        }
        else if(addCream){
            basePrice = basePrice + 10;
        }
        else{
            basePrice = 5;
        }
        basePrice = basePrice * numberOfCoffee;
        return (basePrice);
    }
    private String nameField(){
        EditText Customer = (EditText) findViewById(R.id.name);
        String naming = String.valueOf(Customer.getText());
        return (naming);
    }



    private String orderSummary(int price, boolean addWhipp, boolean addChoco){
        String message = "Name : " + nameField();
       // Log.v("orderSummary" , "value in the nameField()" + message);
        message = message + "\nQuantity " + numberOfCoffee;
        message = message + "\ntotal $" + price;
        message = message + getResources().getString(R.string.thank_You);
        message = message + "\nHas Whipped Cream ";
        message = message + addWhipp;
        message = message + "\nHas Chocolate Topping ";
        message = message + addChoco;
          return message;
    }

}


