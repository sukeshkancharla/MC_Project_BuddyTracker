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
import android.content.ContextWrapper;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by VinodhKumar on 20/10/16.
 */

public class SignUpFragment extends Fragment {
    Button mUserRegisterButton;
    private EditText mUserName;
    private EditText mPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        mUserRegisterButton=(Button)rootView.findViewById(R.id.userRegisterButton);
        mUserRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context=getActivity();
                SharedPreferences sharedPreferences=context.getSharedPreferences(getString(R.string.shared_pref_id),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                //editor.putString(getString(R.string.phone_number),);
                mUserName=(EditText) rootView.findViewById(R.id.userNameRegister);
                editor.putString(getString(R.string.name), mUserName.getText().toString());
                mPhoneNumber=(EditText)rootView.findViewById(R.id.userNumberRegister);
                editor.putString(getString(R.string.phone_number),mPhoneNumber.getText().toString());
                if(!mPhoneNumber.getText().toString().equals("") && !mUserName.getText().toString().equals("")) {
                    editor.commit();
                    Toast.makeText(getActivity(),"added",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),sharedPreferences.getString(getString(R.string.name),"not available"),Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
                }
                //getActivity().findViewById(R.id.homeScreen).setVisibility(View.VISIBLE);
            }
        });
        return rootView;

    }
}
