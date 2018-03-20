package har.team.mapenv;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SubmitActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final int requestCode = 20;

    ImageView imageView;
    Button upload;
    public Uri pictureUri;
    ProgressDialog mProgress;
    FirebaseStorage storage;
    private static final String TAG = "SubmitActivity";
    private Context mContext;

    public TextView latitude;
    public TextView longitude;
    FirebaseAuth mAuth;
    //a list to store all the artist from firebase database
    List<Map> maps;

    //our database reference object
    DatabaseReference databaseMap;

    private StorageReference mstorage;
    private RadioGroup types;
    private RadioButton type,type1,type2;
    private Button btnDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        mContext = getApplicationContext();
        // Get the activity

        databaseMap = FirebaseDatabase.getInstance().getReference("wards");
        mAuth = FirebaseAuth.getInstance();
        maps = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        initializeViews();
      //  addListenerOnButton();
        Bundle extras = getIntent().getExtras();
        upload = (Button) findViewById(R.id.upload);
        mProgress = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();

        //ActivityCompat.requestPermissions(SubmitActivity.this,new String[]{Manifest.permission.WRITE},1);
        ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
   /* public void addListenerOnButton() {

        types = (RadioGroup) findViewById(R.id.types);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = types.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                type = (RadioButton) findViewById(selectedId);
                type1 = (RadioButton) findViewById(selectedId);
                type2 = (RadioButton) findViewById(selectedId);


            }

        });*/
        mstorage = storage.getReference();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upload();

            }
        });

        double latitude_id = extras.getDouble("LATITUDE_ID");
        String latitudeString = Double.toString(latitude_id);
        latitude.setText("Latitude : " + latitudeString);
        double longitude_id = extras.getDouble("LONGITUDE_ID");
        String longitudeString = Double.toString(longitude_id);
        longitude.setText("Longitude : " + longitudeString);

        //The key argument here must match that used in the other activity

        imageView = (ImageView) this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
       // StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());
      //  File path = Environment.getExternalStorageDirectory();
        //File dir = new File(path+"/save/");
        //dir.mkdirs();

        /*File file= new File(dir,"env");
        OutputStream out=null;
        try {
            out=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);

                //   Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
  /*             File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                String pictureName = getPictureName();
                File imageFile = new File(pictureDirectory,pictureName);
                Uri pictureUri = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);

    */         //   startActivityForResult(cameraIntent, requestcode);
            }
        });
upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        addMap();
    }
});
    }

   /* public void upload(){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                    + "/storage/emulated/0/DCIM/");
*/
  /*private String getPictureName() {
        SimpleDateFormat sdf= new SimpleDateFormat("HHmmss_ddMMyyyy");
        String timeStamp = sdf.format(new Date());

        return "Env"+timeStamp+".jpg";

    }*/
   private void addMap() {
       //getting the values to save
       String lat = latitude.getText().toString().trim();
       String lon = longitude.getText().toString().trim();

       // String wardno = editTextWardNo.getText().toString();
       //String incharge = editTextIncharge.getText().toString();

       //checking if the value is provided

       //getting a unique id using push().getKey() method
       //it will create a unique id and we will use it as the Primary Key for our Artist
       String id = databaseMap.push().getKey();

       //creating an Artist Object
       Map map = new Map(id, lat,lon);

       //Saving the Artist
       databaseMap.child(id).setValue(map);

       //setting edittext to blank again

       //displaying a success toast
       Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
       Intent j = new Intent(SubmitActivity.this,DisplayActivity.class);
       startActivity(j);
       finish();

       //if the value is not given displaying a toast
       Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
   }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     /*   if(resultCode != RESULT_CANCELED){
            if (requestCode == CAMERA_REQUEST) {
       */         super.onActivityResult(requestCode, resultCode, data);
                if(this.requestCode == requestCode && resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");

                 /*   String partFilename = currentDateFormat();
                    storeCameraPhotoInSDCard(bitmap, partFilename);

                    // display the image from SD Card to ImageView Control
                    String storeFilename = "photo_" + partFilename + ".jpg";
                    Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                  */  imageView.setImageBitmap(bitmap);
      /*  Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
        */    }
        }
 /*   private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
*/
    private void initializeViews() {
        latitude = (TextView) findViewById(R.id.latitide);
        longitude = (TextView) findViewById(R.id.longitude);
    }


}


