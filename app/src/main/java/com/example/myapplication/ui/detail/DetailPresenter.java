package com.example.myapplication.ui.detail;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.example.myapplication.data.Project;
import com.example.myapplication.usecases.ProjectsUseCase;
import com.example.myapplication.data.Task;
import com.example.myapplication.ui.BasePresenter;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class DetailPresenter extends BasePresenter<DetailPresenter.View> {
    public Project project;
    private ProjectsUseCase projectsUseCase;

    @Inject
    public DetailPresenter(ProjectsUseCase projectsUseCase) {
        this.projectsUseCase = projectsUseCase;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    protected Disposable onBind(View view) {
        checkNotNull(project, "project is null. Set model before binding.");
        return subscribeToShowProject(view);
    }

    private Disposable subscribeToShowProject(DetailPresenter.View view) {
        return projectsUseCase.getTasks(project.getId())
                .flatMapCompletable(tasks -> view.showProject(project, tasks))
                .subscribe();
    }

    public interface View extends BasePresenter.View {
        Completable showProject(Project project, List<Task> tasks);
    }
}
