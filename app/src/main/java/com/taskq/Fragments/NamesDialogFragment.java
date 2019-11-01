package com.taskq.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.DialogFragment;

import com.taskq.Activities.taskQEntryActivity;

import android.provider.ContactsContract;
import android.widget.Toast;

import com.taskq.R;

import java.util.ArrayList;
import java.util.List;

public class NamesDialogFragment extends DialogFragment {

    private int height;
    private int width;
    private Button buttonDone;
    private EditText editTextSearch;
    private ListView listViewNames;
    private boolean bCheckIfAsked = false;
    private SimpleCursorAdapter adapter;
    private View view;

    //Contacts Vars
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME
            };
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    private static final String ContactNameQuery =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY:
                    ContactsContract.Contacts.DISPLAY_NAME;

    final String[] from = new String[] {
            ContactNameQuery,
    };
    final int[] to = new int[] {R.id.listview_contact_names};


    public NamesDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_names_dialog, container, false);

        bCheckIfAsked = false;

        getActivity().setTheme(R.style.AppTheme_MaterialTab);
        //getDialog().setTitle("Tags"); //does not do anything

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        buttonDone = getDialog().findViewById(R.id.NamesDialog_DoneButton);
        listViewNames = getDialog().findViewById(R.id.NamesDialog_LV_Names);
        editTextSearch = getDialog().findViewById(R.id.NamesDialog_ET_Selected);

        //Set Dialog Size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = (int)(displayMetrics.heightPixels * 0.9f);
        width = (int)(displayMetrics.widthPixels * 0.9f);
        getDialog().getWindow().setLayout(width, height);
        //Make Dialog Corners Transparent
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Return back to the taskQEntryActivity
        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((taskQEntryActivity)getActivity()).onDialogDismiss();
                dismiss();
            }
        });

        AccessContact();

        //Access the contacts dB and populate list with it
        String selString = "SELECT " + ContactNameQuery;// + " FROM " + ContactsContract.Contacts.CONTENT_URI;
        ContentResolver cr = getContext().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        adapter = new SimpleCursorAdapter( view.getContext(), R.layout.listview_contacts, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listViewNames.setAdapter(adapter);


        Toast.makeText(getActivity(), "No of contacts is." + cursor.getCount(), Toast.LENGTH_LONG).show();
    }


    //Request access to contacts
    //ToDo:requestPermissions needs min SDK of 23.
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;

    private void AccessContact()
    {
        if(bCheckIfAsked == false) {
            //ToDo - has to be a better way to prevent loops
            bCheckIfAsked = true;

            List<String> permissionsNeeded = new ArrayList<String>();
            final List<String> permissionsList = new ArrayList<String>();

            if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS)) {
                permissionsNeeded.add("Read Contacts");
            }
            if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS)) {
                permissionsNeeded.add("Write Contacts");
            }

            if (permissionsList.size() > 0) {
//                if (permissionsNeeded.size() > 0) {
//                    String message = "You need to grant access to " + permissionsNeeded.get(0);
//                    for (int i = 1; i < permissionsNeeded.size(); i++) {
//                        message = message + ", " + permissionsNeeded.get(i);
//                    }
//                    showMessageOKCancel(message,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                                            REQUEST_MULTIPLE_PERMISSIONS);
//                                }
//                            });
//                    return;
//                }
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_MULTIPLE_PERMISSIONS);
            }
            permissionsList.clear();
        }
        return;
    }
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (getContext().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}






































