package com.example.scrumpoker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.scrumpoker.Utils.NameDialog;
import com.example.scrumpoker.Utils.SessionsDialog;
import com.example.scrumpoker.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.Fragment;

public class MasterFragment extends Fragment {
    private FirebaseAuth mAuth;
    Context actualContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.master_fragment, container, false);
        actualContext = getContext();
        if(savedInstanceState != null){
            return view;
        }
        mAuth = FirebaseAuth.getInstance();
        Button b = view.findViewById(R.id.pokerpage_master_logout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        b = view.findViewById(R.id.pokerpage_master_create);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSession();
            }
        });
        b = view.findViewById(R.id.pokerpage_master_edit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSession();
            }
        });
        b = view.findViewById(R.id.pokerpage_master_start);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSession();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void startSession() {
        SessionsDialog dialog = new SessionsDialog();
        Bundle args = new Bundle();
        args.putString("action", "Start Session");
        dialog.setArguments(args);
        dialog.setTargetFragment(MasterFragment.this,1);
        dialog.show(getFragmentManager(),"sessionDialog");
    }

    private void editSession() {
        SessionsDialog dialog = new SessionsDialog();
        Bundle args = new Bundle();
        args.putString("action", "Edit Session");
        dialog.setArguments(args);
        dialog.setTargetFragment(MasterFragment.this,1);
        dialog.show(getFragmentManager(),"sessionDialog");
    }

    private void createSession(){
        NameDialog dialog = new NameDialog();
        Bundle args = new Bundle();
        args.putString("action", "Create Session");
        dialog.setArguments(args);

        dialog.setTargetFragment(MasterFragment.this,1);
        dialog.show(getFragmentManager(),"nameDialog");
    }

    private void logout() {
        mAuth.signOut();
        getActivity().finish();
    }
}
