package bytebeast.giftbot;

/**
 * Created by Emily on 17/10/2015.
 */

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<String> imgid;
    private final ArrayList<Integer> boughtCount;
    private final ArrayList<ArrayList<Integer>> shopCount;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<String> imgid, ArrayList<Integer> boughtCount, ArrayList<ArrayList<Integer>> shopCount) {
        super(context, R.layout.activity_list_format, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.boughtCount=boughtCount;
        this.shopCount=shopCount;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_format, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.listFriend);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.listImg);
        TextView subText1 = (TextView) rowView.findViewById(R.id.boughtCount);
        TextView subText2 = (TextView) rowView.findViewById(R.id.shopCount);
        ImageView giftCheck = (ImageView) rowView.findViewById(R.id.giftCheck);


        txtTitle.setText(itemname.get(position));
        String imgidString = imgid.get(position);
        if (imgidString.equals("")){
            imageView.setImageResource(R.drawable.robotface);
        } else {
            System.out.println("IMG ID STRING = " + imgidString);
            imageView.setImageBitmap(BitmapFactory.decodeFile(imgidString));
        }
        subText1.setText("Gifts bought: " + boughtCount.get(position));
        ArrayList<Integer> shopCheckList = shopCount.get(position);
        Integer shopListLength = shopCheckList.size();
        Integer yes = 0;
        Integer no = 0;

        //1 for gift bought, 0 for not bought, 2 for no ideas entered
        //i will keep track of number of gifts bought
        //i should equal shopListLength if all gifts are bought
        //if no ideas supplied, i = -1
        for (int x = 0; x < shopListLength; x++){
            if (shopCheckList.get(x) == 1){
                yes += 1;
            } else if(shopCheckList.get(x) == 2){
                yes = -1;
            } else {
                no += 1;
            }
        }

        //Red gift if any gifts not bought, green gift if shopping complete

        //No ideas supplied
        if (yes== -1) {
            giftCheck.setImageResource(R.drawable.questionmark);
            subText2.setText("No ideas entered");
            //No gifts bought
        } else if (yes != shopListLength){
            giftCheck.setImageResource(R.drawable.presentredbowsmall);
            subText2.setText("Gifts to buy: " + no);

        //All gifts bought!
        } else {
            giftCheck.setImageResource(R.drawable.presentgreenbowsmall);
            subText2.setText("All gifts bought!");
        }

        return rowView;

    };


}