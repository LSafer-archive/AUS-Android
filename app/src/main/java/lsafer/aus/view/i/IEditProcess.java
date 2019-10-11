package lsafer.aus.view.i;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.services.util.Process;
import lsafer.services.util.Properties;
import lsafer.services.util.PropertiesGatherer;
import lsafer.view.ViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LSaferSE
 * @version 1 alpha (07-Aug-19)
 * @since 07-Aug-19
 */
public class IEditProcess extends ViewAdapter {

    /**
     *
     */
    private EventListener listener;
    /**
     *
     */
    private List<IEditEntry> nodes_views = new ArrayList<>();
    /**
     *
     */
    private Process process;

    /**
     *
     */
    private Properties properties;

    /**
     * @param context
     */
    public IEditProcess(Context context, ViewGroup parent, EventListener listener, Process process) {
        this.process = process;
        this.listener = listener;

        this.initialize(context, parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.edit_i_process, parent);
    }

    @Override
    public void onCreated() {
        PropertiesGatherer.gather(this.getContext(), new Intent().setClassName(this.process.service_package, this.process.service_class), properties -> {
            this.properties = properties;

            LinearLayout nodes_linear = this.getView().findViewById(R.id.nodes_linear);

            this.getView().<TextView>findViewById(R.id.name).setText(properties.name);
            this.getView().<TextView>findViewById(R.id.description).setText(properties.description);

            this.getView().setOnLongClickListener(v -> this.listener.onProcessLongClick(this));

            nodes_linear.removeAllViews();

            this.properties.entries.forEach((name, node) ->
                    this.nodes_views.add(new IEditEntry(this.getContext(), nodes_linear, node, this.process)));
        });
    }

    /**
     *
     */
    public void apply() {
        for (IEditEntry field_view : this.nodes_views)
            field_view.apply();
    }

    /**
     * @return
     */
    public Process getProcess() {
        return this.process;
    }

    /**
     *
     */
    public interface EventListener {
        /**
         * @param adapter
         * @return
         */
        default boolean onProcessLongClick(IEditProcess adapter) {
            return false;
        }
    }

}
