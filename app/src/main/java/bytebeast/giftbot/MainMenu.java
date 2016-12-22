package bytebeast.giftbot;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class MainMenu extends AppCompatActivity implements AddOptionsDialog.NoticeDialogListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        System.out.println("LOGCAT CHECK!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);


    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    /** Called on 'View My List' press */
    public void viewList(View view) {
        Intent intent = new Intent(this, ChristmasList.class);
        startActivity(intent);
    }

    public void addToGiftList(View view) {
        Intent intent = new Intent(this, EnterInfo.class);
        startActivity(intent);
    }


    /** Not used! Option 1 (add from contacts) interacted oddly with imagecrop and caused crashes */
    @Override
    public void onAddChoiceClick(int dialog){
        //If Facebook is chosen, do this:
        if (dialog==0) {
            /**NOTE: users can only see other people who use this app with FB API 2.0*/
            Intent intent = new Intent(this, facebook_login.class);
            startActivity(intent);}
        //If contacts is chosen
        else if (dialog==1) {
            Intent intent = new Intent(this, contacts_list.class);
            startActivity(intent);}
        //If add manually is chosen
        else if (dialog==2) {
            Intent intent = new Intent(this, EnterInfo.class);
            startActivity(intent);}

    }

    /** Displays options menu fragment - add people to list from facebook, contacts, manual entry */
    /** Also not used */
    public void addToList(View view){
        DialogFragment newFragment = new AddOptionsDialog();
        newFragment.show(getFragmentManager(), "selectOptions");
    }
}
