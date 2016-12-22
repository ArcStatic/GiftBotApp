package bytebeast.giftbot;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Emily on 08/11/2015.
 */
public class PresentListAdapter extends ArrayAdapter<Integer> {

    private final Activity context;
    private final ArrayList<Integer> giftState;
    private final ArrayList<String> giftIdea;

    public PresentListAdapter(Activity context, ArrayList<Integer> giftState, ArrayList<String> giftIdea) {

        super(context, R.layout.present_list_format, giftState);

        this.context=context;
        this.giftState=giftState;
        this.giftIdea=giftIdea;

    }

    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.present_list_format, null, true);

        ImageButton delete = (ImageButton) rowView.findViewById(R.id.deleteItem);
        TextView giftText = (TextView) rowView.findViewById(R.id.giftIdea);
        TextView boughtState = (TextView) rowView.findViewById(R.id.boughtState);
        TextView colourCheck = (TextView) rowView.findViewById(R.id.colourIndicator);
        ImageView giftCheck = (ImageView) rowView.findViewById(R.id.giftCheck);


        giftText.setText(giftIdea.get(position));
        boughtState.setText("Bought state: " + giftState.get(position));
        Integer i = giftState.get(position);

        //Red gift if gift not bought, green if bought (ie. red if 0, green if 1, '?' if 2)
        if (i==1){
            giftCheck.setImageResource(R.drawable.presentgreenbowsmall);
            colourCheck.setText("Colour detect: green");
        } else if (i==0) {
            giftCheck.setImageResource(R.drawable.presentredbowsmall);
            colourCheck.setText("Colour detect: clear");
        } else {
            giftCheck.setImageResource(R.drawable.questionmark);
            colourCheck.setText("Colour detect: no ideas, panel change");
        }

        delete.setOnClickListener(new View.OnClickListener() {

            //Methods execute when button on list item is clicked
            @Override
            public void onClick(View v) {
                //RelativeLayout rl = (RelativeLayout)v.getParent();
                //TextView tv = (TextView)rl.findViewById(R.id.giftIdea);
                //String text = tv.getText().toString();
                String text = "Tom smells";
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;

        }


}