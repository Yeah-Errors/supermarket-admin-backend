package org.yaojiu.supermarket.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class SecurityUtil {
    private static final int ITERATIONS = 3;
    private static final int MEMORY = 1024;
    private static final int PARALLELISM = 3;

    public static String hash(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());

    }

    public static boolean verify(String hash, String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(hash, password.toCharArray());
    }

}
