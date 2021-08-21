package com.momstore.pickers;

import java.util.Random;

public class RandomPicker {
    private static Random random = new Random();

    public static int numberPicker(int totalCount) {
        return random.nextInt(totalCount);
    }
}
