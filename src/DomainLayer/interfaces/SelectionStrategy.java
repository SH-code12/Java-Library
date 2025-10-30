package DomainLayer.interfaces;

import java.util.List;
import DomainLayer.entities.Chromosome;

public interface SelectionStrategy<T extends Chromosome<?>> {
    List<T> select(List<T> population);

}
