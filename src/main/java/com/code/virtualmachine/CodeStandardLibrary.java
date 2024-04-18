package com.code.virtualmachine;

import com.code.errors.runtime.ClassLoaderError;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CodeStandardLibrary {

    private static HashMap<String, Class<?>> standardNatives = new HashMap<>();

    public static void registerStandardNatives() {
        standardNatives.put("List", ArrayList.class);
        standardNatives.put("Dictionary", HashMap.class);
        standardNatives.put("Math", CodeMath.class);
        standardNatives.put("Socket", Socket.class);
        standardNatives.put("ServerSocket", ServerSocket.class);
        standardNatives.put("Set", HashSet.class);
        standardNatives.put("LinkedList", LinkedList.class);
        standardNatives.put("HTTP", HTTP.class);
        standardNatives.put("WebView", CodeWeb.class);
        standardNatives.put("FileReader", CodeFileReader.class);
        standardNatives.put("ThreadUtils", ThreadUtils.class);
        standardNatives.put("DanjoCore", DanjoCore.class);
        standardNatives.put("Timer", SimpleTimer.class);
    }

    public static Class<?> getStandardNative(String name) {
        var x = standardNatives.get(name);
        if (x == null) {
            throw new ClassLoaderError(name);
        } else {
            return x;
        }
    }
}
