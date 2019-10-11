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
    public transient static UserInterface $ = new UserInterface().<UserInterface>remote(Environment.getExternalStoragePublicDirectory("Services/UI")).load();

    /**
     * what editor should use on editing tasks.
     */
    public String editor_mode = "wizard";

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
        return UserInterface.theme(UserInterface.$.theme);
    }

    /**
     * get the theme resources id from the given data string..
     *
     * @param source data
     * @return the theme resources id
     */
    public static int theme(String source) {
        switch (source) {
            case "light":
                return R.style.KroovLightAppTheme;
            case "black":
            default:
                return R.style.KroovBlackAppTheme;
        }
    }

}
