package com.example.myapplication.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.data.Task;
import com.xwray.groupie.GroupAdapter;

import java.util.List;

public class TasksFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView taskRecyclerView;

    private List<Task> taskList;

    private GroupAdapter groupAdapter = new GroupAdapter();

    public TasksFragment(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        taskRecyclerView.setAdapter(groupAdapter);
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        for (Task task : taskList) {
            groupAdapter.add(new TaskItem(task));
        }
    }
}
