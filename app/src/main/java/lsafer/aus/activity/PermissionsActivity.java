package lsafer.aus.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.view.Refreshable;

/**
 * activity to manage application's permissions.
 *
 * @author LSaferSE
 * @version 1
 * @see R.layout#activity_permissions
 * @since 11 Jun 2019
 */
public class PermissionsActivity extends AppCompatActivity implements Refreshable {


    /**
     * setup views with data.
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void refresh() {
        this.<CheckBox>findViewById(R.id.ignoreBatteryOptimization)
                .setChecked(this.getSystemService(PowerManager.class).isIgnoringBatteryOptimizations(this.getPackageName()));
        this.<CheckBox>findViewById(R.id.storage)
                .setChecked(this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED);
        this.<CheckBox>findViewById(R.id.displayOverOtherApps)
                .setChecked(Settings.canDrawOverlays(this));
        this.<CheckBox>findViewById(R.id.writeSystemSettings)
                .setChecked(Settings.System.canWrite(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_permissions);
        this.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    /**
     * ask system for a permission depending on the
     * view's id that have called this method.
     *
     * @param view that have called this method
     */
    @SuppressLint("BatteryLife")
    public void _request(View view) {
        this.refresh();
        switch (view.getId()) {
            case R.id.writeSystemSettings:
                Intent i = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                i.setData(Uri.parse("package:" + getPackageName()));
                startActivity(i);
                break;

            case R.id.storage:
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 83465396);
                break;

            case R.id.ignoreBatteryOptimization:
                Intent ii = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                ii.setData(Uri.parse("package:" + getPackageName()));
                startActivity(ii);
                break;

            case R.id.displayOverOtherApps:
                Intent iii = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                iii.setData(Uri.parse("package:" + getPackageName()));
                startActivity(iii);
                break;
        }
    }
}
