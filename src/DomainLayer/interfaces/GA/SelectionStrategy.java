package DomainLayer.interfaces.GA;

import java.util.List;
import DomainLayer.entities.GA.Chromosome;

public interface SelectionStrategy<T extends Chromosome<?>> {
    List<T> select(List<T> population);

}
