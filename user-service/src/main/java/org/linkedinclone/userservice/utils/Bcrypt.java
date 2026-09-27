package org.linkedinclone.userservice.utils;

import org.mindrot.jbcrypt.BCrypt;

import static org.mindrot.jbcrypt.BCrypt.*;

public class Bcrypt {

    public static String hash(String s) {
        return hashpw(s, gensalt());
    }

    public static boolean match(String passwordText, String passwordHashed) {
        return checkpw(passwordText, passwordHashed);
    }
}
