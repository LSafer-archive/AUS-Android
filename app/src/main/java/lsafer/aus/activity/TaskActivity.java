package lsafer.aus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.json.JSON;
import lsafer.services.io.Task;
import lsafer.view.Refreshable;

/**
 * an activity to display (and edit) level 0
 * information of a {@link Task}.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class TaskActivity extends AppCompatActivity implements Refreshable {

    /**
     * targeted {@link Task} to display information from.
     */
    private Task task;

    @Override
    public void refresh() {
        this.task.<Task>reset().load();
        this.<TextView>findViewById(R.id.name).setText(this.task.remote().getName());
        this.<EditText>findViewById(R.id.feather).setText(JSON.stringify(this.task).replace("\t", "\t\t"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_task);

        String path = this.getIntent().getDataString();
        assert path != null;

        this.task = new Task().remote(path);
        this.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.refresh();
    }

    /**
     * save the {@link #task targeted task}.
     *
     * @param view that had called this method
     */
    public void _save(View view) {
        TextView feather = this.findViewById(R.id.feather);

        this.task.putAll(JSON.parse_map(feather.getText().toString()));

        Toast.makeText(this, this.task.save() ? "saved" : ");", Toast.LENGTH_LONG).show();
        this.refresh();
    }

}
