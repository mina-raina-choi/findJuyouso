package com.example.minachoi.findjuyou.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;

import com.example.minachoi.findjuyou.utils.GeoTrans;
import com.example.minachoi.findjuyou.utils.GeoTransPoint;
import com.google.android.gms.location.LocationListener;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.minachoi.findjuyou.R;
import com.example.minachoi.findjuyou.adapter.ViewPagerAdapter;
import com.example.minachoi.findjuyou.fragment.ListFragment;
import com.example.minachoi.findjuyou.fragment.MapFragment;
import com.example.minachoi.findjuyou.models.OIL;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
        implements ListFragment.SelectOil,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // fragment 관련
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    // 위치정보 관련
    Location location;
    GoogleApiClient googleApiClient;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 15000;
    private long FASTEST_INTERVAL = 5000;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissinsRejected = new ArrayList();
    private ArrayList<String> permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // permissions
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                if (permissionsToRequest.size() > 0)
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

//        LocationManager lm = map.getLocationManager();
//        Values3 pt = lm.getProjectedPoint();
//        IGPSProjection proj = new KatecProjection();
//        map.getLocationManager().setProjection(proj);

        //  현재위치 받아와서 기본값 세팅!?!
        //  옵션메뉴에서 설정페이지로 이동
        // 거리(3km, 5km), 기름종류, 브랜드(all, SK, 알뜰...등등)

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void selectOilStation(OIL oil) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(tag);
        mapFragment.showSelectedOilStation(oil);
    }


    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for (String perm : wanted) {
            if (!hasPermissions(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermissions(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPlayServices())
            Log.d("MainActivity", "onResume: Please install Google Play services.");
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else finish();

            return false;
        }
        return true;
    }

    //    googleApiClient 구현


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Google Play 서비스 연결이 성공해도 필요한 권한이 없을 수도 있습니다. 그런 만큼, API를 호출하거나 GoogleApiClient 에 연결하기 전에 필요한 권한을 가졌는지 확인해야 합니다.
        //서포트 라이브러리에 새롭게 추가된 checkSelfPermissions() 메서드를 사용해 권한을 갖고 있는지 확인할 수 있습니다. 만일 필요한 권한이 없는 경우, 다음과 같이 requestPermissions() 메서드를 이용해 사용자에게 직접 해당 권한을 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            Log.d("Main", "onConnected: lat : " + location.getLatitude() + "/ lng : " + location.getLongitude());

            GeoTransPoint currLocation = new GeoTransPoint(122.084, 37.4219983);
//            GeoTransPoint currLocation = new GeoTransPoint(location.getLongitude(), location.getLatitude());

            GeoTransPoint katecLocation = GeoTrans.convert(GeoTrans.GEO, GeoTrans.KATEC, currLocation);

            GeoTransPoint TMLocation = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, currLocation);

            Log.d("Main", "onConnected: katecLocation lat -" + katecLocation.getX() + "/ lng -" + katecLocation.getY());
            Log.d("Main", "onConnected: TMLocation lat -" + TMLocation.getX() + "/ lng -" + TMLocation.getY());
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        locationRequest = new LocationRequest();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermissions(perms)) permissinsRejected.add(perms);
                }

                if (permissinsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissinsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissinsRejected.toArray(new String[permissinsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String msg, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    public void stopLocationUpdates() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(googleApiClient, this);

            googleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            Log.d("", "Latitude : " + location.getLatitude() + "  Longitude : " + location.getLongitude());
    }
}
