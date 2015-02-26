package com.u.juthamas.egoverment;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class NearMapActivity extends Activity {
    GoogleMap googleMap;
    Marker marker;
    private double lat, lng;
    private Spinner distanceSpinner;
    private Spinner lineSpinner;
    private LatLng curPos;
    private Circle circle;
    private LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_maps);
        createMapView();
        addMarker();
    }

    private void createMapView() {
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if (null == googleMap) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if (null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception) {
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker() {

        /** Make sure that the map has been initialised **/
        if (null != googleMap) {
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title("Marker")
                            .draggable(true)
            );
        }
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            curPos = new LatLng(loc.getLatitude(), loc.getLongitude());
            lat = loc.getLatitude();
            lng = loc.getLongitude();

            if (marker != null) {
                marker.setPosition(new LatLng(lat, lng));
                circle.setCenter(new LatLng(lat, lng));
                circle.setRadius(Double.parseDouble(distanceSpinner.getSelectedItem().toString()));
            } else {
                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
                marker.setAnchor(0.5f, 0.5f);
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation));
                circle = googleMap.addCircle(new CircleOptions()
                                .center(new LatLng(lat, lng))
                                .radius(Double.parseDouble(distanceSpinner.getSelectedItem().toString()))
                                .strokeColor(0x996C9FF0)
                                .fillColor(0x80CCD8EB)
                                .strokeWidth(5)
                                .visible(false)

                );
//                moveToCurrent();
            }

        }

    };

    private void setUpMap() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isNetwork =
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPS =
                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isNetwork) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, (android.location.LocationListener) listener);
            Location loc = lm.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }
    }
}

