package ru.mirea.komaristaya.mireaproject;

import static android.Manifest.permission.RECORD_AUDIO;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import ru.mirea.komaristaya.mireaproject.databinding.FragmentAudioRecordBinding;
import ru.mirea.komaristaya.mireaproject.databinding.FragmentDatchikBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioRecord extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentAudioRecordBinding binding;
    private	static final int REQUEST_CODE_PERMISSION = 200;
    private	final String TAG = MainActivity.class.getSimpleName();
    private	boolean	isWork;
    private	String	fileName = null;
    private Button recordButton	= null;
    private	Button	playButton = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    boolean	isStartRecording = true;
    boolean	isStartPlaying = true;
    private String recordFilePath;

    public AudioRecord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioRecord newInstance(String param1, String param2) {
        AudioRecord fragment = new AudioRecord();
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
        binding = FragmentAudioRecordBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        recordButton =	binding.recordButton;
        playButton = binding.playButton;
        playButton.setEnabled(false);
        recordFilePath = (new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "/audiorecordtest.3gp")).getAbsolutePath();

        int	audioRecordPermissionStatus	= ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO);
        int	storagePermissionStatus	= ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.
                WRITE_EXTERNAL_STORAGE);
        if	(audioRecordPermissionStatus ==	PackageManager.PERMISSION_GRANTED && storagePermissionStatus
                ==	PackageManager.PERMISSION_GRANTED)	{
            isWork = true;
        }
        else	{
            ActivityCompat.requestPermissions(getActivity(), new	String[] {RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
        recordButton.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void onClick(View v)	{
                if	(isStartRecording)	{
                    recordButton.setText("Stop	recording");
                    playButton.setEnabled(false);
                    startRecording();
                }	else	{
                    recordButton.setText("Start	recording");
                    playButton.setEnabled(true);
                    stopRecording();
                }
                isStartRecording = !isStartRecording;
            }
        });
        playButton.setOnClickListener(new	View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartPlaying) {
                    playButton.setText("Stop playing");
                    recordButton.setEnabled(false);
                    startPlaying();
                } else {
                    playButton.setText("Start playing");
                    recordButton.setEnabled(false);
                    stopPlaying();
                }
                isStartPlaying = !isStartPlaying;
            }
        });
        return v;
    }

    private	void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }
    private	void startPlaying()	{
        player = new MediaPlayer();
        try	{
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        }	catch (IOException e)	{
            Log.e(TAG, "prepare() failed");
        }
    }
    private	void stopPlaying()	{
        player.release();
        player = null;
    }
    private	void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try	{
            recorder.prepare();
        }	catch	(IOException	e)	{
            Log.e(TAG,	"prepare()	failed");
        }
        recorder.start();
    }
}