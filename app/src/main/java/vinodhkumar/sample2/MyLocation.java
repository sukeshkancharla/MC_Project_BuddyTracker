package vinodhkumar.sample2;

import android.app.Dialog;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 21-Oct-16.
 */

public class MyLocation extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mgoogleMap;
    private TextView textView;
    private GoogleApiClient googleApiClient;
    private Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isGooglePlayServicesAvailable()) {
            textView=(TextView)findViewById(R.id.text1);
            Toast.makeText(this, "perfect", Toast.LENGTH_LONG).show();
            setContentView(R.layout.my_location);
            initmap();
        } else {
            //No googlemap layout
        }

    }

    /*public void geoLocate(View view) throws IOException {

        if (location != null) {
            //Toast.makeText(this,"ingeoloacte",Toast.LENGTH_LONG).show();
            Geocoder geocoder = new Geocoder(this);
            //Toast.makeText(this,location,Toast.LENGTH_LONG).show();
            List<Address> list;
            Address address;
            try {
                list = geocoder.getFromLocationName(location, 1);
                address = list.get(0);
                String locality = address.getLocality();
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                //Toast.makeText(this,lat+" "+lng+"ahsf",Toast.LENGTH_LONG).show();
                gotoLocationZoom(lat, lng, 15);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }*/
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,"itemselected",Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.item1:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.item2:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.item3:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.item4:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.item5:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    public void initmap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Google playservices is not available", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {           // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,"Nopermission",Toast.LENGTH_LONG).show();
            return;
        }
        //Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
        mgoogleMap.setMyLocationEnabled(true);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

    }

    public void gotoLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mgoogleMap.moveCamera(update);
    }

    public void gotoLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mgoogleMap.moveCamera(update);
    }

    LocationRequest locationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Toast.makeText(this,"onconnected",Toast.LENGTH_LONG).show();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        //Toast.makeText(this,"requested updates",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location){
        //Toast.makeText(this,"onlocationchanged",Toast.LENGTH_LONG).show();
        if(location!=null){
            //Toast.makeText(this,location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_LONG);
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(ll,15);
            mgoogleMap.animateCamera(cameraUpdate);
            String strAdd = "";

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");
                    if(marker!=null){
                        marker.remove();
                    }
                    MarkerOptions options=new MarkerOptions()
                            .title(returnedAddress.getLocality())
                            .position(new LatLng(location.getLatitude(),location.getLongitude()));
                    marker=mgoogleMap.addMarker(options);

                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    strAdd = strReturnedAddress.toString();
                    //    Log.w("My Current loction address", "" + strReturnedAddress.toString());
                } else {
                    //  Log.w("My Current loction address", "No Address returned!");
                }
                textView=(TextView)findViewById(R.id.text1);
                textView.setText(strAdd);
            } catch (Exception e) {
                e.printStackTrace();
                //   Log.w("My Current loction address", "Canont get Address!");

            }
        }
        else{

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
