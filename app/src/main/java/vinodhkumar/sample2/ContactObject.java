package vinodhkumar.sample2;

/**
 * Created by VinodhKumar on 17/11/16.
 */

public class ContactObject {

    private String mName;
    private String mPhoneNo;

    ContactObject (String name, String phoneNo){
        mName = name;
        mPhoneNo = phoneNo;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoneNo() {
        return mPhoneNo;
    }

    public void setmPhoneNo(String mPhoneNo) {
        this.mPhoneNo = mPhoneNo;
    }
}
