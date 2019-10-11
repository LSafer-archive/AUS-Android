package lsafer.aus.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lsafer.aus.R;
import lsafer.aus.util.ChainEditor;
import lsafer.aus.view.i.IEditEntry;
import lsafer.aus.view.i.IEditProcess;
import lsafer.services.io.Chain;
import lsafer.services.util.Process;
import lsafer.services.util.Properties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LSaferSE
 * @version 1 alpha (06-Aug-19)
 * @since 06-Aug-19
 */
public class EditChainWizard extends ChainEditor implements IEditProcess.EventListener {

    /**
     *
     */
    private Chain chain;
    /**
     *
     */
    private List<IEditEntry> nodes_views = new ArrayList<>();
    /**
     *
     */
    private List<IEditProcess> processes_views = new ArrayList<>();
    /**
     *
     */
    private Properties properties;

    /**
     * @param context
     * @param parent
     * @param chain
     */
    public EditChainWizard(Context context, ViewGroup parent, Properties properties, Chain chain) {
        this.chain = chain;
//        this.properties = chain.properties(context.getResources(), R.string.class);
        this.properties = properties;

        this.initialize(context, parent);
        this.refresh();
    }

    @Override
    public void apply() {
        for (IEditEntry field_view : nodes_views)
            field_view.apply();
        for (IEditProcess part_view : processes_views)
            part_view.apply();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.edit_chain_wizard, parent);

        view.findViewById(R.id.add).setOnClickListener(v -> {
            this.chain.processes.add(new Process<>());
            this.refresh();
        });

        return view;
    }

    @Override
    public boolean onProcessLongClick(IEditProcess adapter) {
        Process process = adapter.getProcess();
        new AlertDialog.Builder(this.getContext(), R.style.KroovAlertDialogTheme)
                .setMessage(this.getContext().getString(R.string.plh__process_delete_message, process.service_class, process.index))
                .setPositiveButton(R.string.abs__yes, (d, w) -> {
                    this.chain.processes.remove(process);
                    boolean ww = this.chain.save();
                    if (ww) this.refresh();

                    Toast.makeText(this.getContext(), this.getContext().getString(
                            ww ? R.string.plh__process_deleted_success : R.string.plh__process_deleted_faild,
                            process.service_class, process.index),
                            Toast.LENGTH_LONG).show();
                })
                .setNegativeButton(R.string.abs__no, (d, w) -> {
                })
                .show();

        return true;
    }

    @Override
    public void refresh() {
        this.nodes_views.clear();
        this.processes_views.clear();

        LinearLayout nodes_linear = this.getView().findViewById(R.id.nodes_linear);
        LinearLayout processes_linear = this.getView().findViewById(R.id.processes_linear);

        nodes_linear.removeAllViews();
        processes_linear.removeAllViews();

        this.getView().<TextView>findViewById(R.id.name).setText(this.chain.remote().getName());
        this.getView().<TextView>findViewById(R.id.path).setText(this.chain.remote().toString());

        this.properties.entries.forEach((name, node) ->
                this.nodes_views.add(new IEditEntry(this.getContext(), nodes_linear, node, this.chain)));

        this.chain.processes.<Process>list().forEach(process ->
                this.processes_views.add(new IEditProcess(this.getContext(), processes_linear, this, process)));
    }
}
