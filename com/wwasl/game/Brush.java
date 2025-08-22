package com.wwasl.game;

// Package it if you're moving out of default package, e.g.:
// package com.minigame.edit;

import java.util.Objects;

/**
 * Brush = a declarative "edit intent" for the world.
 * The world/engine interprets this (e.g., voxel CSG),
 * not this class itself.
 *
 * Keep this POD-like: small, serializable, and deterministic.
 */
public final class Brush {

    /** Add (material) or Subtract (material). */
    public enum Operation { ADD, SUB }

    /** Primitive shape for the edit volume. Keep small & stable for networking. */
    public enum Shape { SPHERE, CAPSULE, WEDGE, BOX }

    // --- Core spec (immutable once built) ---
    private final Operation op;
    private final Shape shape;

    /** Center of effect in world space (required for all shapes). */
    private final Vertex center;

    /**
     * Orientation / direction in world space.
     * For axis‑free shapes (SPHERE) this can be null.
     * For oriented shapes (CAPSULE/WEDGE/BOX) supply a (roughly) unit vector.
     */
    private final Vertex direction;

    /**
     * Primary size parameter:
     *  - SPHERE: radius
     *  - CAPSULE: radius
     *  - WEDGE: baseRadius (or edge half‑thickness)
     *  - BOX: halfExtentX (use sizeB/sizeC for Y/Z)
     */
    private final double sizeA;

    /**
     * Secondary size parameter:
     *  - CAPSULE: halfLength along direction
     *  - WEDGE: length along direction
     *  - BOX: halfExtentY
     *  - SPHERE: unused (0)
     */
    private final double sizeB;

    /**
     * Tertiary size parameter (only for BOX as halfExtentZ; else 0).
     */
    private final double sizeC;

    /**
     * Logical "cut power" (already scaled by your p^2 rule, etc.).
     * Interpretation is up to World.apply(Brush): energy→removed volume.
     */
    private final double energy;

    /** Optional: material id (e.g., wood=1, stone=2). 0 means "unspecified". */
    private final int materialId;

    /** Optional: deterministic seed for any stochastic micro‑details. */
    private final long seed;

    // --- Construction ---

    private Brush(Builder b) {
        this.op = Objects.requireNonNull(b.op, "op");
        this.shape = Objects.requireNonNull(b.shape, "shape");
        this.center = Objects.requireNonNull(b.center, "center");
        this.direction = b.direction; // may be null for SPHERE

        this.sizeA = nonNegative(b.sizeA, "sizeA");
        this.sizeB = nonNegative(b.sizeB, "sizeB");
        this.sizeC = nonNegative(b.sizeC, "sizeC");
        this.energy = nonNegative(b.energy, "energy");

        this.materialId = b.materialId;
        this.seed = b.seed;
    }

    private static double nonNegative(double v, String name) {
        if (v < 0) throw new IllegalArgumentException(name + " < 0");
        return v;
    }

    // --- Getters (no setters; keep deterministic & simple to serialize) ---

    public Operation getOp() { return op; }
    public Shape getShape() { return shape; }
    public Vertex getCenter() { return center; }
    public Vertex getDirection() { return direction; } // may be null
    public double getSizeA() { return sizeA; }
    public double getSizeB() { return sizeB; }
    public double getSizeC() { return sizeC; }
    public double getEnergy() { return energy; }
    public int getMaterialId() { return materialId; }
    public long getSeed() { return seed; }

    // --- Convenience factories for clarity at call sites ---

    /** Sphere subtract/add with radius and energy at center. */
    public static Brush sphere(Operation op, Vertex center, double radius, double energy, int materialId) {
        return new Builder()
                .op(op).shape(Shape.SPHERE)
                .center(center)
                .sizeA(radius)
                .energy(energy)
                .materialId(materialId)
                .build();
    }

    /** Capsule along direction (halfLength, radius). */
    public static Brush capsule(Operation op, Vertex center, Vertex direction, double halfLength, double radius, double energy, int materialId) {
        return new Builder()
                .op(op).shape(Shape.CAPSULE)
                .center(center).direction(direction)
                .sizeA(radius).sizeB(halfLength)
                .energy(energy).materialId(materialId)
                .build();
    }

    /** Wedge oriented by direction (baseRadius, length). */
    public static Brush wedge(Operation op, Vertex center, Vertex direction, double baseRadius, double length, double energy, int materialId) {
        return new Builder()
                .op(op).shape(Shape.WEDGE)
                .center(center).direction(direction)
                .sizeA(baseRadius).sizeB(length)
                .energy(energy).materialId(materialId)
                .build();
    }

    /** Box with half-extents (x,y,z) oriented by direction (your engine decides exact frame). */
    public static Brush box(Operation op, Vertex center, Vertex direction, double hx, double hy, double hz, double energy, int materialId) {
        return new Builder()
                .op(op).shape(Shape.BOX)
                .center(center).direction(direction)
                .sizeA(hx).sizeB(hy).sizeC(hz)
                .energy(energy).materialId(materialId)
                .build();
    }

    @Override
    public String toString() {
        return "Brush{" +
                "op=" + op +
                ", shape=" + shape +
                ", center=(" + center.x + "," + center.y + "," + center.z + ")" +
                (direction != null ? ", dir=(" + direction.x + "," + direction.y + "," + direction.z + ")" : "") +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                ", energy=" + energy +
                ", materialId=" + materialId +
                ", seed=" + seed +
                '}';
    }

    // --- Builder for readability and optional fields ---

    public static final class Builder {
        private Operation op;
        private Shape shape;
        private Vertex center;
        private Vertex direction; // nullable
        private double sizeA, sizeB, sizeC;
        private double energy;
        private int materialId;
        private long seed;

        public Builder op(Operation v) { this.op = v; return this; }
        public Builder shape(Shape v) { this.shape = v; return this; }
        public Builder center(Vertex v) { this.center = v; return this; }
        public Builder direction(Vertex v) { this.direction = v; return this; }
        public Builder sizeA(double v) { this.sizeA = v; return this; }
        public Builder sizeB(double v) { this.sizeB = v; return this; }
        public Builder sizeC(double v) { this.sizeC = v; return this; }
        public Builder energy(double v) { this.energy = v; return this; }
        public Builder materialId(int v) { this.materialId = v; return this; }
        public Builder seed(long v) { this.seed = v; return this; }

        public Brush build() { return new Brush(this); }
    }
}
