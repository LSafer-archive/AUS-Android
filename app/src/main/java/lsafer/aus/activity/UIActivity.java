package lsafer.aus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.view.Refreshable;

/**
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class UIActivity extends AppCompatActivity implements Refreshable {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_ui);
        this.refresh();
    }

    @Override
    public void refresh() {
        UserInterface.$.<UserInterface>reset().load();
        this.<Button>findViewById(R.id.theme).setText(UserInterface.$.theme);
    }

    /**
     * @param view
     */
    public void _set(View view) {
        //noinspection SwitchStatementWithTooFewBranches
        switch (view.getId()){
            case R.id.theme:
                UserInterface.$.theme = UserInterface.$.theme.equals("black") ? "light" : "black";
                UserInterface.$.save();
                this.finish();
                this.startActivity(new Intent(this, UIActivity.class));
                return;
        }

        UserInterface.$.save();
        this.refresh();
    }

}
