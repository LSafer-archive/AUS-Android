package lsafer.aus.view.ii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.services.util.Process;
import lsafer.view.ViewAdapter;

/**
 * a view to display level 2
 * information of a {@link Process}.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
public class ProcessIIView extends ViewAdapter {

    /**
     * targeted {@link Process} to display information from.
     */
    private Process process;

    /**
     * initialize this.
     *
     * @param context to initialize the view from
     * @param process to display information from
     */
    public ProcessIIView(Context context, Process process) {
        this.process = process;
        this.initialize(context, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_ii_process, parent);
        view.<TextView>findViewById(R.id.name).setText(this.getContext().getString(R.string.plh__process_mixed_name, this.process.service_package, this.process.service_class.replace(this.process.service_package, "")));
        return view;
    }
}
