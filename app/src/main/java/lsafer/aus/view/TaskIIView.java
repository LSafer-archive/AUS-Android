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
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
@SuppressWarnings("WeakerAccess")
public class TaskIIView extends ViewAdapter {

    /**
     *
     */
    private Task task;

    /**
     * @param context
     * @param task
     */
    public TaskIIView(Context context, Task task){
        this.task = task;
        this.init(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_ii_task, null);
        TextView name = view.findViewById(R.id.name);

        name.setText(this.task.remote().getName());

        if (!this.task.activated) name.setTextColor(this.getContext().getColor(R.color.triggered));

        return view;
    }

}
