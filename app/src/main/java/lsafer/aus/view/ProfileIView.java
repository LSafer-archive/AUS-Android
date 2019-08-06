package lsafer.aus.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import lsafer.aus.R;
import lsafer.io.JSONFileStructure;
import lsafer.services.io.Profile;
import lsafer.services.io.Task;
import lsafer.services.text.Run;
import lsafer.view.Refreshable;
import lsafer.view.ViewAdapter;

/**
 * a view to display level 1
 * information of a {@link Profile}.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
public class ProfileIView extends ViewAdapter implements Refreshable {

    /**
     * the listener to invoke when specific events happened in this.
     */
    private EventListener listener;
    /**
     * targeted {@link Profile} to display information from.
     */
    private Profile profile;

    /**
     * initialize this.
     *
     * @param context  to initialize the view from
     * @param listener to invoke when specific events happened in this
     * @param profile  to display information from
     */
    public ProfileIView(Context context, EventListener listener, Profile profile) {
        this.profile = profile;
        this.listener = listener;
        this.init(context);
        this.refresh();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_i_profile, null);
        LinearLayout tasks_linear = view.findViewById(R.id.tasks_linear);

        view.setOnLongClickListener(v -> this.listener.onProfileLongClick(this));
        view.setOnClickListener(v -> this.listener.onProfileClick(this));

        view.<TextView>findViewById(R.id.name).setText(this.profile.remote().getName());
        view.findViewById(R.id.liver).setOnClickListener(v -> {
            this.profile.run(this.profile.$status.equals(Run.start) ? Run.stop : Run.start, this.getContext());
            this.refresh();
        });

        this.profile.forEach((Object key, JSONFileStructure task) ->
                tasks_linear.addView(new TaskIIView(this.getContext(), task.clone(Task.class)).getView()));

        return view;
    }

    @Override
    public void refresh() {
        this.getView().<Button>findViewById(R.id.liver).setText(this.getContext().getString(R.string.plh__status, this.profile.$status));
        this.getView().findViewById(R.id.banner).setBackground(this.getContext().getDrawable(
                this.profile.$status.equals(Run.start) ? R.drawable.round_good : R.drawable.round_silk));
    }

    /**
     * get the targeted {@link Profile}.
     *
     * @return the current targeted task
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * a listener to be invoked when specific events happened in the view adapter.
     */
    public interface EventListener {
        /**
         * get called when the view clicked.
         *
         * @param adapter the adapter that had called this method
         */
        default void onProfileClick(ProfileIView adapter) {
        }

        /**
         * get called when the view clicked a long click.
         *
         * @param adapter the adapter that had called this method
         * @return what to return to {@link View.OnLongClickListener#onLongClick(View)}
         */
        default boolean onProfileLongClick(ProfileIView adapter) {
            return true;
        }
    }

}
