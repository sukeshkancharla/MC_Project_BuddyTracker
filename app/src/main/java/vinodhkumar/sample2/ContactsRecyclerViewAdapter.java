package vinodhkumar.sample2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VinodhKumar on 17/11/16.
 */

public class ContactsRecyclerViewAdapter extends RecyclerView
        .Adapter<ContactsRecyclerViewAdapter
        .ContactObjectHolder>{

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<ContactObject> mDataset;
    private Context context;
    //private static MyClickListener myClickListener;

    public static class ContactObjectHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mPhoneNo;
        Button mInvite;
        Button mRequest;
        private Context context;
        public ContactObjectHolder(View itemView, final Context context) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.contactName);
            mPhoneNo = (TextView) itemView.findViewById(R.id.contactPhoneNo);
            mInvite=(Button) itemView.findViewById(R.id.inviteButton);
            mRequest=(Button) itemView.findViewById(R.id.requestButton);
            mInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent=new Intent();
                    sharingIntent.setAction(Intent.ACTION_SEND);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT,"You are invited for Buddy Tracker");
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_PHONE_NUMBER,mPhoneNo.getText().toString());
                    context.startActivity(sharingIntent);

                }
            });
            //dateTime = (TextView) itemView.findViewById(R.id.textView2);
            //Log.d(LOG_TAG, "Adding Listener");
            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            myClickListener.onItemClick(getAdapterPosition(), v);
//        }
    }

    public ContactsRecyclerViewAdapter(ArrayList<ContactObject> myDataset,Context context) {
        mDataset = myDataset;
        this.context=context;
    }

    @Override
    public ContactObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_recyler_view_items, parent, false);

        ContactObjectHolder contactObjectHolder = new ContactObjectHolder(view,context);
        return contactObjectHolder;
    }

    @Override
    public void onBindViewHolder(ContactObjectHolder holder, int position) {
        holder.mName.setText(mDataset.get(position).getmName());
        holder.mPhoneNo.setText(mDataset.get(position).getmPhoneNo());
        //holder.dateTime.setText(mDataset.get(position).getmText2());
    }

    public void addItem(ContactObject contactObj, int index) {
        mDataset.add(contactObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
