package ru.mirea.komaristaya.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.security.keystore.KeyGenParameterSpec;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.komaristaya.mireaproject.databinding.FragmentAudioRecordBinding;
import ru.mirea.komaristaya.mireaproject.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private String text;
    private FragmentProfileBinding binding;

    public Profile() {
        // Required empty public constructor
    }


    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        SharedPreferences preferences = getContext().getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        String text = preferences.getString("DESC", "");

        binding.editTextDesc.setText(text);
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences_bt = getContext().getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_bt = preferences_bt.edit();
                editor_bt.putString("DESC", binding.editTextDesc.getText().toString());
                editor_bt.apply();
                Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}