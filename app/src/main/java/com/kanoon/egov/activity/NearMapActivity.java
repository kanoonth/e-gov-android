package com.kanoon.egov.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.kanoon.egov.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NearMapActivity extends Activity implements OnMarkerClickListener{
    GoogleMap mMap;
    Marker marker;
    private double latitude, longitude;
    private Spinner distanceSpinner;
    private Spinner lineSpinner;
    private LatLng curPos;
    private Circle circle;
    private LocationManager lm;

    private List<String> namePlace;
    private List<String> lat;
    private List<String> log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_maps);
        namePlace = new ArrayList<String>();
        lat = new ArrayList<String>();
        log = new ArrayList<String>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            namePlace = Arrays.asList(extras.getStringArray("namePlace"));
            lat = Arrays.asList(extras.getStringArray("latitude"));
            log = Arrays.asList(extras.getStringArray("longitude"));
        }
//        DAO dao = (DAO) intent.getSerializableExtra("MyClass");
        createMapView();
        setUpMap();
        Log.d("aaaaaaaa",namePlace.size()+"");
        for(int i=0;i<namePlace.size();i++){
            double latitude = Double.parseDouble(lat.get(i));
            double longitude = Double.parseDouble(log.get(i));
            Log.d("zzzzz",latitude+"");
            Log.d("zzzzzzzzzz",longitude+"");
            addMarker(new LatLng(latitude,longitude),namePlace.get(i));
        }
        addMarker(new LatLng(13.851095, 100.567791),("Kaset"));
        addMarker(new LatLng(13.640073, 100.555517),("Wat samean"));
        zoom(new LatLng(13.640073, 100.555517),new LatLng(latitude,longitude));
      }

    private void createMapView() {
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if (null == mMap) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if (null == mMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception) {
            Log.e("mapApp", exception.toString());
        }

        mMap.setOnMarkerClickListener(this);
    }

    /**
     * Adds a marker to the map
     */
//    private void addMarker() {
//
//        /** Make sure that the map has been initialised **/
//        if (null != mMap) {
//            mMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(lat, lng))
//                            .title("Marker")
//                            .draggable(true)
//            );
//        }
//    }

//    LocationListener listener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location loc) {
//            curPos = new LatLng(loc.getLatitude(), loc.getLongitude());
//            latitude = loc.getLatitude();
//            longitude = loc.getLongitude();
//
//            if (marker != null) {
//                marker.setPosition(new LatLng(latitude, longitude));
//                circle.setCenter(new LatLng(latitude, longitude));
//                circle.setRadius(Double.parseDouble(distanceSpinner.getSelectedItem().toString()));
//            } else {
//                marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
//                marker.setAnchor(0.5f, 0.5f);
////                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation));
//                circle = mMap.addCircle(new CircleOptions()
//                                .center(new LatLng(latitude, longitude))
//                                .radius(Double.parseDouble(distanceSpinner.getSelectedItem().toString()))
//                                .strokeColor(0x996C9FF0)
//                                .fillColor(0x80CCD8EB)
//                                .strokeWidth(5)
//                                .visible(false)
//
//                );
////                moveToCurrent();
//            }
//
//        }
//
//    };

//    private void setUpMap() {
//        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        boolean isNetwork =
//                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        boolean isGPS =
//                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if (isNetwork) {
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, (android.location.LocationListener) listener);
//            Location loc = lm.getLastKnownLocation(
//                    LocationManager.NETWORK_PROVIDER);
//            if (loc != null) {
//                lat = loc.getLatitude();
//                lng = loc.getLongitude();
//            }
//        }
//    }

    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        try {
            // Get latitude of the current location
            latitude = myLocation.getLatitude();

            // Get longitude of the current location
            longitude = myLocation.getLongitude();
            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }catch (NullPointerException e){
            //Can't find location service
            //Magic location
            //In some device but still can click button of your location service on map
            latitude = 13.851095;
            longitude = 100.567791;
            LatLng latLng = new LatLng(13.851095, 100.567791);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        // Show the current location in Google Map


        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
     }

    private void addMarker(LatLng latLng,String nameOfPlace){
        Marker markerPlace = mMap.addMarker(new MarkerOptions().position(latLng).title(nameOfPlace));

    }

    private void zoom(LatLng userLocation, LatLng targetLocation){
//        LatLngBounds(LatLng southwest, LatLng northeast)

        LatLngBounds latBound = new LatLngBounds(userLocation,targetLocation);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latBound, 1));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latBound.getCenter(), 12));
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        Log.d("www","!Current"+latitude+" , "+longitude);
        Log.d("sss","!!!!!!!!Message"+latBound+"");
// Set the camera to the greatest possible zoom level that includes the

    }

    /**
     * handle marker click event
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        LocationActivity.idPlace = marker.getId();
        Intent newActivity = new Intent(NearMapActivity.this, TransactionDetailActivity.class);
        newActivity.putExtra("selectedPlace",marker.getTitle());
        startActivity(newActivity);
//        if(marker.equals(marker_1)){
//            Log.w("Click", "test");
//            return true;
//        }
        return true;
    }

}

