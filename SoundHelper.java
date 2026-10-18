package ru.dipsik.woolars;

import org.bukkit.Registry;
import org.bukkit.Sound;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundHelper {

    private static List<String> cachedSoundNames = null;

    public static List<String> getAllSoundNames() {
        if (cachedSoundNames == null) {
            cachedSoundNames = new ArrayList<>();
            for (Sound sound : Registry.SOUNDS) {
                if (sound != null) {
                    String key = sound.getKey().getKey();
                    cachedSoundNames.add(key.toUpperCase().replace('.', '_'));
                }
            }
            Collections.sort(cachedSoundNames);
        }
        return cachedSoundNames;
    }
}
