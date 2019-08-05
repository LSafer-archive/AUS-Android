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
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class TaskIView extends ViewAdapter {

    /**
     *
     */
    private Task task;

    /**
     *
     */
    private EventListener listener;

    /**
     * @param context
     * @param task
     */
    public TaskIView(Context context, EventListener listener, Task task){
        this.task = task;
        this.listener = listener;
        this.init(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_i_task, null);
        LinearLayout parts_linear = view.findViewById(R.id.parts_linear);

        view.setOnClickListener(v-> this.listener.onTaskClick(this));
        view.setOnLongClickListener(v-> this.listener.onTaskLongClick(this));

        //TODO resources
        view.<TextView>findViewById(R.id.name).setText(this.task.remote().getName());
        view.<TextView>findViewById(R.id.status).setText(this.task.activated ? "status: activated" : "status: deactivated");
        view.<TextView>findViewById(R.id.class_name).setText("class: " + this.task.class_name);
        view.<TextView>findViewById(R.id.apk_path).setText("class file: " + this.task.apk_path);

        if (!this.task.activated)
            view.findViewById(R.id.banner).setBackground(this.getContext().getDrawable(R.drawable.round_triggered));

        for (Map map : (List<Map>)(Object) this.task.parts)
            parts_linear.addView(new TaskPartIIView(this.getContext(), new Task.Part().putAll(map)).getView());

        return view;
    }

    /**
     * @return
     */
    public Task getTask() {
        return task;
    }

    /**
     *
     */
    public interface EventListener {
        /**
         * @param adapter
         * @return
         */
        default boolean onTaskLongClick(TaskIView adapter){
            return true;
        }
        /**
         * @param adapter
         */
        default void onTaskClick(TaskIView adapter){
        }
    }
}
