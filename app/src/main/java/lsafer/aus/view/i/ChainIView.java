package lsafer.aus.view.i;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.aus.view.ii.ProcessIIView;
import lsafer.services.io.Chain;
import lsafer.services.util.Process;
import lsafer.view.Refreshable;
import lsafer.view.ViewAdapter;

/**
 * a view to display level 1
 * information of a {@link Chain}.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
public class ChainIView extends ViewAdapter implements Refreshable {

    /**
     * targeted {@link Chain} to display information from.
     */
    private Chain chain;
    /**
     * the listener to invoke when specific events happened in this.
     */
    private EventListener listener;

    /**
     * initialize this.
     *
     * @param context  to initialize the view from
     * @param listener to invoke when specific events happened in this
     * @param chain    to display information from
     */
    public ChainIView(Context context, ViewGroup parent, EventListener listener, Chain chain) {
        this.chain = chain;
        this.listener = listener;
        this.initialize(context, parent);
        this.refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_i_chain, parent);

        view.setOnClickListener(v -> this.listener.onChainClick(this));
        view.setOnLongClickListener(v -> this.listener.onChainLongClick(this));

        view.<TextView>findViewById(R.id.name).setText(this.chain.remote().getName());
        view.<TextView>findViewById(R.id.path).setText(this.getContext().getString(R.string.plh__path, this.chain.remote().toString()));

        return view;
    }

    @Override
    public void refresh() {
        LinearLayout processes_linear = this.getView().findViewById(R.id.processes_linear);

        this.getView().<TextView>findViewById(R.id.status).setText(this.getContext().getString(R.string.plh__activated, this.chain.activated));
        this.getView().findViewById(R.id.banner).setBackground(this.getContext().getDrawable(
                this.chain.activated ? R.drawable.kroov_round_good : R.drawable.kroov_round_triggered));

        this.chain.processes.<Process>list().forEach(process -> processes_linear.addView(new ProcessIIView(this.getContext(), process).getView()));
    }

    /**
     * get the targeted {@link Chain}.
     *
     * @return the current targeted task
     */
    public Chain getChain() {
        return this.chain;
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
        default void onChainClick(ChainIView adapter) {
        }

        /**
         * get called when the view clicked a long click.
         *
         * @param adapter the adapter that had called this method
         * @return what to return to {@link View.OnLongClickListener#onLongClick(View)}
         */
        default boolean onChainLongClick(ChainIView adapter) {
            return false;
        }
    }
}
