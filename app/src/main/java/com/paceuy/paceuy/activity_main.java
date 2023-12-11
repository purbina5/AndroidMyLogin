package com.paceuy.paceuy;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import android.Manifest;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;



public class activity_main extends AppCompatActivity {
    private MapView mapView;
    private MyLocationNewOverlay myLocationOverlay;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Marker MiMarker;
    IMapController mapController;
    Polyline polyline = new Polyline();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mapView = findViewById(R.id.mapView);
        Marker pos = new Marker(mapView);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);


        double santiagoLatitude = -33.44915;
        double santiagoLongitude = -70.66242;


        double micasaLatitude = -33.538048;
        double micasaLongitude = -70.7526656;

        GeoPoint santiagoPoint = new GeoPoint(santiagoLatitude, santiagoLongitude);
        GeoPoint valparaisoPoint = new GeoPoint(micasaLatitude, micasaLongitude);

        Marker santiagoMarker = new Marker(mapView);
        santiagoMarker.setPosition(santiagoPoint);
        santiagoMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        santiagoMarker.setTitle("Santiago, Chile");
        santiagoMarker.setSnippet("Santiago de Chile");


        mapView.getOverlays().add(santiagoMarker);

        if (checkLocationPermission()) {
            requestLocation();
        }


        mapView.getOverlayManager().add(polyline);

        double distance = CalcularDistancia.CalcularDistancia(santiagoPoint, valparaisoPoint);
        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setText("Distancia entre mi casa y la Universidad: " + String.format("%.2f", distance) + " km");

        mapController = mapView.getController();
        mapController.setCenter(santiagoPoint);
        mapController.setZoom(14);

    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            }
        }
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            GeoPoint MiPoint = new GeoPoint(latitude,longitude);
                            MiMarker = new Marker(mapView);
                            MiMarker.setPosition(MiPoint);
                            MiMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            MiMarker.setTitle("Mi Ubicación");
                            MiMarker.setSnippet("Usted esta aqui");
                            mapView.getOverlays().add(MiMarker);
                            mapController.setCenter(MiPoint);
                            mapController.setZoom(18);
                            TextView distanceTextView = findViewById(R.id.distanceTextView);
                            double santiagoLatitude = -33.44915;
                            double santiagoLongitude = -70.66242;

                            GeoPoint santiagoPoint1 = new GeoPoint(santiagoLatitude, santiagoLongitude);
                            double distance = CalcularDistancia.CalcularDistancia(santiagoPoint1,MiPoint);
                            distanceTextView.setText("Distancia entre su casa y la universidad " + String.format("%.2f", distance) + " km");
                            polyline.addPoint(santiagoPoint1);
                            polyline.addPoint(MiPoint);
                            polyline.setColor(0xFF0000FF);
                            polyline.setWidth(5);

                        }
                    }
                });

    }
    public void VolverAinicio(View view) {
        Intent intent = new Intent(this,inicio.class);
        startActivity(intent);
    }
}

/*public class activity_main extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private  MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapView= findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // If permission is already granted, request location updates
            requestLocationUpdates();
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        requestLocationUpdates();
        }


    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            GeoPoint santiagoPoint = new GeoPoint(latitude,longitude);
                            Marker santiagoMarker = new Marker(mapView);
                            santiagoMarker.setPosition(santiagoPoint);
                            santiagoMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            santiagoMarker.setTitle("Santiago, Chile");
                            santiagoMarker.setSnippet("Capital de Chile");
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, request location updates
                requestLocationUpdates();
            }
        }
    }
}*/
    /*private MapView mapView;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // If permission is already granted, get the last known location
            setupMapWithLocation();
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void setupMapWithLocation() {
        mapView.getController().setZoom(14);

        // Use last known location
        if (lastKnownLocation != null) {
            GeoPoint currentLocationPoint = new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mapView.getController().setCenter(currentLocationPoint);
        }

        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        //Coordenadas de Santiago, Chile
        double santiagoLatitude = -33.4489;
        double santiagoLongitude = -70.6693;

        //Coordenadas de Valparaíso, Chile
        double valparaisoLatitude = -33.4989778;
        double valparaisoLongitude = -70.6180072;

        //Crear objetos GeoPoint para los marcadores
        GeoPoint santiagoPoint = new GeoPoint(santiagoLatitude, santiagoLongitude);
        GeoPoint valparaisoPoint = new GeoPoint(valparaisoLatitude, valparaisoLongitude);

        //Crear marcadores con títulos y descripciones
        Marker santiagoMarker = new Marker(mapView);
        santiagoMarker.setPosition(santiagoPoint);
        santiagoMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        santiagoMarker.setTitle("Santiago, Chile");
        santiagoMarker.setSnippet("Capital de Chile");
        Marker valparaisoMarker = new Marker(mapView);
        valparaisoMarker.setPosition(valparaisoPoint);
        valparaisoMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        valparaisoMarker.setTitle("Valparaíso, Chile");
        valparaisoMarker.setSnippet("Puerto principal de Chile");

        //Agregar marcadores al mapa
        mapView.getOverlays().add(santiagoMarker);
        mapView.getOverlays().add(valparaisoMarker);

        //Crear y agregar la línea entre Santiago y Valparaíso
        Polyline polyline = new Polyline();
        polyline.addPoint(santiagoPoint);
        polyline.addPoint(valparaisoPoint);
        polyline.setColor(0xFF0000FF); // Color de la línea (azul en formato ARGB)
        polyline.setWidth(5); // Ancho de la línea en píxeles

        //Agrega la Linea al Mapa
        mapView.getOverlayManager().add(polyline);

        //Calcular la distancia entre Santiago y Valparaíso
        double distance = CalcularDistancia.CalcularDistancia(santiagoPoint, valparaisoPoint);
        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setText("Distancia entre Santiago y Valparaíso: " +
                String.format("%.2f", distance) + " km");
        // Centrar el mapa en Santiago, Chile

        IMapController mapController = mapView.getController();
        mapController.setCenter(santiagoPoint);
        mapController.setZoom(14); // Puedes ajustar el nivel de zoom según sea necesario
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the last known location
                setupMapWithLocation();
            }
        }
    }
}*/
        /*MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        //Coordenadas de Santiago, Chile
        double santiagoLatitude = -33.4489;
        double santiagoLongitude = -70.6693;

        //Coordenadas de Valparaíso, Chile
        double valparaisoLatitude = -33.4989778;
        double valparaisoLongitude = -70.6180072;

        //Crear objetos GeoPoint para los marcadores
        GeoPoint santiagoPoint = new GeoPoint(santiagoLatitude, santiagoLongitude);
        GeoPoint valparaisoPoint = new GeoPoint(valparaisoLatitude, valparaisoLongitude);

        Crear marcadores con títulos y descripciones
        Marker santiagoMarker = new Marker(mapView);
        santiagoMarker.setPosition(santiagoPoint);
        santiagoMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        santiagoMarker.setTitle("Santiago, Chile");
        santiagoMarker.setSnippet("Capital de Chile");
        Marker valparaisoMarker = new Marker(mapView);
        valparaisoMarker.setPosition(valparaisoPoint);
        valparaisoMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        valparaisoMarker.setTitle("Valparaíso, Chile");
        valparaisoMarker.setSnippet("Puerto principal de Chile");

        //Agregar marcadores al mapa
        mapView.getOverlays().add(santiagoMarker);
        mapView.getOverlays().add(valparaisoMarker);

        //Crear y agregar la línea entre Santiago y Valparaíso
        Polyline polyline = new Polyline();
        polyline.addPoint(santiagoPoint);
        polyline.addPoint(valparaisoPoint);
        polyline.setColor(0xFF0000FF); // Color de la línea (azul en formato ARGB)
        polyline.setWidth(5); // Ancho de la línea en píxeles

        //Agrega la Linea al Mapa
        mapView.getOverlayManager().add(polyline);

        //Calcular la distancia entre Santiago y Valparaíso
        double distance = CalcularDistancia.CalcularDistancia(santiagoPoint, valparaisoPoint);
        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setText("Distancia entre Santiago y Valparaíso: " +
                String.format("%.2f", distance) + " km");
        // Centrar el mapa en Santiago, Chile

        IMapController mapController = mapView.getController();
        mapController.setCenter(santiagoPoint);
        mapController.setZoom(14); // Puedes ajustar el nivel de zoom según sea necesario*/
    /*}
}*/