package ru.mirea.komaristaya.mireaproject;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.work.Configuration;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.komaristaya.mireaproject.databinding.ActivityMainBinding;
import ru.mirea.komaristaya.mireaproject.databinding.FragmentBackgroundTaskBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BackgroundTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BackgroundTask extends Fragment {

    private FragmentBackgroundTaskBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int PermissionCode = 200;


    public BackgroundTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BackgroundTask.
     */
    // TODO: Rename and change types and number of parameters
    public static BackgroundTask newInstance(String param1, String param2) {
        BackgroundTask fragment = new BackgroundTask();
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
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = FragmentBackgroundTaskBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        if (ContextCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            Log.d(MainActivity.class.getSimpleName().toString(), "Разрешения получены");

        }
        else {
            Log.d(MainActivity.class.getSimpleName().toString(), "Нет разрешений!");

            ActivityCompat.requestPermissions(getActivity(), new String[]{POST_NOTIFICATIONS}, PermissionCode);

        }

        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkerManager.class).build();
        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enqueuing the work request
                WorkManager.initialize(getContext(), new Configuration.Builder().build());
                WorkManager.getInstance(view.getContext()).enqueue(workRequest);
            }
        });
        return v;
    }
}