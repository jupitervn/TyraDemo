package vn.jupiter.tyrademo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String DEFAULT_ACCOUNT = "tadmin@tyraappen.se";
    public static final String DEFAULT_PASSWORD = "1111";
    private AppNavigator appNavigator;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appNavigator = new FragmentBasedAppNavigator(this, R.id.fl_content);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.sign_in_progress));
        if (savedInstanceState == null) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            if ((currentUser != null && !currentUser.isAuthenticated()) || currentUser == null) {
                showLoginLoading();
                ParseUser.logInInBackground(DEFAULT_ACCOUNT, DEFAULT_PASSWORD, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        hideLoginLoading();
                        if (e != null) {
                            displayError(e);
                        } else {
                            showHomePage();
                        }
                    }
                });
            } else {
                showHomePage();
            }
        }
    }

    public void showHomePage() {
        appNavigator.openListCategory();
    }

    public void displayError(Exception ex) {
        Toast.makeText(this, R.string.error_cannot_login, Toast.LENGTH_LONG).show();
    }

    public void showLoginLoading() {
        progressDialog.show();
    }

    public void hideLoginLoading() {
        progressDialog.dismiss();
    }
}
