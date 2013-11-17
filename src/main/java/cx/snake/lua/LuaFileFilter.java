package cx.snake.lua;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class LuaFileFilter extends FileFilter
{
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        if (f.getName().endsWith(".lua")) {
            return true;
        }

        return false;
    }
    public String getDescription() {
        return "lua scripts";
    }
}