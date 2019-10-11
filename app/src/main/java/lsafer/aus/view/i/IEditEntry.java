package lsafer.aus.view.i;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.aus.view.ii.EntryValueIIView;
import lsafer.json.JSON;
import lsafer.services.util.Properties;
import lsafer.util.Structure;
import lsafer.view.ViewAdapter;

/**
 * @author LSaferSE
 * @version 1 alpha (07-Aug-19)
 * @since 07-Aug-19
 */
public class IEditEntry extends ViewAdapter {

    /**
     *
     */
    private Properties.Entry entry;

    /**
     *
     */
    private Structure structure;

    /**
     * @param context
     * @param entry
     * @param structure
     */
    public IEditEntry(Context context, ViewGroup parent, Properties.Entry entry, Structure structure) {
        this.structure = structure;
        this.entry = entry;
        this.initialize(context, parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_i_node, parent);
        LinearLayout values_linear = view.findViewById(R.id.values_linear);

        view.<TextView>findViewById(R.id.value).setText(this.structure.get(String.class, this.entry.name));
        view.<TextView>findViewById(R.id.key).setText(this.entry.name);
        view.<TextView>findViewById(R.id.description).setText(this.entry.description);
        view.<TextView>findViewById(R.id.defaultValue).setText(String.valueOf(this.entry.defaultValue));

        for (Properties.Entry.Value value : this.entry.values)
            values_linear.addView(new EntryValueIIView(this.getContext(), value).getView());

        return view;
    }

    /**
     *
     */
    public void apply() {
        String value = this.getView().<EditText>findViewById(R.id.value).getText().toString();
        this.structure.put(this.entry.name, JSON.parse(value));
    }

}
