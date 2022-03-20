package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
public class CAS {
    private String name;
    private ArrayList<FunctionalTask> functionalTasks;
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CAS cas = (CAS) o;
        return Objects.equals(name, cas.name) &&
                Objects.equals(functionalTasks, cas.functionalTasks) &&
                Objects.equals(price, cas.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, functionalTasks, price);
    }
}
