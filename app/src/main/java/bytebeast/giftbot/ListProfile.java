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


        //Split ideas into separate items by newline char and comma
        String[] ideaSplit = giftIdeaRaw.split("\n|,");

        //Split giftstate at every character (ie. into individual digits)
        String[] stateSplit = giftStateRaw.split("(?!^)");

        //Construct bought state list (0 = not bought, 1 = bought)
        //int j counter needed - bought state only recorded for actual ideas, not whitespace
        int j = 0;
        for (int i=0; i < ideaSplit.length; i++){
            //Strip leading whitespace character from gift entry if needed
            //Capitalise first letter
            //NOTE: first condition check to resolve crash caused by empty string resulting from using \n after a comma
            //Second condition check to resolve crash caused by garbage input like ',,, ,    ,'
            if ((!ideaSplit[i].equals("")) && (!(ideaSplit[i].trim()).equals(""))) {
                if (ideaSplit[i].charAt(0) == ' ') {
                    String ideaStripped = ideaSplit[i].replaceFirst("\\s", "");
                    ideaStripped = (ideaStripped.substring(0, 1)).toUpperCase() + ideaStripped.substring(1);
                    System.out.println("Regex attempted");
                    giftIdeaList.add(ideaStripped);
                } else {
                    String ideaStripped = (ideaSplit[i].substring(0, 1)).toUpperCase() + ideaSplit[i].substring(1);
                    giftIdeaList.add(ideaStripped);
                }

                Integer newState = Integer.parseInt(stateSplit[j]);
                giftStateList.add(newState);
                j++;
            }
        }

        System.out.println("IdeaList check: " + giftIdeaList);
        System.out.println("StateList check: " + giftStateList);

        final PresentListAdapter adapter = new PresentListAdapter(this, giftStateList, giftIdeaList);
        list=(ListView)findViewById(R.id.giftList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //See which button was clicked
                long viewID = view.getId();

                //If delete button is clicked, remove item from list
                if (viewID == R.id.deleteItem){
                    adapter.notifyDataSetChanged();
                }

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
        //TODO: update lists on ChristmasList view to update after deletion

        startActivity(intent);
    }
}
