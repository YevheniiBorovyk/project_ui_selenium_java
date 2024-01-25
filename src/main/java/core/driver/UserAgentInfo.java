package core.driver;

import static utils.SystemMan.getOsName;

public class UserAgentInfo {

    //"Macintosh; Intel Mac OS";
    private final String MAC = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) Chrome/85.0.4183.83 Safari/537.36";
    //"Windows NT 6.0";
    private final String WIN = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) Chrome/85.0.4183.83 Safari/537.36";
    //X11; Linux";
    private final String LINUX = "Mozilla/5.0 (X11; Linux armv7l)  Chrome/85.0.4183.83 Safari/537.36";

    public String toString() {
        String os = getOsName().toLowerCase();
        if (os.contains("mac")) {
            return MAC;
        } else if (os.contains("win")) {
            return WIN;
        } else if (os.contains("linux")) {
            return LINUX;
        } else {
            return "undefined UserAgentInfo";
        }
    }
}
