package ru.mirea.komaristaya.mireaproject;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.komaristaya.mireaproject.databinding.ActivityMapTwoBinding;

public class MapTwo extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMapTwoBinding binding;
    private MapView mapView = null;
    private	static final int REQUEST_CODE_PERMISSION = 200;
    private boolean isWork = false;
    private MyLocationNewOverlay locationNewOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        binding = ActivityMapTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapView = binding.mapView;

        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapController.setCenter(startPoint);

        int	CoarseLocationPermissionStatus = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int	FineLocation = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);


        if	(CoarseLocationPermissionStatus	== PackageManager.PERMISSION_GRANTED && FineLocation
                ==	PackageManager.PERMISSION_GRANTED)	{
            isWork = true;
            Toast.makeText(this, "Разрешения получены", Toast.LENGTH_SHORT).show();
        }	else	{
            ActivityCompat.requestPermissions(this, new	String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
            Toast.makeText(this, "Нет разрешений", Toast.LENGTH_SHORT).show();
        }


        interIcon("CityWave - стационарная волна для сёрфа. Время работы: 7:00 - 23:00",
                R.drawable.waves, 55.683326, 37.780922);
        interIcon("МaxBakery - тут самые вкуснецкие тортики ;). Время работы 7:00 - 22:00",
                R.drawable.cake,  55.73884, 37.54648);
        interIcon("Ленинградский вокзал. Устройте себе мини покатушки!",
                R.drawable.city,  55.77625, 37.655306);


        //oпределение местоположения устройства
        locationNewOverlay = new MyLocationNewOverlay(new
                GpsMyLocationProvider(getApplicationContext()),mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.locationNewOverlay);
        //компас
        CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), new
                InternalCompassOrientationProvider(getApplicationContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);
        // oтображение метрической шкалы масштаба
        final Context context = this.getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);
        // Добавление маркера на карту и обработка нажатия
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(55.794229, 37.700772));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getApplicationContext(),"Click",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);
        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker.setTitle("Title");
    }

    public void interIcon(String text, Integer drawable, Double lat, Double lon){
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(lat, lon));
        marker.setIcon(ResourcesCompat.getDrawable(getResources(), drawable, null));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(MapTwo.this, text, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);
    }
    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }
}