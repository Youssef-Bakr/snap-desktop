package org.esa.beam.framework.ui.color;

import com.bc.ceres.core.Assert;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Color codes according to <a href="http://www.w3schools.com/html/html_colornames.asp">W3C HTML Color Names</a>.
 *
 * @author Norman Fomferra
 * @since SNAP 2.0
 */
public class ColorCodes {

    private List<String> nameList;
    private Map<Color, String> nameMap;
    private Map<String, Color> colorMap;

    static final ColorCodes instance = new ColorCodes();

    private ColorCodes() {
        nameList = new ArrayList<>(512);
        nameMap = new HashMap<>(512);
        colorMap = new HashMap<>(512);
        try {
            Path path = Paths.get(ColorComboBox.class.getResource("color-codes.txt").toURI());
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                int i = line.indexOf('\t');
                Color color = Color.decode(line.substring(0, i).trim());
                String name = line.substring(i).trim();
                //System.out.println("color = " + color + ", name = " + name);
                Assert.state(!nameMap.containsKey(color), String.format("color '%s' already added", color));
                Assert.state(!colorMap.containsKey(name), String.format("color '%s' already added", name));
                nameList.add(name);
                nameMap.put(color, name);
                colorMap.put(name, color);
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getNames() {
        return Collections.unmodifiableList(instance.nameList);
    }

    public static Color getColor(String name) {
        return instance.colorMap.get(name);
    }

    public static String getName(Color color) {
        return instance.nameMap.get(color);
    }
}
