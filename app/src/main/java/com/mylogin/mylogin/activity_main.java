package com.mylogin.mylogin;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.Manifest;





public class activity_main extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        private MapView mapView;
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

        // Rest of your code
        // ...
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
}
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