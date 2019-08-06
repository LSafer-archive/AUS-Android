package lsafer.aus.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.aus.view.TaskIView;
import lsafer.io.JSONFileStructure;
import lsafer.services.io.Profile;
import lsafer.services.io.Task;
import lsafer.view.Refreshable;

/**
 * an activity to display (and edit) level 0
 * information of a {@link Profile}.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class ProfileActivity extends AppCompatActivity implements Refreshable, TaskIView.EventListener {

    /**
     * targeted {@link Profile} to display information from.
     */
    private Profile profile;

    @Override
    public void refresh() {
        this.profile.<Profile>reset().load();
        LinearLayout tasks_linear = this.findViewById(R.id.tasks_linear);

        tasks_linear.removeAllViews();

        this.<TextView>findViewById(R.id.name).setText(this.profile.remote().getName());

        this.profile.forEach((Object key, JSONFileStructure task) ->
                tasks_linear.addView(new TaskIView(this, this, task.clone(Task.class)).getView()));
    }

    @Override
    public void onTaskClick(TaskIView adapter) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.setData(Uri.parse(adapter.getTask().remote().toString()));
        this.startActivity(intent);
    }

    @Override
    public boolean onTaskLongClick(TaskIView adapter) {
        new AlertDialog.Builder(this, R.style.AppAlertDialog)
                .setMessage("do you really want to delete \"" + adapter.getTask().remote().getName() + "\" task ?")
                .setPositiveButton("yes", (d, w) -> {
                    boolean ww = adapter.getTask().delete();
                    if (ww) this.refresh();

                    //TODO resources %s deleting %b
                    Toast.makeText(this, ww ? adapter.getTask().remote().getName() + " deleted" : ");", Toast.LENGTH_LONG).show();
                })
                .show();
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_profile);

        String path = this.getIntent().getDataString();
        assert path != null;

        this.profile = new Profile().remote(path);
        this.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.refresh();
    }

}
