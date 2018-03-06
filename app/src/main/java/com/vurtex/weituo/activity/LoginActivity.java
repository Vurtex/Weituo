package com.vurtex.weituo.activity;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vurtex.weituo.R;
import com.vurtex.weituo.base.ImmersionBaseActivity;
import com.vurtex.weituo.base.PowerApp;
import com.vurtex.weituo.entity.ResultInfo;
import com.vurtex.weituo.server.ApiService;
import com.vurtex.weituo.server.HttpServiceApi;
import com.vurtex.weituo.utils.ErrorAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import work.wanghao.simplehud.SimpleHUD;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ImmersionBaseActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @BindView(R.id.tv_toolbar)
    TextView tv_top_title;
    @BindView(R.id.image)
    ImageView img_top_btn;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.et_username)
    AutoCompleteTextView et_Username;
    @BindView(R.id.et_password)
    EditText et_Password;
    @BindView(R.id.btn_submit)
    TextView btn_Submit;
    @BindView(R.id.btn_register)
    TextView btn_Register;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(mToolbar)
                .navigationBarColor(R.color.colorBackground)
                .init();
    }

    @Override
    protected void initView() {
        // Set up the login form.
        populateAutoComplete();
        tv_top_title.setText("Employ");
        et_Password.setOnEditorActionListener((textView, id, keyEvent) -> {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
        );

        btn_Submit.setOnClickListener((v -> {
            attemptLogin();
        }));
        btn_Register.setOnClickListener((v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        }));
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(et_Username, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        et_Username.setError(null);
        et_Password.setError(null);

        // Store values at the time of the login attempt.
        String username = et_Username.getText().toString();
        String password = et_Password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            et_Password.setError(getString(R.string.error_invalid_password));
            focusView = et_Password;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            et_Username.setError(getString(R.string.error_field_required));
            focusView = et_Username;
            cancel = true;
        } else if (!isEmailValid(username)) {
            et_Username.setError(getString(R.string.error_invalid_username));
            focusView = et_Username;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            SimpleHUD.showLoadingMessage(LoginActivity.this, "正在登录...", true);
            //TODO 去登录
            HttpServiceApi mHttpServiceApi = ApiService.getInstance().createApiService(HttpServiceApi.class);
            mHttpServiceApi.dologin(username, password).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()).subscribe((loginResult -> {
                SimpleHUD.dismiss();
                ResultInfo resultInfo = loginResult.getResultInfo();
                if (resultInfo.getResultCode() == 1) {
                    PowerApp.user = loginResult.getUser();
                    PowerApp.token = loginResult.getToken();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, resultInfo.getResultMsg(), Toast.LENGTH_SHORT).show();
                }
            }), new ErrorAction() {
                @Override
                public void call(Throwable throwable) {
                    super.call(throwable);
                    SimpleHUD.showErrorMessage(LoginActivity.this, "" + throwable.getMessage());
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        et_Username.setAdapter(adapter);
    }



    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}

