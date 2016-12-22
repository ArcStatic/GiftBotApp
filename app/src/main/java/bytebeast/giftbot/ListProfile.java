package bytebeast.giftbot;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListProfile extends AppCompatActivity {

    public String clickName;
    public String clickImg;
    public Integer clickID;
    public Integer boughtCount;
    public Integer notBoughtCount;
    public String giftStateRaw;
    public String giftIdeaRaw;

    public static ArrayList<Integer> giftStateList = new ArrayList<>();
    public static ArrayList<String> giftIdeaList = new ArrayList<>();

    ListView list;

    //TODO: resolve ChristmasList being wiped on no gift ideas entry being clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profile);

        // Clear Arraylists to prevent items multiplying
        giftIdeaList.clear();
        giftStateList.clear();

        // Unpack extras
        Bundle extras = getIntent().getExtras();
        clickName = extras.getString("clickName");
        clickImg = extras.getString("clickImg");
        clickID = extras.getInt("clickID");
        boughtCount = extras.getInt("boughtCount");
        notBoughtCount = extras.getInt("notBoughtCount");
        giftIdeaRaw = extras.getString("giftIdeasRaw");
        giftStateRaw = extras.getString("giftIdeaState");

        //Set views
        TextView textView = (TextView) findViewById(R.id.ProfileName);
        textView.setText(clickName);

        TextView textView2 = (TextView) findViewById(R.id.giftsNotBought);
        textView2.setText("Gifts still to buy: " + notBoughtCount);

        TextView textView3 = (TextView) findViewById(R.id.giftsBought);
        textView3.setText("Gifts bought: " + boughtCount);

        ImageView imgView = (ImageView) findViewById(R.id.profileImg);
        if (clickImg.equals("")){
            imgView.setImageResource(R.drawable.robotface);
        } else {
            imgView.setImageBitmap(BitmapFactory.decodeFile(clickImg));
        }

        String[] ideaSplit = giftIdeaRaw.split("\n");
        //Split at every character
        String[] stateSplit = giftStateRaw.split("(?!^)");

        //Construct bought state list (0 = not bought, 1 = bought)
        for (int i=0; i < ideaSplit.length; i++){
            giftIdeaList.add(ideaSplit[i]);
            Integer newState = Integer.parseInt(stateSplit[i]);
            giftStateList.add(newState);
        }

        System.out.println("IdeaList check: " + giftIdeaList);
        System.out.println("StateList check: " + giftStateList);

        PresentListAdapter adapter=new PresentListAdapter(this, giftStateList, giftIdeaList);
        list=(ListView)findViewById(R.id.giftList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_profile, menu);
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
        Intent intent = new Intent(this, ChristmasList.class);
        intent.putExtra("clickID", clickID);

        startActivity(intent);
    }
}
