package bytebeast.giftbot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class AddToList extends AppCompatActivity {

    //This class lets the user choose which contacts to add to the list
    //User has chosen to add manually

    //Add data to existing list giftListFriends

    ArrayList newFriendAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);
    }



}
