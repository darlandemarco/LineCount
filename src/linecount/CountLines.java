/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linecount;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrador
 */
class CountLines {

    static Pattern commentLine = Pattern.compile("^\\s*//");
    static Pattern commentBlockStartAndEnd = Pattern.compile("^\\s*(.*)(/\\*)+(.*)");
    static Pattern commentBlockEnd = Pattern.compile("\\*/");
    static Pattern importLine = Pattern.compile("^\\s*import+\\d*");
    static Pattern packageLine = Pattern.compile("^\\s*package+\\d*");

    private static final int COMMENTED_LINE = 1;
    private static final int COMMENTED_BLOCK_LINE = 2;
    private static final int CONTENT = 0;

    public static int count(BufferedReader in) throws IOException {
        int countLines = -1;
        int countChaves = 1;
        String line;

        boolean commentBlockMode = false;
        int commentBlockLineReturn;

        while (countChaves > 0) {
            line = in.readLine();
            if (line == null) {
                return countLines;
            }
            if (commentBlockMode) {
                commentBlockMode = !commentBlockEnd.matcher(line).find();
                continue;
            } else if (isCommentedLine(line)) {
                continue;
            } else if ((commentBlockLineReturn = isCommentedBlockLine(line)) > CONTENT) {
                if (commentBlockLineReturn == COMMENTED_BLOCK_LINE) {
                    commentBlockMode = true;
                }
                continue;
            } else if (isImportOrPackage(line)) {
                continue;
            }

            if (isValidRow(line)) {
                countLines++;
            }

            if (line.contains("{") ^ line.contains("}")) {
                if (line.contains("{")) {
                    countChaves++;
                } else {
                    countChaves--;
                }
            }
        }

        return countLines;
    }

    public static boolean isValidRow(String line) {
        String lineTrim = line.trim().replaceAll(" ", "");
        return lineTrim.length() > 0 && !lineTrim.equals("{") && !lineTrim.equals("}") && !lineTrim.equals("try{")
                && !lineTrim.equals("}else{") && !lineTrim.contains("}catch(") && !lineTrim.contains(".tratarErro(");
    }

    public static boolean isCommentedLine(String line) {
        return commentLine.matcher(line).find();
    }

    public static int isCommentedBlockLine(String line) {
        Matcher m = commentBlockStartAndEnd.matcher(line);
        if (m.find()) {
            String group1 = m.group(1);
            boolean firstComment = group1.equals("/*");
            boolean hasContent = !firstComment && group1.trim().length() > 1;
            if (m.groupCount() > 1) {
                String group2 = m.group(2);
                if (!hasContent && group2.equals("*/")) {
                    return COMMENTED_LINE;
                }

                if (m.groupCount() > 2 && m.group(3) != null && m.group(3).contains("*/")) {
                    return CONTENT;
                } else {
                    return COMMENTED_BLOCK_LINE;
                }
            } else if (firstComment) {
                return COMMENTED_BLOCK_LINE;
            }
        }

        return CONTENT;
    }

    private static boolean isImportOrPackage(String line) {
        return importLine.matcher(line).find() || packageLine.matcher(line).find();
    }
}
