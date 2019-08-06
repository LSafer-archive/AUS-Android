package lsafer.aus.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import lsafer.aus.R;
import lsafer.services.io.Task;
import lsafer.view.ViewAdapter;

/**
 * a view to display level 2
 * information of a {@link Task.Part}.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
@SuppressWarnings("WeakerAccess")
public class TaskPartIIView extends ViewAdapter {

    /**
     * targeted {@link Task.Part} to display information from.
     */
    private Task.Part part;

    /**
     * initialize this.
     *
     * @param context to initialize the view from
     * @param part    to display information from
     */
    public TaskPartIIView(Context context, Task.Part part) {
        this.part = part;
        this.init(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_ii_task_part, null);
        view.<TextView>findViewById(R.id.name).setText(this.part.class_name);
        return view;
    }
}
