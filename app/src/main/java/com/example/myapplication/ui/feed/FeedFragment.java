package com.example.myapplication.ui.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.data.Project;
import com.example.myapplication.di.AppComponentProvider;
import com.xwray.groupie.GroupAdapter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment implements FeedPresenter.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView projectRecyclerView;
    @BindView(R.id.progressView)
    ContentLoadingProgressBar progressView;
    @BindView(R.id.errorView)
    TextView errorView;

    private GroupAdapter groupAdapter = new GroupAdapter();

    private BehaviorSubject<Project> clickSubject = BehaviorSubject.create();

    private Disposable disposable;

    @SuppressWarnings("WeakerAccess")
    @Inject
    FeedPresenter presenter;

    FragmentNavigator.Extras extras;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppComponentProvider component = (AppComponentProvider) (getActivity().getApplication());
        component.getComponent().build().feedFragment(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("GotoProject", "bind");

        setupRecyclerView();
        disposable = presenter.bind(this);

    }

    @Override
    public void onDetach() {
        disposable.dispose();
        super.onDetach();
    }

    @Override
    public void showProgress() {
        errorView.setVisibility(View.INVISIBLE);
        projectRecyclerView.setVisibility(View.INVISIBLE);
        progressView.show();
    }

    @Override
    public Completable showProjects(List<Project> projectList) {
        return Completable.create(emitter ->
        {
            ArrayList<ProjectItem> itemList = new ArrayList<>();
            progressView.hide();
            for (Project project : projectList) {
                itemList.add(new ProjectItem(project));
            }
            groupAdapter.update(itemList);
            projectRecyclerView.setVisibility(View.VISIBLE);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Project> projectClick() {
        return clickSubject;
    }

    @Override
    public Completable showEmpty() {
        return Completable.create(emitter ->
        {
            progressView.hide();
            projectRecyclerView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            emitter.onComplete();
        });
    }

    @Override
    public void goToProject(Project project) {
        Log.d("GotoProject", "called");
        Bundle bundle = new Bundle();
        bundle.putParcelable("project", project);
        NavHostFragment.findNavController(this).navigate(R.id.project_click, bundle, null);

    }

    private void setupRecyclerView() {
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        projectRecyclerView.setAdapter(groupAdapter);
        projectRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        groupAdapter.setOnItemClickListener((item, view) -> {
            ProjectItem projectItem = (ProjectItem) item;
            clickSubject.onNext(projectItem.getProject());
        });
    }
}
