package DomainLayer.interfaces;

import DomainLayer.entities.Chromosome;

import java.util.List;

public interface SelectionStrategy <T extends Chromosome<?>> {
    public List<T> select();

}
