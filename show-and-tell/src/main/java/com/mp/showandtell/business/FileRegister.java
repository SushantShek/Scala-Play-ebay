package com.mp.showandtell.business;

import java.util.HashMap;
import java.util.Map;

public class FileRegister {

    static Map<String,ReaderInterface> reg = new HashMap<>();

    public static void register(String ext, ReaderInterface fr ) {
        reg.put( ext, fr );
    }

    public static ReaderInterface getClass(String ext) {
        return reg.get(ext);
    }
}
