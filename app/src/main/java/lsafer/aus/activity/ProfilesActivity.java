package lsafer.aus.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.aus.view.ProfileIView;
import lsafer.services.io.Profile;
import lsafer.services.io.Profiles;
import lsafer.services.text.Run;
import lsafer.view.Refreshable;

/**
 * an activity to display (or edit) all profiles.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class ProfilesActivity extends AppCompatActivity implements Refreshable, ProfileIView.EventListener {

    @Override
    public void refresh() {
        LinearLayout profiles_linear = this.findViewById(R.id.profiles_linear);

        profiles_linear.removeAllViews();

        Profiles.$.forEach((Object key, Profile profile) -> profiles_linear.addView(
                new ProfileIView(this, this, profile).getView()));
    }

    @Override
    public void onProfileClick(ProfileIView adapter) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.setData(Uri.parse(adapter.getProfile().remote().toString()));
        this.startActivity(intent);
    }

    @Override
    public boolean onProfileLongClick(ProfileIView adapter) {
        new AlertDialog.Builder(this, R.style.AppAlertDialog)
                .setMessage("do you really want to delete \"" + adapter.getProfile().remote().getName() + "\" profile ?")
                .setPositiveButton("yes", (d, w) -> {
                    boolean ww = adapter.getProfile().delete();
                    if (ww) this.refresh();

                    //TODO resources %s deleting %b
                    Toast.makeText(this, ww ? adapter.getProfile().remote().getName() + " deleted" : ");", Toast.LENGTH_LONG).show();
                })
                .show();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_profiles);

        Profiles.$.remote(Environment.getExternalStoragePublicDirectory("Services/Profiles"));

        if (!Profiles.$.$initialized) Profiles.$.<Profiles>load().initialize(this);

        this.refresh();
    }

    /**
     * run {@link Run#stop "stop"} on {@link Profiles global profiles instance}
     * the reset, reload it and reinitialize it then refresh this.
     *
     * @param view that had called this method
     */
    public void _killThenReload(View view) {
        if (Profiles.$.$initialized) {
            Profiles.$.run(Run.stop, this);
        }
        Profiles.$.<Profiles>reset().<Profiles>load().initialize(this);
        this.refresh();
    }

}
