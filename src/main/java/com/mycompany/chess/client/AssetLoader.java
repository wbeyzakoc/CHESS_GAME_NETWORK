package com.mycompany.chess.client;

import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;

public final class AssetLoader {

    private AssetLoader() {
    }

    public static ImageIcon icon(String fileName) {
        URL resource = AssetLoader.class.getResource("/com/mycompany/chess/assets/" + fileName);
        if (resource != null) {
            return new ImageIcon(resource);
        }

        File fallback = new File("img", fileName);
        if (fallback.exists()) {
            return new ImageIcon(fallback.getAbsolutePath());
        }

        return new ImageIcon();
    }
}
