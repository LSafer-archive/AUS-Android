package lsafer.aus.io;

import android.os.Environment;

import lsafer.aus.R;
import lsafer.io.JSONFileStructure;

/**
 * user interface preference data storage.
 *
 * @author LSaferSE
 * @version 2 release (06-Aug-19)
 * @since 05-Aug-19
 */
public class UserInterface extends JSONFileStructure {

    /**
     * the instance.
     */
    public static UserInterface $ = new UserInterface().<UserInterface>remote(
            Environment.getExternalStoragePublicDirectory("Services/UI")).load();

    /**
     * the UI theme.
     */
    public String theme = "black";

    /**
     * get the theme resources id from the {@link #$ global instance}.
     *
     * @return the theme resources id
     */
    public static int theme() {
        switch (UserInterface.$.theme) {
            case "black":
                return R.style.BlackAppTheme;
            case "light":
                return R.style.LightAppTheme;
            default:
                return R.style.BlackAppTheme;
        }
    }
}
