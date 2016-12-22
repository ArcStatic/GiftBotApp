package bytebeast.giftbot;

/**
 * Created by Emily on 17/10/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapterContacts extends ArrayAdapter<String> {

    private final Activity context;
    private Integer arrayLength;
    private final ArrayList<String> contactName;
    private final ArrayList<Integer> contactImg;

    public CustomListAdapterContacts(Activity context, ArrayList<String> contactName, ArrayList<Integer> contactImg) {
        super(context, R.layout.contact_list_format, contactName);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.contactName=contactName;
        this.contactImg=contactImg;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_format, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.newContactName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.contactPic);


        //contactName and contactImg will always be the same length, only needs checked once
        arrayLength = contactName.size();
        System.out.println("!!!!!ARRAYLENGTH CHECK = " + arrayLength);
        System.out.println("!!!!!ARRAYNAME CHECK = " + contactName.get(0));
        //ie. if contactName and contactImg are empty
        if (arrayLength == 1){
            System.out.println("!!!ARRAYLENGTH = 1!!!");
            txtTitle.setText(contactName.get(position));

            imageView.setImageResource(R.drawable.robotface);

        } else if (arrayLength > 1) {

            System.out.println("!!!ARRAYLENGTH != 1!!!");
            System.out.println("!!!POSITION = " + position);
            //txtTitle.setText(contactName.get(position));
            txtTitle.setText(contactName.get(position));

            Integer i = contactImg.get(position);


            if (i==0){
                imageView.setImageResource(R.drawable.presentgreenbowsmall);
            } else if (i>0) {
                imageView.setImageResource(R.drawable.presentredbowsmall);
            }}


        return rowView;

    };
}