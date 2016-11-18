package vinodhkumar.sample2;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by VinodhKumar on 19/10/16.
 */

public class RequestLocationFragment extends Fragment {

//    private String dummyContacts[] = {"abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx"};
//    private String dummyNumbers[] = {"123", "456", "789", "101", "213", "141", "516", "171"};

    public static ArrayList<String> contactNames = new ArrayList<String>();
    public static ArrayList<String> contactPhoneNos = new ArrayList<String>();
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;


    TableLayout mContactsTableLayout;
    TableRow mContactsTableRow;
    TextView mContactName, mContactNumber;
    Button mRequestOrInviteButton;
    private RecyclerView mContactsRecyclerView;
    private RecyclerView.Adapter mContactsAdapter;
    private RecyclerView.LayoutManager mContactsLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_request_location, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().getApplicationContext().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            fetchContactsFromPhone();
        }

        /* Populate recycler view */
        mContactsRecyclerView = (RecyclerView) rootView.findViewById(R.id.contactsRecyclerView);
        mContactsRecyclerView.setHasFixedSize(true);
        mContactsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mContactsRecyclerView.setLayoutManager(mContactsLayoutManager);
        mContactsAdapter = new ContactsRecyclerViewAdapter(getDataSet(),getActivity());
        mContactsRecyclerView.setAdapter(mContactsAdapter);
//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration (this.getApplicationContext(), LinearLayoutManager.VERTICAL);
//        mRecyclerView.addItemDecoration(itemDecoration);


        //mContactsTableLayout = (TableLayout) rootView.findViewById(R.id.contactsTableLayout);
//        addHeaders();
//        addData();

        return rootView;
    }

    public ArrayList<ContactObject> getDataSet() {
        ArrayList results = new ArrayList<ContactObject>();
        Cursor res;

        int count = contactNames.size();
        for (int i=0;i<count;i++){
            ContactObject cObj = new ContactObject(contactNames.get(i),contactPhoneNos.get(i));
            results.add(i,cObj);
        }
        return results;
    }

    public void fetchContactsFromPhone(){
        ContentResolver cr =  getActivity().getApplicationContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");


        if (cur.getCount() > 0 ) {
            int countCheck = 0;
            while (cur.moveToNext() && countCheck < 75) {
                countCheck++;
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactNames.add(name);
                        contactPhoneNos.add(phoneNo);
                        //Toast.makeText(NativeContentProvider.this, "Name: " + name
                        //      + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
            }
        }
    }

//    public void addData() {
//
//        for (int i = 0; i < contactNames.size(); i++) {
//            /** Create a TableRow dynamically **/
//            mContactsTableRow = new TableRow(getActivity().getApplicationContext());
//            mContactsTableRow.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//            /** Creating a TextView to add to the row **/
//            mContactName = new TextView(getActivity().getApplicationContext());
//            mContactName.setText(contactNames.get(i));
//            mContactName.setTextColor(Color.CYAN);
//            mContactName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//            mContactName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//            mContactName.setPadding(2, 2, 2, 2);
//            mContactsTableRow.addView(mContactName);  // Adding textView to tablerow.
//
//            /** Creating another textview **/
//            mContactNumber = new TextView(getActivity().getApplicationContext());
//            mContactNumber.setText(contactPhoneNos.get(i));
//            mContactNumber.setTextColor(Color.BLACK);
//            mContactNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//            mContactNumber.setPadding(2, 2, 2, 2);
//            mContactNumber.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//            mContactsTableRow.addView(mContactNumber); // Adding textView to tablerow.
//
//            mRequestOrInviteButton = new Button(getActivity().getApplicationContext());
//            mRequestOrInviteButton.setText("Invite/Request");
//            mRequestOrInviteButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//            mContactsTableRow.addView(mRequestOrInviteButton);
//
//
//            // Add the TableRow to the TableLayout
//            mContactsTableLayout.addView(mContactsTableRow, new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//            mRequestOrInviteButton.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//                    /* Handle reply here */
//
//
//                }
//            });
//        }
//
//    }
//
//    public void addHeaders() {
//        /** Create a TableRow dynamically **/
//        mContactsTableRow = new TableRow(getActivity().getApplicationContext());
//        mContactsTableRow.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        /** Creating a TextView to add to the row **/
//        TextView mContactNameHeader = new TextView(getActivity().getApplicationContext());
//        mContactNameHeader.setText("Name");
//        mContactNameHeader.setTextColor(Color.GRAY);
//        mContactNameHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        mContactNameHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        mContactNameHeader.setPadding(2, 2, 2, 0);
//        //mContactNameHeader.setBackgroundResource(R.drawable.border);
//        mContactsTableRow.addView(mContactNameHeader);  // Adding textView to tablerow.
//
//        /** Creating another textview **/
//        TextView mContactNumberHeader = new TextView(getActivity().getApplicationContext());
//        mContactNumberHeader.setText("Number");
//        //mContactNumberHeader.setBackgroundResource(R.drawable.border);
//        mContactNumberHeader.setTextColor(Color.GRAY);
//        mContactNumberHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        mContactNumberHeader.setPadding(2, 2, 2, 0);
//        mContactNumberHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//
//        mContactsTableRow.addView(mContactNumberHeader); // Adding textView to tablerow.
//
//        Button mContactInviteOrRequestHeader = new Button(getActivity().getApplicationContext());
//        mContactInviteOrRequestHeader.setText("");
//        //mContactNameHeader.setLayoutParams(new Layout);
//        mContactInviteOrRequestHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//        mContactInviteOrRequestHeader.setVisibility(View.INVISIBLE);
//        //mContactInviteOrRequestHeader.setPadding(5, 5, 5, 0);
//        mContactsTableRow.addView(mContactInviteOrRequestHeader);  // Adding textView to tablerow.
//
//        // Add the TableRow to the TableLayout
//        mContactsTableLayout.addView(mContactsTableRow, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        // we are adding two textviews for the divider because we have two columns
//        mContactsTableRow = new TableRow(getActivity().getApplicationContext());
//        mContactsTableRow.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        /** Creating another textview **/
//        TextView divider = new TextView(getActivity().getApplicationContext());
//        divider.setText("--------");
//        divider.setTextColor(Color.DKGRAY);
//        divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        divider.setPadding(1, 0, 0, 0);
//        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        mContactsTableRow.addView(divider); // Adding textView to tablerow.
//
//        TextView divider2 = new TextView(getActivity().getApplicationContext());
//        divider2.setText("----------");
//        divider2.setTextColor(Color.DKGRAY);
//        divider2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        divider2.setPadding(1, 0, 0, 0);
//        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        mContactsTableRow.addView(divider2); // Adding textView to tablerow.
//
//        // Add the TableRow to the TableLayout
//        mContactsTableLayout.addView(mContactsTableRow, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//    }


//    public void addData1() {
//
//        for (int i = 0; i < dummyContacts.length; i++) {
//            /** Create a TableRow dynamically **/
//            mContactsTableRow = new TableRow(getActivity().getApplicationContext());
//            mContactsTableRow.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//            /** Creating a TextView to add to the row **/
//            mContactName = new TextView(getActivity().getApplicationContext());
//            mContactName.setText(dummyContacts[i]);
//            mContactName.setTextColor(Color.CYAN);
//            mContactName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//            mContactName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//            mContactName.setPadding(2, 2, 2, 2);
//            mContactsTableRow.addView(mContactName);  // Adding textView to tablerow.
//
//            /** Creating another textview **/
//            mContactNumber = new TextView(getActivity().getApplicationContext());
//            mContactNumber.setText(dummyNumbers[i]);
//            mContactNumber.setTextColor(Color.BLACK);
//            mContactNumber.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//            mContactNumber.setPadding(2, 2, 2, 2);
//            mContactNumber.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//            mContactsTableRow.addView(mContactNumber); // Adding textView to tablerow.
//
//            mRequestOrInviteButton = new Button(getActivity().getApplicationContext());
//            mRequestOrInviteButton.setText("Invite/Request");
//            mRequestOrInviteButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//            mContactsTableRow.addView(mRequestOrInviteButton);
//
//
//            // Add the TableRow to the TableLayout
//            mContactsTableLayout.addView(mContactsTableRow, new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//            mRequestOrInviteButton.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//                    /* Handle reply here */
//
//
//                }
//            });
//        }
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission is granted
//                showContacts();
//            } else {
//                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }
}
