package com.example.myapplication.ui;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends BasePresenter.View> {
    /**
     * Called when this Presenter is bind to the {@linkplain BasePresenter.View view}.
     * At this point this should subscribe to all streams (including input events provided by the view).
     *
     * @return A {@link Disposable} which should aggregate all stream subscriptions.
     */
    @NonNull
    protected abstract Disposable onBind(T view);

    /** Returns {@link Disposable} that should be handled by the consumer. */
    @SuppressLint("RestrictedApi")
    @NonNull
    public final Disposable bind(T view) {
        Preconditions.checkNotNull(view);

        return onBind(view);
    }


    /**
     * Contract for a 'View' in an MVP structure.
     * <p>
     * Any implementation and/or extension of the contract should <strong>only</strong> interact with the {@link BasePresenter} via it's
     * {@link BasePresenter#bind(View)} method.
     * <p>
     */
    public interface View {

    }
}
