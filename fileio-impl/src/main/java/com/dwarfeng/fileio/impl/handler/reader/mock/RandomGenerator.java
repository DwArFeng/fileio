package com.dwarfeng.fileio.impl.handler.reader.mock;

import java.util.Random;

/**
 * 实时模拟数据源随机生成器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class RandomGenerator {

    private final Random random;

    public RandomGenerator(Long randomSeed) {
        if (randomSeed == null) {
            random = new Random();
        } else {
            random = new Random(randomSeed);
        }
    }

    @FieldTypeMarker("int")
    public int nextInt() {
        return random.nextInt();
    }

    @FieldTypeMarker("long")
    public long nextLong() {
        return random.nextLong();
    }

    @FieldTypeMarker("float")
    public float nextFloat() {
        return random.nextFloat();
    }

    @FieldTypeMarker("double")
    public double nextDouble() {
        return random.nextDouble();
    }

    @FieldTypeMarker("gaussian")
    public double nextGaussian() {
        return random.nextGaussian();
    }

    @FieldTypeMarker("boolean")
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @FieldTypeMarker("string")
    public String nextString() {
        long value = Math.abs(nextLong());
        return Long.toString(value, Character.MAX_RADIX);
    }

    @FieldTypeMarker("int_string")
    public String nextIntString() {
        int value = nextInt();
        return Integer.toString(value);
    }

    @FieldTypeMarker("long_string")
    public String nextLongString() {
        long value = nextLong();
        return Long.toString(value);
    }

    @FieldTypeMarker("float_string")
    public String nextFloatString() {
        float value = nextFloat();
        return Float.toString(value);
    }

    @FieldTypeMarker("double_string")
    public String nextDoubleString() {
        double value = nextDouble();
        return Double.toString(value);
    }

    @FieldTypeMarker("gaussian_string")
    public String nextGaussianString() {
        double value = nextGaussian();
        return Double.toString(value);
    }

    @Override
    public String toString() {
        return "RandomGenerator{" +
                "random=" + random +
                '}';
    }
}
