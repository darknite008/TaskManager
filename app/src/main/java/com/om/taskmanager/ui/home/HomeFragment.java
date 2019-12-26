package com.om.taskmanager.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.om.taskmanager.MainActivity;
import com.om.taskmanager.R;

public class HomeFragment extends Fragment {
    private TextView textView;
    private Button button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView=root.findViewById(R.id.txtview);
        button=root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                Toast.makeText(getActivity(), "Logged Out!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null){
            String token = bundle.getString("token");
            String username = bundle.getString("username");
            textView.setText("Hey! "+username+"\n Ur token is "+token);

        }
        return root;
    }
}