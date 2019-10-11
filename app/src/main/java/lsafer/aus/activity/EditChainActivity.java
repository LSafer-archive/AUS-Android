package lsafer.aus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lsafer.aus.R;
import lsafer.aus.io.UserInterface;
import lsafer.aus.util.ChainEditor;
import lsafer.aus.view.EditChainSource;
import lsafer.aus.view.EditChainWizard;
import lsafer.services.io.Chain;
import lsafer.services.util.Properties;
import lsafer.view.Refreshable;

/**
 * an activity to display (and edit) level 0
 * information of a {@link lsafer.services.util.Process}.
 *
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
 * @since 05-Aug-19
 */
public class EditChainActivity extends AppCompatActivity implements Refreshable {

    /**
     * targeted to display information from.
     */
    private Chain chain;

    /**
     *
     */
    private ChainEditor editor;

    /**
     *
     */
    private String mode;

    /**
     *
     */
    private Properties properties;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(UserInterface.theme());
        this.setContentView(R.layout.activity_edit_chain);

        this.properties = new Chain().properties(this.getResources());
        this.chain = (Chain) this.getIntent().getSerializableExtra("chain");
        this.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.refresh();
    }

    @Override
    public void refresh() {
        this.chain.<Chain>reset().load();

        this.<TextView>findViewById(R.id.name).setText(this.chain.remote().getName());

        if (!UserInterface.$.editor_mode.equals(this.mode)) {
            ScrollView banner = this.findViewById(R.id.banner);

            banner.removeAllViews();

            switch (UserInterface.$.editor_mode) {
                case "wizard":
                    this.editor = new EditChainWizard(this, banner, properties, this.chain);
                    this.mode = "wizard";
                    break;
                case "source":
                default:
                    this.editor = new EditChainSource(this, banner, this.chain);
                    this.mode = "source";
                    break;
            }
        }

        this.editor.refresh();
    }

    /**
     * @param view
     */
    public void _mode(View view) {
        switch (this.mode) {
            case "wizard":
                UserInterface.$.editor_mode = "source";
                break;
            case "source":
            default:
                UserInterface.$.editor_mode = "wizard";
                break;
        }

        UserInterface.$.save();
        this.refresh();
    }

    /**
     * save the {@link #chain targeted task}.
     *
     * @param view that had called this method
     */
    public void _save(View view) {
        this.editor.apply();
        Toast.makeText(this, this.chain.save() ? "saved" : ");", Toast.LENGTH_LONG).show();
        this.refresh();
    }

}
