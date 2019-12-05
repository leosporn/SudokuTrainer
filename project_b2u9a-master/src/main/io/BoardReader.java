package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BoardReader {

    public static final int[] SIZE = new int[4];
    private static final int N_BOARDS;

    private static final String PATH = "./data/";
    private static final String EXT = ".txt";

    public static final int[][] BOARDS;
    public static final boolean[][] GIVEN;

    static {
        List<List<Integer>> solved = load("solved");
        List<List<Integer>> unsolved = load("unsolved");
        assertSameSize(solved, unsolved);
        N_BOARDS = solved.size();
        calculateSize(solved.get(0));
        BOARDS = new int[N_BOARDS][SIZE[3]];
        GIVEN = new boolean[N_BOARDS][SIZE[3]];
        for (int boardNumber = 0; boardNumber < N_BOARDS; boardNumber++) {
            assertSameSize(solved.get(boardNumber), unsolved.get(boardNumber));
            for (int idx = 0; idx < SIZE[3]; idx++) {
                BOARDS[boardNumber][idx] = solved.get(boardNumber).get(idx);
                GIVEN[boardNumber][idx] = unsolved.get(boardNumber).get(idx) > 0;
            }
        }
    }

    private static List<List<Integer>> load(String filename) {
        List<List<Integer>> data = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get(PATH + filename + EXT))) {
                data.add(newBoard(line.toCharArray()));
            }
        } catch (IOException e) {
            System.out.println("The following stack trace should only be printed out while testing:");
            e.printStackTrace();
        }
        return data;
    }

    private static List<Integer> newBoard(char[] line) {
        List<Integer> board = new ArrayList<>();
        for (char ch : line) {
            board.add(Character.getNumericValue(ch));
        }
        return board;
    }

    private static void calculateSize(List<Integer> board) {
        int size = 1;
        while (size * size * size * size < board.size()) {
            size++;
        }
        SIZE[0] = size;
        SIZE[1] = size * SIZE[0];
        SIZE[2] = size * SIZE[1];
        SIZE[3] = size * SIZE[2];
    }

    private static void assertSameSize(List list1, List list2) {
        assert list1.size() == list2.size();
    }
}
