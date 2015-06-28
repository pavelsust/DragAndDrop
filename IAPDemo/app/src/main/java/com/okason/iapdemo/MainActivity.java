package com.okason.iapdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.okason.iapdemo.billing.IabHelper;
import com.okason.iapdemo.billing.IabResult;
import com.okason.iapdemo.billing.Inventory;
import com.okason.iapdemo.billing.Purchase;


public class MainActivity extends Activity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    IabHelper mHelper;
    public boolean mIsPremium = false;
    static final int RC_REQUEST = 10001;

    private final int CURRENT_COUNTER = 0;
    private final String PUBLIC_KEY = "some_key_value_from_developer_console";
    public final static String IS_PREMIUM_USER = "is_premium_user";

    public final static String SKU_UNLIMITED_VERSION = "unlimited_version";
    public final static String COUNTER = "counter";

    private Button mEditButton;
    private Button mSaveButton;
    Activity mActivity;

    ProgressDialog upgradeDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        mEditButton = (Button)findViewById(R.id.demo_edit_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsPremium){
                    //non premium user check if limit has exceeded
                    //get the number of completed edits
                    int counter = mPref.getInt(COUNTER, 0);

                    if (counter > 20){
                        promptForUpgrade();
                    }
                }
            }
        });

        mSaveButton = (Button)findViewById(R.id.demo_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = mPref.getInt(COUNTER, 0);
                mEditor.putInt(COUNTER, counter + 1);
                mEditor.apply();
            }
        });





        mHelper = new IabHelper(this, PUBLIC_KEY);
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {

            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    alert("problem makeing purchse" + result);
                    return;
                } else {
                    getUserStatus();
                }
            }
        });
    }

    private void promptForUpgrade() {
        AlertDialog.Builder upgradeAlert = new AlertDialog.Builder(this);
        upgradeAlert.setTitle("Upgrade?");
        upgradeAlert.setMessage("Do you want to upgrade to unlimited version?");
        upgradeAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //set progress dialog and start the in app purchase process
                upgradeDialog = ProgressDialog.show(mActivity, "Please wait", "Upgrade transaction in process", true);

                  /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
                String payload = "";

                mHelper.launchPurchaseFlow(mActivity, SKU_UNLIMITED_VERSION, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
            }
        }).setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        upgradeAlert.show();
    }


    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                alert("Error purchasing: " + result);
                upgradeDialog.dismiss();
            }


            else if (purchase.getSku().equals(SKU_UNLIMITED_VERSION)) {
                alert("Thank you for upgrade");
                mIsPremium = true;
                setUserStatus(true);
                upgradeDialog.dismiss();
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(LOG_TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                alert("Failed to check if you have Premium version: " + result);
                return;
            }

            Log.d(LOG_TAG, "Query inventory was successful.");
            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_UNLIMITED_VERSION);
            mIsPremium = (premiumPurchase != null);
            Log.d(LOG_TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
        }
    };



    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }

    /***
     * Check if the user is a premium user, if yes set the isPremium user boolean to true
     * Else set to no
     */
    private void getUserStatus(){
        if (mPref.getBoolean(IS_PREMIUM_USER, false)) {
            mIsPremium = true;
        } else {
            mHelper.queryInventoryAsync(mGotInventoryListener);

        }
    }

    private void setUserStatus(boolean status){
        mEditor.putBoolean(IS_PREMIUM_USER, status);
        mEditor.apply();
    }
}
