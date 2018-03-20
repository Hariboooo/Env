package har.team.mapenv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity  {
    private Context mContext;
    private Activity mActivity;
    public TextView tv;
    private RelativeLayout mRelativeLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;
    private static final String TAG = "MapsActivity";

    public EditText editTextNo;
    public EditText editTextWardNo;
    public EditText editTextIncharge;
    private Button scan_btn,upload_btn;
    FirebaseAuth mAuth;
    //a list to store all the artist from firebase database
    List<Map> wards;

    //our database reference object
    DatabaseReference databaseWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mContext = getApplicationContext();
        // Get the activity

        databaseWard = FirebaseDatabase.getInstance().getReference("wards");
        mAuth = FirebaseAuth.getInstance();
        wards = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        //getting views
        editTextNo = (EditText) findViewById(R.id.editTextNo);
        //editTextWardNo = (EditText) findViewById(R.id.editTextWardNo);
        //editTextIncharge = (EditText) findViewById(R.id.editTextIncharge);


        editTextNo.setEnabled(false);
        //editTextWardNo.setEnabled(false);
        //editTextIncharge.setEnabled(false);
        editTextNo.setClickable(false);
        //editTextWardNo.setClickable(false);
        //editTextIncharge.setClickable(false);
        editTextNo.setClickable(false);
        //editTextWardNo.setCursorVisible(false);
        //editTextIncharge.setCursorVisible(false);

        scan_btn = (Button) findViewById(R.id.scan_btn);
        upload_btn = (Button) findViewById(R.id.upload_btn);
        scan_btn.requestFocus();
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWard();
            }
        });
    }


    private void addWard() {
        //getting the values to save
        String no = editTextNo.getText().toString().trim();
       // String wardno = editTextWardNo.getText().toString();
        //String incharge = editTextIncharge.getText().toString();

        //checking if the value is provided

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseWard.push().getKey();

            //creating an Artist Object
            Ward ward = new Ward(id, no);

            //Saving the Artist
            databaseWard.child(id).setValue(ward);

            //setting edittext to blank again

            //displaying a success toast
            Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
            Intent j = new Intent(ScanActivity.this,DisplayActivity.class);
            startActivity(j);
            finish();

            //if the value is not given displaying a toast
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {

                EditText editText = (EditText)findViewById(R.id.editTextNo);
                editText.clearFocus();
                editText.setEnabled(false);
                editText.setClickable(false);
                editText.setCursorVisible(false);
                editText.setText(result.getContents(), TextView.BufferType.EDITABLE);}

            // Get a reference for the custom view close button

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
