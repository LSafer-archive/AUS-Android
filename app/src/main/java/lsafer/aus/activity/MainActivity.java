package lsafer.aus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import lsafer.aus.R;
import lsafer.aus.io.UserInterface;

/**
 * navigator activity.
 * <p>
 * read data from ~/Utilities/Demons/
 *
 * @author LSaferSE
 * @version 1
 * @see R.layout#activity_main
 * @since 10 Jun 2019
 */
public class MainActivity extends AppCompatActivity {

    /**
     * current applied theme.
     */
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(this.theme = UserInterface.theme());
        this.setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.theme != UserInterface.theme()) {
            this.finish();
            this.startActivity(new Intent(this, MainActivity.class));
        }
    }

    /**
     * opens {@link PermissionsActivity}.
     *
     * @param view that had called this method
     */
    public void _permissions(View view) {
        this.startActivity(new Intent(this, PermissionsActivity.class));
    }

    /**
     * start {@link ProfilesActivity}.
     *
     * @param view that had called this method
     */
    public void _profiles(View view) {
        this.startActivity(new Intent(this, ProfilesActivity.class));
    }

    /**
     * start {@link UIActivity}.
     *
     * @param view that had called this method
     */
    public void _ui(View view) {
        this.startActivity(new Intent(this, UIActivity.class));
    }
}
