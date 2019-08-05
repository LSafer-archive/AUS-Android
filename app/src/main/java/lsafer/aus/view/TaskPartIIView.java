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
public class TaskPartIIView extends ViewAdapter {

    /**
     *
     */
    private Task.Part part;

    /**
     * @param context
     * @param part
     */
    public TaskPartIIView(Context context, Task.Part part){
        this.part = part;
        this.init(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.view_ii_task_part,null);
        view.<TextView>findViewById(R.id.name).setText(this.part.class_name);
        return view;
    }
}
