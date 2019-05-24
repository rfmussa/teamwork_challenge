package com.example.myapplication.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.data.Project;

public class InfoFragment extends Fragment {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.category)
    TextView category;
    private Project project;

    InfoFragment(Project project) {
        this.project = project;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        description.setText(project.getDescription());
        //company.setText(project.getCompany())
        category.setText(project.getDescription());
     //   status.setText(project.getStatus());
    }
}
