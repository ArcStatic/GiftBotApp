package bytebeast.giftbot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddOptionsDialog extends DialogFragment {

    //This activity generates a 3-choice dialogue box
    //'Import contacts from facebook, contacts, manual', etc

    final CharSequence[] addOptions = {"Facebook", "Contacts", "Neither, add manually"};

    String addChoice;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add contacts to list from...")
                .setItems(addOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        NoticeDialogListener activity = (NoticeDialogListener) getActivity();
                        activity.onAddChoiceClick(which);


                    }
                });
        return builder.create();
    }

    //Used to send data from this fragment back to MainMenu activity
    public interface NoticeDialogListener {
        public void onAddChoiceClick(int dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
