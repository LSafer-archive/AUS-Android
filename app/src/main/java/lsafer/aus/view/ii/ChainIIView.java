package lsafer.aus.view.ii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import lsafer.aus.R;
import lsafer.services.io.Chain;
import lsafer.view.ViewAdapter;

/**
 * a view to display level 2
 * information of a {@link Chain}.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
public class ChainIIView extends ViewAdapter {

    /**
     * targeted {@link Chain} to display information from.
     */
    private Chain chain;

    /**
     * initialize this.
     *
     * @param context to initialize the view from
     * @param chain   to display information from
     */
    public ChainIIView(Context context, Chain chain) {
        this.chain = chain;
        this.initialize(context, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_ii_chain, parent);
        TextView name = view.findViewById(R.id.name);

        name.setText(this.chain.remote().getName());
        name.setTextColor(this.getContext().getColor(this.chain.activated ? R.color.kroovColorGood : R.color.kroovColorTriggered));

        return view;
    }

}
