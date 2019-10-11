package lsafer.aus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.services.util.Service;
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
        this.refresh();
    }

    /**
     * setup views with data.
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void refresh() {
        this.<CheckBox>findViewById(R.id.ignoreBatteryOptimization).setChecked(
                this.getSystemService(PowerManager.class).isIgnoringBatteryOptimizations(this.getPackageName()));

        this.<CheckBox>findViewById(R.id.storage).setChecked(
                this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);

        this.<CheckBox>findViewById(R.id.invoker).setChecked(
                this.checkSelfPermission(Service.PERMISSION_INVOKER) == PackageManager.PERMISSION_GRANTED);

        this.<CheckBox>findViewById(R.id.foregroundService).setChecked(
                Build.VERSION.SDK_INT < Build.VERSION_CODES.P ||
                this.checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED);
//        this.<CheckBox>findViewById(R.id.displayOverOtherApps)
//                .setChecked(Settings.canDrawOverlays(this));
//        this.<CheckBox>findViewById(R.id.writeSystemSettings)
//                .setChecked(Settings.System.canWrite(this));
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
            case R.id.storage:
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 83465396);
                break;

            case R.id.ignoreBatteryOptimization:
                Intent intent0 = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent0.setData(Uri.parse("package:" + this.getPackageName()));
                this.startActivity(intent0);
                break;

            case R.id.foregroundService:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    this.requestPermissions(new String[]{Manifest.permission.FOREGROUND_SERVICE}, 611561551);
                break;

            case R.id.invoker:
                this.requestPermissions(new String[]{Service.PERMISSION_INVOKER}, 64198161);
                break;
//            case R.id.writeSystemSettings:
//                Intent i = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                i.setData(Uri.parse("package:" + getPackageName()));
//                startActivity(i);
//                break;
//            case R.id.displayOverOtherApps:
//                Intent iii = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                iii.setData(Uri.parse("package:" + getPackageName()));
//                startActivity(iii);
//                break;
        }
    }
}
