package com.example.shopping_demo.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping_demo.Product;
import com.example.shopping_demo.ProductAdapter;
import com.example.shopping_demo.R;
import com.example.shopping_demo.helper.SQLiteHandler;
import com.example.shopping_demo.helper.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://192.168.43.99/Online%20Shopping%20Portal%20project/shopping/images1/";
    private static final String URL_PRODUCTS = " http://192.168.43.99/Online%20Shopping%20Portal%20project/shopping/Api.php";
    ArrayList<Product> productList;

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    Product product;
    ProductAdapter mProductAdapter;
    //navigationDrawer
    NavigationView mNavigationView;
    //the recyclerview
    RecyclerView recyclerView;
    private DrawerLayout drawer;

    private SQLiteHandler db;
    private SessionManager session;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productList = new ArrayList<>();

        mNavigationView = findViewById(R.id.nav_view);
        View header = mNavigationView.getHeaderView(0);
        txtName = header.findViewById(R.id.mName);
        txtEmail = header.findViewById(R.id.mEmail);


        sp = getSharedPreferences("Login", MODE_PRIVATE);
        String uName = sp.getString("name", "");
        String uEmail = sp.getString("email", "");
        txtName.setText(uName);
        txtEmail.setText(uEmail);

        //navigationDrawer implementation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();


        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
       /* mProductAdapter = new ProductAdapter(MainActivity.this,productList);
        recyclerView.setAdapter(mProductAdapter);*/
        // userprofile();
        loadProducts();



       /* //logout event click
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });*/


    }

   /* private void userprofile() {


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");
        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);
        Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                String mProductName = object.getString("productName");
                                String mProductCompany = object.getString("productCompany");
                                int mProductPrice = object.getInt("productPrice");
                                int mShippingCharge = object.getInt("shippingCharge");
                                String mImageUrl = object.getString("productImage1");
                                String Url = url + mImageUrl;

                                product = new Product(id, mProductName, mProductCompany, mProductPrice, mShippingCharge, Url);
                                productList.add(product);

                            } //creating adapter object and setting it to recyclerview
                            ProductAdapter adapter = new ProductAdapter(MainActivity.this, productList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
