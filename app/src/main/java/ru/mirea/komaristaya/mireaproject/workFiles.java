package ru.mirea.komaristaya.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.komaristaya.mireaproject.databinding.FragmentAudioRecordBinding;
import ru.mirea.komaristaya.mireaproject.databinding.FragmentWorkFilesBinding;


public class workFiles extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentWorkFilesBinding binding;

    private String mParam1;
    private String mParam2;

    public workFiles() {
        // Required empty public constructor
    }

    public static workFiles newInstance(String param1, String param2) {
        workFiles fragment = new workFiles();
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
        binding = FragmentWorkFilesBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        binding.SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExternalStorageWritable();
                writeFileToExternalStorage();
            }
        });
        binding.LoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExternalStorageReadable();
                readFileFromExternalStorage();
            }
        });

        return v;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void writeFileToExternalStorage()	{
        File path =	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        String fileName = String.valueOf(binding.File.getText());
        File file =	new	File(path,fileName + ".txt");
        try	{
            FileOutputStream fileOutputStream =	new	FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output =	new	OutputStreamWriter(fileOutputStream);
            //	Запись строки в файл
            output.write(binding.Content.getText().toString());
            //	Закрытие потока записи
            output.close();
        }	catch (IOException e)	{
            Log.w("ExternalStorage","Error	writing	" +	file, e);
        }
    }


    public void readFileFromExternalStorage()	{
        File path =	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        String fileName = String.valueOf(binding.File.getText());
        File file =	new	File(path,fileName + ".txt");
        try	{
            FileInputStream fileInputStream	= new FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader	= new InputStreamReader
                    (fileInputStream, StandardCharsets.UTF_8);
            List<String> lines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            Log.w("ExternalStorage", String.format("Read from file %s successful", lines.toString()));
            binding.Content.setText(lines.toString());
        }	catch (Exception e)	{
            Log.w("ExternalStorage", String.format("Read from file %s failed", e.getMessage()));
        }
    }
}