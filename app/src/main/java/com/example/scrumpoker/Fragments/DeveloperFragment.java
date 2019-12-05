package com.example.scrumpoker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.scrumpoker.Utils.NameDialog;
import com.example.scrumpoker.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.Fragment;

public class DeveloperFragment extends Fragment {
    private FirebaseAuth mAuth;
    Context actualContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.developer_fragment, container, false);
        actualContext = getContext();
        if(savedInstanceState != null){
            return view;
        }
        mAuth = FirebaseAuth.getInstance();
        Button b = view.findViewById(R.id.pokerpage_dev_logout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        b = view.findViewById(R.id.pokerpage_dev_join);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinSession();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void joinSession() {
        NameDialog dialog = new NameDialog();
        Bundle args = new Bundle();
        args.putString("action", "Join Session");
        dialog.setArguments(args);

        dialog.setTargetFragment(DeveloperFragment.this,2);
        dialog.show(getFragmentManager(),"nameDialog");
    }


    private void logout() {
        mAuth.signOut();
        getActivity().finish();
    }
}

