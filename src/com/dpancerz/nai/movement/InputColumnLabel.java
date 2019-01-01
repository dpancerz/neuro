package com.dpancerz.nai.movement;

import java.util.Objects;

class InputColumnLabel {
    private final int column;
    private final String text;

    InputColumnLabel(int column, String text) {
        this.column = column;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputColumnLabel that = (InputColumnLabel) o;
        return column == that.column &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, text);
    }
}
