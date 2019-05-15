package android.project.handmedown;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * Checks if the provided string is an acceptable email address.
     *
     * @param email the string to check for email address format
     * @return whether the given string adheres to acceptable email address format
     */
    public static boolean isEmailValid(String email) {
        // email regex definition
        String regExpn = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        // user a Matcher to check if the provided string matches the email regex
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        // return result of match
        return matcher.matches();
    }
}
