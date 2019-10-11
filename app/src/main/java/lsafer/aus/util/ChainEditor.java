package lsafer.aus.util;

import lsafer.view.Refreshable;
import lsafer.view.ViewAdapter;

/**
 *
 */
public abstract class ChainEditor extends ViewAdapter implements Refreshable {
    /**
     *
     */
    public abstract void apply();
}