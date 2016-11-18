package vinodhkumar.sample2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by VinodhKumar on 19/10/16.
 */

public class HomeFragment extends Fragment {

    Button mSignUpButton;
    private TextView userName;
    private TextView phoneNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.content_main, container, false);

                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.content_main, new SignUpFragment()).commit();

                getActivity().findViewById(R.id.homeScreen).setVisibility(View.GONE);
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences(getString(R.string.shared_pref_id), Context.MODE_PRIVATE);
                userName=(TextView)rootView.findViewById(R.id.userName);
                phoneNumber=(TextView)rootView.findViewById(R.id.userMobileNumber);
                userName.setText(sharedPreferences.getString(getString(R.string.name),null));
                phoneNumber.setText(sharedPreferences.getString(getString(R.string.phone_number),null));
                //rootView.findViewById(R.id.homeScreen).setVisibility(View.GONE);



        return rootView;
    }
}
