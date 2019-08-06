package lsafer.aus.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import lsafer.aus.R;
import lsafer.services.io.Task;
import lsafer.view.ViewAdapter;

/**
 * a view to display level 1
 * information of a {@link Task}.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
public class TaskIView extends ViewAdapter {

    /**
     * the listener to invoke when specific events happened in this.
     */
    private EventListener listener;
    /**
     * targeted {@link Task} to display information from.
     */
    private Task task;

    /**
     * initialize this.
     *
     * @param context  to initialize the view from
     * @param listener to invoke when specific events happened in this
     * @param task     to display information from
     */
    public TaskIView(Context context, EventListener listener, Task task) {
        this.task = task;
        this.listener = listener;
        this.init(context);
    }

    @SuppressLint({"InflateParams"})
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_i_task, null);
        LinearLayout parts_linear = view.findViewById(R.id.parts_linear);

        view.setOnClickListener(v -> this.listener.onTaskClick(this));
        view.setOnLongClickListener(v -> this.listener.onTaskLongClick(this));

        view.<TextView>findViewById(R.id.name).setText(this.task.remote().getName());
        view.<TextView>findViewById(R.id.status).setText(this.getContext().getString(R.string.plh__activated, this.task.activated));
        view.<TextView>findViewById(R.id.class_name).setText(this.getContext().getString(R.string.plh__class_name, this.task.class_name));
        view.<TextView>findViewById(R.id.apk_path).setText(this.getContext().getString(R.string.plh__apk_path, this.task.apk_path));

        if (!this.task.activated)
            view.findViewById(R.id.banner).setBackground(this.getContext().getDrawable(R.drawable.round_triggered));

        for (Map map : (List<Map>) (Object) this.task.parts)
            parts_linear.addView(new TaskPartIIView(this.getContext(), new Task.Part().putAll(map)).getView());

        return view;
    }

    /**
     * get the targeted {@link Task}.
     *
     * @return the current targeted task
     */
    public Task getTask() {
        return task;
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
        default void onTaskClick(TaskIView adapter) {
        }

        /**
         * get called when the view clicked a long click.
         *
         * @param adapter the adapter that had called this method
         * @return what to return to {@link View.OnLongClickListener#onLongClick(View)}
         */
        default boolean onTaskLongClick(TaskIView adapter) {
            return true;
        }
    }
}
