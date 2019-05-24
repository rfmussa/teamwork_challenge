package com.example.myapplication.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.data.Project;
import com.example.myapplication.data.Task;
import com.example.myapplication.di.AppComponentProvider;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment implements DetailPresenter.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.logo)
    ImageView toolbarImage;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Inject
    DetailPresenter presenter;

    private Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponentProvider component = (AppComponentProvider) (getActivity().getApplication());
        component.getComponent().build().detailFragment(this);

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move);

        this.setSharedElementEnterTransition(transition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, view);
        presenter.project = (Project) getArguments().get("project");
        disposable = presenter.bind(this);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collapsingToolbarLayout.setTitle(presenter.project.getName());

        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        tabLayout.addTab(tabLayout.newTab().setText("Tasks"));
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.setupWithViewPager(viewPager);

        Picasso.get()
                .load(presenter.project.getLogo())
                .into(toolbarImage);

        NavigationUI.setupWithNavController(toolbar, NavHostFragment.findNavController(this));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }

    @Override
    public Completable showProject(Project project, List<Task> taskList) {
        return Completable.create(emitter -> {
            Picasso.get()
                    .load(project.getLogo())
                    .into(toolbarImage);
            Log.d("GotoProject", "showProject Detail");
            Log.d("GotoProject", taskList.size() + "");

            setupViewPager(viewPager, project, taskList);
            emitter.onComplete();
        });
    }

    private void setupViewPager(ViewPager viewPager, Project project, List<Task> taskList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new TasksFragment(taskList), "Tasks");
        adapter.addFragment(new InfoFragment(project), "Info");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}