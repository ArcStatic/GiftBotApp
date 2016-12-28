package bytebeast.giftbot;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class EnterInfo extends AppCompatActivity{


    //TODO: Pass custom image information
    public String friendName;
    private static int RESULT_LOAD_IMG = 1;
    public String imgDecodableString = "";
    private final int RESULT_CROP = 400;
    public String filename;
    public static Integer fileCreateID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);

        //Retrieve contact name passed over from contact_list activity
        try {
            Bundle extras = getIntent().getExtras();
            friendName = extras.getString("friendName");

        } catch (Exception main){
            friendName = null;
        }
        ImageView imageView = (ImageView) findViewById(R.id.img_enter_contact);
        imageView.setImageResource(R.drawable.robotface);

        TextView editView = (TextView) findViewById(R.id.contact_enter_name);
        editView.setText(friendName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void selectImage(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            //If result is a cropped image
            if (requestCode == RESULT_CROP ) {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");

                    // Set The Bitmap Data To ImageView
                    // (ie. set the cropped image to imgView)
                    ImageView imgView = (ImageView) findViewById(R.id.img_enter_contact);
                    imgView.setImageBitmap(selectedBitmap);
                    imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                    System.out.println("BITMAP = " + selectedBitmap);

                    filename = selectedBitmap.toString();
                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/GiftBot";
                    File dir = new File(file_path);
                    if(!dir.exists())
                        dir.mkdirs();
                    File file = new File(dir, "giftBot" + fileCreateID + ".png");
                    FileOutputStream fOut = new FileOutputStream(file);

                    selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                    fileCreateID += 1;

                    System.out.println("FILE CREATED = " + file);
                    imgDecodableString = file.toString();
                }
            }


                // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.img_enter_contact);

                //If there is no image selected, set imageView to drawable robotface
                if (imgDecodableString.equals("")) {
                    imgView.setImageResource(R.drawable.robotface);
                } else {
                    // Set the Image in ImageView after decoding the String
                    //Possible error here if back is pressed - result is .jpg, might not be passed correctly
                    imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                }

                //Launch image crop
                performCrop(imgDecodableString);

            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! Something went wrong...", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }

        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Sorry, your device doesn't appear to support cropping";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public void addContact(View view) {

        //Read the text from the edit name field
        EditText editField = (EditText) findViewById(R.id.contact_enter_name);
        String friendName = editField.getText().toString();

        //Read raw text from gift ideas field
        EditText editField2 = (EditText) findViewById(R.id.contact_ideas);
        String contactIdeas = editField2.getText().toString();

        Intent intent = new Intent(EnterInfo.this, ChristmasList.class);
        intent.putExtra("friendName", friendName);
        intent.putExtra("friendImage", imgDecodableString);
        intent.putExtra("ideasText", contactIdeas);
        Integer clickID = null;
        intent.putExtra("clickID", clickID);
        startActivity(intent);
    }
}
