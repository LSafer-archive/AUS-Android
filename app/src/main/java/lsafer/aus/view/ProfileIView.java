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
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class ProfileIView extends ViewAdapter implements Refreshable {

    /**
     *
     */
    private Profile profile;

    /**
     *
     */
    private EventListener listener;

    /**
     *
     */
    private boolean running;

    /**
     * @param context
     * @param profile
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

    /**
     * @return
     */
    public Profile getProfile() {
        return profile;
    }

    @Override
    public void refresh() {
        this.getView().<Button>findViewById(R.id.liver).setText("status: "+this.profile.$status);
        this.getView().findViewById(R.id.banner).setBackground(this.getContext().getDrawable(
                this.profile.$status.equals(Run.start) ? R.drawable.round_good : R.drawable.round_silk));
    }

    /**
     *
     */
    public interface EventListener {
        /**
         * @param adapter
         * @return
         */
        default boolean onProfileLongClick(ProfileIView adapter){
            return true;
        }
        /**
         * @param adapter
         */
        default void onProfileClick(ProfileIView adapter){
        }
    }

}
