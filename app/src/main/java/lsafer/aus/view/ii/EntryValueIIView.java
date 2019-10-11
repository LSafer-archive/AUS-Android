package lsafer.aus.view.ii;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.services.util.Properties;
import lsafer.view.ViewAdapter;

/**
 * @author LSaferSE
 * @version 1 alpha (07-Aug-19)
 * @since 07-Aug-19
 */
@SuppressWarnings("WeakerAccess")
public class EntryValueIIView extends ViewAdapter {

    /**
     *
     */
    private Properties.Entry.Value value;

    /**
     * @param context
     * @param value
     */
    public EntryValueIIView(Context context, Properties.Entry.Value value) {
        this.value = value;
        this.initialize(context, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_ii_entry_value, parent);

        view.<TextView>findViewById(R.id.name).setText(this.getContext().getString(R.string.plh__value_name, String.valueOf(this.value.name)));
        view.<TextView>findViewById(R.id.description).setText(String.valueOf(this.value.description));

        return view;
    }

}
