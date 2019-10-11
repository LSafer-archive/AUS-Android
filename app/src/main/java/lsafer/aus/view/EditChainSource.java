package lsafer.aus.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import lsafer.aus.R;
import lsafer.aus.util.ChainEditor;
import lsafer.json.JSON;
import lsafer.services.io.Chain;

/**
 * @author LSaferSE
 * @version 1 alpha (06-Aug-19)
 * @since 06-Aug-19
 */
public class EditChainSource extends ChainEditor {

    /**
     *
     */
    private Chain chain;

    /**
     * @param context
     */
    public EditChainSource(Context context, ViewGroup parent, Chain chain) {
        this.chain = chain;
        this.initialize(context, parent);
    }

    /**
     * @return
     */
    public void apply() {
        this.chain.reset();
        String source = this.getView().<EditText>findViewById(R.id.feather).getText().toString();
        this.chain.putAll(JSON.parse_map(source));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.edit_chain_source, parent);
    }

    @Override
    public void refresh() {
        this.getView().<EditText>findViewById(R.id.feather).setText(JSON.stringify(this.chain).replace("\t", "\t\t"));
    }

}
