package bytebeast.giftbot;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ContactListViewAbandoned extends AppCompatActivity {

    private static final String TAG = contacts_list.class.getSimpleName();
    public static Integer addID = 0;
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;     // contacts unique ID


    /** NOTE: Contact pictures can't be received if they are generated from Facebook profile pics - manually set images from phone only*/

    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        //CustomListAdapterContacts adapter=new CustomListAdapterContacts(this, contactNames, addPicID);
        CustomListAdapterContacts adapter=new CustomListAdapterContacts(this, addNameList, addPicIDList);
        list=(ListView)findViewById(R.id.contactAddList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Selecteditem = contactNames[+position];
                Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public String[] contactNames={

    };

    public Integer[] addPicID={

    };


    //new arraylist?
    //Works!
    public ArrayList<Integer> addPicIDList = new ArrayList<Integer>();

    public ArrayList<String> addNameList = new ArrayList<String>();

    public void onClickSelectContact(View btnSelectContact) {

        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();

        }
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            cursor.close();
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);



        TextView textView = (TextView) findViewById(R.id.contact_name);
        ImageView imageView = (ImageView) findViewById(R.id.img_contact);
        imageView.setImageResource(R.drawable.robotface);
        textView.setText(contactName);

        addID += 1;


        //TODO: find out why these cunts are throwing errors
        //addPicIDList.add(addID);
        //contactNames.add(contactName);

        addPicIDList.add(addID);
        addNameList.add(contactName);

        System.out.println("!!!!ADD ID " + addID);
        System.out.println(addID);
        System.out.println(addPicIDList);
        System.out.println(contactName);
        System.out.println(addNameList);
        System.out.println("!!!.GET CHECK!!!" + addNameList.get(0));

    }

}
