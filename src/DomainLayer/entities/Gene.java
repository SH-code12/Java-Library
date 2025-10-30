
package DomainLayer.entities;

public class Gene<T> {
    private T value;

    public Gene(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    // Optional: useful if you need deep copies or comparisons
    public Gene<T> copy() {
        return new Gene<>(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Gene<?> gene)) return false;
        return value != null && value.equals(gene.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}

