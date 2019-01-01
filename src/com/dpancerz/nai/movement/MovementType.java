package com.dpancerz.nai.movement;

import java.util.Objects;

class MovementType {
    private final int category;
    private final String label;

    MovementType(int category, String label) {
        this.category = category;
        this.label = label;
    }

    public int getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovementType movementType1 = (MovementType) o;
        return category == movementType1.category &&
                Objects.equals(label, movementType1.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, label);
    }

    @Override
    public String toString() {
        return label;
    }
}
