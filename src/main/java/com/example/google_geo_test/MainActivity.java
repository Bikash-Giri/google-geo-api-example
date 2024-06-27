package com.example.google_geo_test;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.google_geo_test.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ApiService apiService;
    private ArrayAdapter<String> adapter;

    private ListView listView;
    private List<String> dataList;
    private Button button1;

    private ProgressBar progressBar;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Map<String, Object>> cellTowers = getCellTowers(this);
        List<Map<String, Object>> wifiAccessPoints = getWifiAccessPoints(this);


        setSupportActionBar(binding.toolbar);
        listView = findViewById(R.id.listView);

        // Initialize dataList and adapter
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://www.googleapis.com/geolocation/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                apiService = retrofit.create(ApiService.class);
//                makeGetRequest();
//            }
//        });

        // Set up button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                adapter.notifyDataSetChanged();

            }
        });

        // Set up button click listeners
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a logging interceptor
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Set desired log level

                // Add the logging interceptor to OkHttpClient
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(loggingInterceptor); // Add logging interceptor

                // Handle button 1 click
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.googleapis.com/geolocation/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build()) // Set
                        .build();

                apiService = retrofit.create(ApiService.class);
                makeGetRequest();
            }
        });
    }

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

//    @Override
//    public boolean onSupportNavigateUp() {
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
     public List<Map<String, Object>> getCellTowers(Context context) {
         List<Map<String, Object>> cellTowers = new ArrayList<>();

         if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             // Request permissions if not granted
             ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
         }

         TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
         List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();

         for (CellInfo cellInfo : cellInfos) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                 Map<String, Object> cellTower = new HashMap<>();
                 final CellIdentity cellType =   cellInfo.getCellIdentity();
                 System.out.println(cellType);


//                 cellTower.put("cellId", cellType.getCellIdentity().getCid());
//                 cellTower.put("locationAreaCode", cellInfoGsm.getCellIdentity().getLac());
//                 cellTower.put("mobileCountryCode", cellInfoGsm.getCellIdentity().getMcc());
//                 cellTower.put("mobileNetworkCode", cellInfoGsm.getCellIdentity().getMnc());
//                 cellTower.put("signalStrength", cellInfoGsm.getCellSignalStrength().getDbm());
//                 cellTowers.add(cellTower);
             }
             if (cellInfo instanceof CellInfoGsm) {
                 CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                 Map<String, Object> cellTower = new HashMap<>();
                 cellTower.put("cellId", cellInfoGsm.getCellIdentity().getCid());
                 cellTower.put("locationAreaCode", cellInfoGsm.getCellIdentity().getLac());
                 cellTower.put("mobileCountryCode", cellInfoGsm.getCellIdentity().getMcc());
                 cellTower.put("mobileNetworkCode", cellInfoGsm.getCellIdentity().getMnc());
                 cellTower.put("signalStrength", cellInfoGsm.getCellSignalStrength().getDbm());
                 cellTowers.add(cellTower);
             } else if (cellInfo instanceof CellInfoLte) {
                 CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                 Map<String, Object> cellTower = new HashMap<>();
                 cellTower.put("cellId", cellInfoLte.getCellIdentity().getCi());
                 cellTower.put("locationAreaCode", cellInfoLte.getCellIdentity().getTac());
                 cellTower.put("mobileCountryCode", cellInfoLte.getCellIdentity().getMcc());
                 cellTower.put("mobileNetworkCode", cellInfoLte.getCellIdentity().getMnc());
                 cellTower.put("signalStrength", cellInfoLte.getCellSignalStrength().getDbm());

                 cellTowers.add(cellTower);
             }
         }
         return cellTowers;
     }

     public List<Map<String, Object>> getWifiAccessPoints(Context context) {
         List<Map<String, Object>> wifiAccessPoints = new ArrayList<>();

         WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

         if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             // Request permissions if not granted
             ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
         }

         List<ScanResult> scanResults = wifiManager.getScanResults();
         for (ScanResult scanResult : scanResults) {
             Map<String, Object> wifiAccessPoint = new HashMap<>();
             wifiAccessPoint.put("macAddress", scanResult.BSSID);
             wifiAccessPoint.put("signalStrength", scanResult.level);
             wifiAccessPoints.add(wifiAccessPoint);
         }
         return wifiAccessPoints;
     }

    private void makeGetRequest() {
        // Replace with actual values for your query parameters
        String key = "AIzaSyCA2xSJdPpqoTf48A9Bdp_aQiHULDonges";

        // Create request body object
        PostRequestBody requestBody = new PostRequestBody(
                429,
                1,
                "lte",
                "Ncell",
                true,
                getCellTowers(this),
                getWifiAccessPoints(this)
        );
        progressBar.setVisibility(View.VISIBLE);
        // Make the API call
        Call<LocationResponseModel> call = apiService.requestLocation(key,requestBody);
        call.enqueue(new Callback<LocationResponseModel>() {
            @Override
            public void onResponse(Call<LocationResponseModel> call, Response<LocationResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    // Handle successful response
                    // Convert JSON string to LocationResponseModel

                    if (response.body() != null) {

                        Log.d("MainActivity", "Converted Response to Model: " + response.body().toString());
                        final double latitude = response.body().getLocation().getLat();
                        final double longtitude = response.body().getLocation().getLng();
                        final double accuracy = response.body().getAccuracy();

                        String message = "Lat: " + latitude + ", Lng: " + longtitude + "accuracy:" + accuracy;
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        dataList.add(message);
                        adapter.notifyDataSetChanged();
                    } else {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(MainActivity.this, "Failed to convert data", Toast.LENGTH_LONG).show();

                    }
                    // Process the data here
                } else {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "Failed to get location", Toast.LENGTH_LONG).show();

                    // Handle error response
                }
            }

            @Override
            public void onFailure(Call<LocationResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "Failed to get location", Toast.LENGTH_LONG).show();

            }


        });
    }
}

