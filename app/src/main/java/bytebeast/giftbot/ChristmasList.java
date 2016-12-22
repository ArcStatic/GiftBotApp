package bytebeast.giftbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChristmasList extends AppCompatActivity {

    //Activity views status of friends added
    //TODO Add 'all', 'bought', 'not bought' views

    //TODO: Receive and deal with custom image information

    public String newFriendName;
    public String newFriendImg;
    public String giftIdeasRawText;
    public Integer boughtCount = 0;
    public Integer notBoughtCount = 0;
    public String giftIdeaState = "";

    public static ArrayList<String> friendNamesList = new ArrayList<>();
    public static ArrayList<String> friendImageList = new ArrayList<>();
    public static ArrayList<String> rawGiftTextList = new ArrayList<>();
    public static ArrayList<String> rawGiftStateList = new ArrayList<>();
    public static ArrayList<Integer> giftComplete = new ArrayList<>();
    public ArrayList<Integer> giftNotCompleteInner = new ArrayList<>();

    public static ArrayList<ArrayList<Integer>> giftNotCompleteOuter = new ArrayList<>();

    public ArrayList<Integer> innerNest;
    public String clickName;
    public String clickImg;
    public Integer clickID;


    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_list);

        //If activity reached from EnterInfo class, do this
        try {
            Bundle extras = getIntent().getExtras();
            clickID = extras.getInt("clickID");
            System.out.println("ClickID = " + clickID);
            //If activity is reached from EnterInfo, create new entries in lists
            //if (clickID == null) {
                System.out.println(extras.getString("friendName"));
                newFriendName = extras.getString("friendName");
                newFriendImg = extras.getString("friendImage");
                System.out.println("FriendImg = " + newFriendImg);
                friendNamesList.add(newFriendName);
                friendImageList.add(newFriendImg);
                System.out.println("FRIEND IMAGE LIST = " + friendImageList);
                System.out.println("NEW FRIEND IMG = " + newFriendImg);

                giftIdeasRawText = extras.getString("ideasText");
                System.out.println("RAW TEXT = " + giftIdeasRawText);

                if (!giftIdeasRawText.equals("")) {

                    String[] ideaSplit = giftIdeasRawText.split("\n");
                    rawGiftTextList.add(giftIdeasRawText);
                    notBoughtCount = ideaSplit.length;
                    //Create inner array list
                    for (int x = 0; x < notBoughtCount; x++) {
                        giftNotCompleteInner.add(0);
                        giftIdeaState += "0";
                    }
                    //Add inner list to outer arraylist
                    //Output should be in format [[0,0,0,1],[0,1,1,1,0],[1,0] ... ] ...
                    giftNotCompleteOuter.add(giftNotCompleteInner);

                    //If no ideas given, put marker integer 2
                } else {
                    giftIdeaState = "2";
                    rawGiftTextList.add("");
                    giftNotCompleteInner.add(2);
                    giftNotCompleteOuter.add(giftNotCompleteInner);
                }
                notBoughtCount = 0;
                giftComplete.add(0);
                rawGiftStateList.add(giftIdeaState);
                System.out.println("!!!!IDEA NOT BOUGHT COUNT = " + giftNotCompleteOuter);
                System.out.println("!!!!IDEA BOUGHT COUNT = " + giftComplete);
            //If activity is reached from ListProfile, update lists
            //} else {

            //}

        }


        //If no new contacts are added, do this
        catch (Exception main){
            newFriendName = "BLANK";
            giftIdeasRawText = "NO IDEAS YET" ;
        }

        TextView textView = (TextView) findViewById(R.id.nameTest);
        textView.setText(newFriendName);

        TextView textView2 = (TextView) findViewById(R.id.editTest);
        textView2.setText(giftIdeasRawText);

        System.out.println("!!!!FRIENDS ARRAY = " + friendNamesList);
        System.out.println("!!!!IDEAS RAW TEXT = " + giftIdeasRawText);

        CustomListAdapter adapter=new CustomListAdapter(this, friendNamesList, friendImageList, giftComplete, giftNotCompleteOuter);
        list=(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

        //OnClick do [stuff]
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //String clickName = friendNamesList.get(position);
                System.out.println("POSITION = " + position);
                System.out.println("ID = " + id);
                System.out.println("RETRIEVED NAME = " + friendNamesList.get(position));
                clickName = friendNamesList.get(position);
                clickImg = friendImageList.get(position);
                giftIdeasRawText = rawGiftTextList.get(position);
                giftIdeaState = rawGiftStateList.get(position);
                innerNest = giftNotCompleteOuter.get(position);
                System.out.println("INNER NEST = " + innerNest);
                clickID = position;

                for (int x = 0; x < innerNest.size(); x++) {
                    if (innerNest.get(x) == 1) {
                        boughtCount += 1;
                    } else if (innerNest.get(x) == 0) {
                        notBoughtCount += 1;
                    } else {
                        notBoughtCount = 0;
                    }
                    System.out.println("!!!NOT BOUGHT CHECK = " + notBoughtCount);
                }
                viewProfile(clickName, clickImg, boughtCount, notBoughtCount, clickID);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_christmas_list, menu);
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

    public void viewProfile(String clickName, String clickImg, Integer boughtCount, Integer notBoughtCount, Integer clickID) {
        Intent intent = new Intent(this, ListProfile.class);
        intent.putExtra("clickName", clickName);
        intent.putExtra("clickImg", clickImg);
        intent.putExtra("boughtCount", boughtCount);
        intent.putExtra("notBoughtCount", notBoughtCount);
        intent.putExtra("giftIdeasRaw", giftIdeasRawText);
        intent.putExtra("giftIdeaState", giftIdeaState);
        intent.putExtra("clickID", clickID);
        //intent.putExtra("ideasText", contactIdeas);
        startActivity(intent);
    }
}
