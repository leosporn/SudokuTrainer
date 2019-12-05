package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class WebSudokuReader {

    private static final int SIZE2 = 9;

    private String urlBase = "http://lipas.uwasa.fi/~timan/sudoku/";
    private int puzzleNumber;
    private String puzzleLetter;

    private URL[] url = new URL[2];

    /*
     * REQUIRES: n and s correspond to a valid puzzle
     * EFFECTS:  constructor
     */
    public WebSudokuReader(int n, String s) {
        makeURL(n, s);
    }

    /*
     * EFFECTS:  prints the loaded puzzle to the console
     */
    public void printPuzzle(int solvedFlag) {
        for (int[] row : loadPuzzle(solvedFlag)) {
            System.out.println(Arrays.toString(row));
        }
    }

    /*
     * REQUIRES: n and s correspond to a valid puzzle
     * MODIFIES: this
     * EFFECTS:  creates the URLs leading to the solved and unsolved puzzles
     */
    private void makeURL(int n, String s) {
        StringBuilder sb;
        try {
            for (int i = 0; i < 2; i++) {
                sb = new StringBuilder("http://lipas.uwasa.fi/~timan/sudoku/");
                sb.append(String.format("s%02d", n));
                sb.append(n < 16 ? s : "");
                sb.append(i == 1 ? "_s" : "");
                sb.append(".txt");
                url[i] = new URL(sb.toString());
            }
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * EFFECTS:  returns the puzzle (solved or unzolved) as a square array
     */
    private int[][] loadPuzzle(int solvedFlag) {
        int[][] puzzle = new int[SIZE2][SIZE2];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url[solvedFlag].openStream()))) {
            int row = 0;
            while (row < SIZE2) {
                char[] chars = br.readLine().toCharArray();
                if (Character.isDigit(chars[0])) {
                    puzzle[row++] = toRow(chars);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transpose(puzzle);
    }

    /*
     * REQUIRES: puzzle is a square
     * EFFECTS:  transposes puzzle
     */
    private int[][] transpose(int[][] puzzle) {
        int temp;
        for (int i = 0; i < SIZE2; i++) {
            for (int j = i + 1; j < SIZE2; j++) {
                temp = puzzle[i][j];
                puzzle[i][j] = puzzle[j][i];
                puzzle[j][i] = temp;
            }
        }
        return puzzle;
    }

    /*
     * EFFECTS:  converts character array into an integer array represending a row of the puzzle
     */
    private int[] toRow(char[] chars) {
        int[] row = new int[SIZE2];
        int col = 0;
        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                row[col++] = Character.digit(ch, 10);
                if (col == SIZE2) {
                    break;
                }
            }
        }
        return row;
    }
}
