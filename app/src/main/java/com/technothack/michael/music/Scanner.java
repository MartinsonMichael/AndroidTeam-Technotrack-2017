package com.technothack.michael.music;

import android.os.Environment;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Scanner {
    private Deque<File> queue = new ArrayDeque<>();
    private List<String> absolutePaths = new ArrayList<>();
    // root must be changed (not all files are found)
    private File root = Environment.getExternalStorageDirectory();
    private String mask;

    public Scanner(String mask) {
        this.mask = mask;
        Collections.addAll(queue, root.listFiles());
    }

    public void run() {
        while (!queue.isEmpty()) {
            File currentFile = queue.remove();
            if (currentFile.isDirectory()) {
                Collections.addAll(queue, currentFile.listFiles());
            } else {
                // get file type
                String fileName = currentFile.toString();
                String fileType = "";
                int nameLength = fileName.length();
                for (int i = nameLength - mask.length(); i < nameLength; i++) {
                    fileType += fileName.charAt(i);
                }

                if (fileType.equals(mask)) {
                    absolutePaths.add(fileName);
                }
            }
        }
    }

    public List<String> getAbsolutePaths() {
        return absolutePaths;
    }
}
