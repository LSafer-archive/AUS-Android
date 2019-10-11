package lsafer.aus.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.aus.view.i.ChainIView;
import lsafer.services.io.Chain;
import lsafer.services.io.Profile;
import lsafer.services.util.Process;
import lsafer.view.Refreshable;

/**
 * an activity to display (and edit) level 0
 * information of a {@link Profile}.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class ProfileActivity extends AppCompatActivity implements Refreshable, ChainIView.EventListener {

    /**
     * targeted {@link Profile} to display information from.
     */
    private Profile profile;

    @Override
    public void onChainClick(ChainIView adapter) {
        Intent intent = new Intent(this, EditChainActivity.class);
        intent.putExtra("chain", adapter.getChain());
        this.startActivity(intent);
    }

    @Override
    public boolean onChainLongClick(ChainIView adapter) {
        new AlertDialog.Builder(this, R.style.KroovAlertDialogTheme)
                .setMessage("do you really want to delete \"" + adapter.getChain().remote().getName() + "\" task ?")
                .setPositiveButton("yes", (d, w) -> {
                    boolean ww = adapter.getChain().delete();
                    if (ww) this.refresh();

                    //TODO resources %s deleting %b
                    Toast.makeText(this,
                            ww ? adapter.getChain().remote().getName() + " deleted" : ");", Toast.LENGTH_LONG).show();
                })
                .show();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_profile);

        this.profile = (Profile) this.getIntent().getSerializableExtra("profile");
        this.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.refresh();
    }

    @Override
    public void refresh() {
        this.profile.<Profile>reset().load();
        LinearLayout chains_linear = this.findViewById(R.id.chains_linear);

        chains_linear.removeAllViews();

        this.<TextView>findViewById(R.id.name).setText(this.profile.remote().getName());

        this.profile.map(String.class, Chain.class).forEach((name, chain) ->
                new ChainIView(this, chains_linear, this, chain));
    }

    /**
     * @param view
     */
    public void _add(View view) {
        new AlertDialog.Builder(this, R.style.KroovAlertDialogTheme)
                .setTitle("new task")
                .setMessage("name:")
                .setView(R.layout.edit_dialog_input)
                .setPositiveButton("done", (d, w) -> {
                    this.profile.put(((Dialog) d).<EditText>findViewById(R.id.text).getText().toString(), new Process<>());
                    this.profile.save();
                    this.refresh();
                })
                .show();
    }

}
