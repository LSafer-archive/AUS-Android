package lsafer.aus.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.aus.view.i.ProfileIView;
import lsafer.services.annotation.Invokable;
import lsafer.services.io.Profile;
import lsafer.services.io.Profiles;
import lsafer.services.util.Arguments;
import lsafer.services.util.Service;
import lsafer.view.Refreshable;

/**
 * an activity to display (or edit) all profiles.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class ProfilesActivity extends AppCompatActivity implements Refreshable, ProfileIView.EventListener {

    /**
     *
     */
    private Profiles profiles = new Profiles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_profiles);

        this.profiles.remote(Environment.getExternalStoragePublicDirectory("Services/Profiles"));
        this.refresh();
    }

    @Override
    public void onProfileClick(ProfileIView adapter) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile", adapter.getProfile());
        this.startActivity(intent);
    }

    @Override
    public boolean onProfileLongClick(ProfileIView adapter) {
        new AlertDialog.Builder(this, R.style.KroovAlertDialogTheme)
                .setMessage(
                        "do you really want to delete \"" + adapter.getProfile().remote().getName() + "\" profile ?")
                .setPositiveButton("yes", (d, w) -> {
                    boolean ww = adapter.getProfile().delete();
                    if (ww) this.refresh();

                    //TODO resources %s deleting %b
                    Toast.makeText(this,
                            ww ? adapter.getProfile().remote().getName() + " deleted" : ");", Toast.LENGTH_LONG).show();
                })
                .show();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.refresh();
    }

    @Override
    public void refresh() {
        this.profiles.<Profiles>reset().load();
        LinearLayout profiles_linear = this.findViewById(R.id.profiles_linear);

        profiles_linear.removeAllViews();

        this.profiles.map(String.class, Profile.class).forEach((name, profile) ->
                profiles_linear.addView(new ProfileIView(this, this, profile).getView()));
    }

    /**
     * @param view
     */
    public void _add(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.KroovAlertDialogTheme)
                .setTitle(R.string.txt__process_new_title)
                .setMessage(R.string.txt__process_new_message)
                .setView(R.layout.edit_dialog_input)
                .setPositiveButton("done", (d, w) -> {
                    this.profiles.put(((Dialog) d).<EditText>findViewById(R.id.text).getText().toString(), new Profile());
                    boolean ww = this.profiles.save();
                    this.refresh();
                })
                .show();
    }

    /**
     * @param view
     */
    public void _killThenRefresh(View view) {
        this.profiles.map(String.class, Profile.class).forEach((name, profile) -> profile.callAll(this, Service.ACTION_SHUTDOWN, Invokable.stop, new Arguments()));
        this.refresh();
    }

}
