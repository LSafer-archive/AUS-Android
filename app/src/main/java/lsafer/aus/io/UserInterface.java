package lsafer.aus.io;

import android.os.Environment;

import lsafer.aus.R;
import lsafer.io.JSONFileStructure;

/**
 * @author LSaferSE
 * @version 1 alpha (05-Aug-19)
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
     * @return
     */
    public static int theme(){
        switch (UserInterface.$.theme){
            case "black":
                return R.style.BlackAppTheme;
            case "light":
                return R.style.LightAppTheme;
            default:
                return R.style.BlackAppTheme;
        }
    }
}
