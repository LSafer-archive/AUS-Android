package lsafer.aus.view.i;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.aus.view.ii.ChainIIView;
import lsafer.services.annotation.Invokable;
import lsafer.services.io.Chain;
import lsafer.services.io.Profile;
import lsafer.services.util.Arguments;
import lsafer.services.util.Service;
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
        this.initialize(context, null);
        this.refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_i_profile, parent);

        view.setOnClickListener(v -> this.listener.onProfileClick(this));
        view.setOnLongClickListener(v -> this.listener.onProfileLongClick(this));

        view.<TextView>findViewById(R.id.name).setText(this.profile.remote().getName());
        view.<Button>findViewById(R.id.liver).setOnClickListener(v -> {
            if (this.profile.temp.method.equals(Invokable.start))
                this.profile.callAll(this.getContext(), 0, Service.ACTION_SHUTDOWN, Invokable.stop, new Arguments());
            else
                this.profile.callAll(this.getContext(), 0, Service.ACTION_INVOKE, Invokable.start, new Arguments());

            this.refresh();
        });

        return view;
    }

    @Override
    public void refresh() {
        this.profile.<Profile>reset().load();

        LinearLayout chains_linear = this.getView().findViewById(R.id.chains_linear);

        chains_linear.removeAllViews();

        this.profile.map(String.class, Chain.class).forEach((name, chain) -> chains_linear.addView(new ChainIIView(this.getContext(), chain).getView()));

        this.getView().<Button>findViewById(R.id.liver).setText(this.profile.temp.method);

        this.getView().findViewById(R.id.banner).setBackground(this.getContext().getDrawable(
                this.profile.temp.method.equals(Invokable.start) ? R.drawable.kroov_round_good : R.drawable.kroov_round_silk));
    }

    /**
     * get the targeted {@link Profile}.
     *
     * @return the current targeted task
     */
    public Profile getProfile() {
        return this.profile;
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
            return false;
        }
    }

}
