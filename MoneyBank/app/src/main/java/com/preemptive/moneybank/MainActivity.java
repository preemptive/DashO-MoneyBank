package com.preemptive.moneybank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.preemptive.moneybank.api.RequestQueueSingleton;
import com.preemptive.moneybank.data.LoginRepository;
import com.preemptive.moneybank.data.model.Account;
import com.preemptive.moneybank.data.model.LoggedInUser;
import com.preemptive.moneybank.ui.login.LoginActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ACCOUNTS_URL = "https://10.0.2.2:5001/api/account";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoginRepository loginRepository = LoginRepository.getInstance();
        if (!loginRepository.isLoggedIn()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return;
        }
        LoggedInUser user = loginRepository.getLoggedInUser();
        String welcome = "Welcome, " + user.getDisplayName() + "!";
        Toast welcomeToast = Toast.makeText(this, welcome, Toast.LENGTH_SHORT);
        welcomeToast.show();
        RequestQueueSingleton queue = RequestQueueSingleton.getInstance(getApplicationContext());
        final RecyclerView rv_accounts = findViewById(R.id.rv_accounts);
        BearerTokenAuthJsonObjectRequest request = new BearerTokenAuthJsonObjectRequest(Request.Method.GET, ACCOUNTS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Account> accounts = convertAccounts(response.getJSONArray("accounts"));
                    AccountAdapter adapter = new AccountAdapter(accounts);

                    rv_accounts.setAdapter(adapter);
                    rv_accounts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } catch (JSONException exc) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error parsing accounts", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error loading accounts", Toast.LENGTH_SHORT);
                toast.show();
            }
        }, user.getAuthToken());
        queue.addToRequestQueue(request);
    }

    private List<Account> convertAccounts(JSONArray array) throws JSONException {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject accountJsonObject = array.getJSONObject(i);
            accounts.add(new Account(accountJsonObject.getString("accountNumber"), accountJsonObject.getDouble("balance")));
        }
        return accounts;
    }
}
