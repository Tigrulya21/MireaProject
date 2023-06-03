package ru.mirea.komaristaya.mireaproject;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import ru.mirea.komaristaya.mireaproject.databinding.FragmentDatchikBinding;
import ru.mirea.komaristaya.mireaproject.databinding.FragmentYandexMapBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YandexMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YandexMap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentYandexMapBinding binding;
    private MapView mapView = null;
    private	static final int REQUEST_CODE_PERMISSION = 200;
    private boolean isWork = false;
    private MyLocationNewOverlay locationNewOverlay;

    public YandexMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YandexMap.
     */
    // TODO: Rename and change types and number of parameters
    public static YandexMap newInstance(String param1, String param2) {
        YandexMap fragment = new YandexMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentYandexMapBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        Intent intent = new Intent(getContext(), MapTwo.class);
        startActivity(intent);

        return v;
    }
}
