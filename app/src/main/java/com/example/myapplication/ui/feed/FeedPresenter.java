package com.example.myapplication.ui.feed;

import androidx.annotation.NonNull;
import com.example.myapplication.data.Project;
import com.example.myapplication.usecases.ProjectsUseCase;
import com.example.myapplication.ui.BasePresenter;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FeedPresenter extends BasePresenter<FeedPresenter.View> {
    private final ProjectsUseCase projectsUseCase;

    public List<Project> projectList;

    @Inject
    public FeedPresenter(ProjectsUseCase projectsUseCase) {
        this.projectsUseCase = projectsUseCase;
    }


    private Disposable subscribeToLoadProjects(View view) {
        if (projectList == null) {
            return projectsUseCase.getProjects()
                    .doOnSubscribe(disposable -> view.showProgress())
                    .flatMapCompletable(projectList -> {
                        this.projectList = projectList;
                        if (projectList.isEmpty()) {
                            return view.showEmpty();
                        } else {
                            return view.showProjects(projectList);
                        }
                    })
                    .andThen(view.projectClick())
                    .doOnNext(view::goToProject)
                    .subscribe();
        } else {
            return view.showProjects(projectList)
                    .subscribe();
        }
    }

    @NonNull
    @Override
    protected Disposable onBind(View view) {
        return subscribeToLoadProjects(view);
    }

    public interface View extends BasePresenter.View {
        void showProgress();

        Completable showProjects(List<Project> projectList);

        Observable<Project> projectClick();

        Completable showEmpty();

        void goToProject(Project project);
    }
}
