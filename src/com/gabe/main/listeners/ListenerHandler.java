package com.gabe.main.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Gabriel on 2020-07-05.
 */
public class ListenerHandler {
    
    private static HashMap<Class, ArrayList<Listener>> listenerHashMap = new HashMap<>();
    
    public static void registerListener(Class c, Listener listener) {
        listenerHashMap.computeIfAbsent(c, l -> new ArrayList<>());
        listenerHashMap.get(c).add(listener);
    }
    
    public static void fireEvent(Event event) {
        for(Listener listener: listenerHashMap.get(event.getClass())) {
            listener.onEvent(event);
        }
    }
}
