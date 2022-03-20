package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
public class CASForManagementBody {
    private CAS cas;
    private Double efficiency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CASForManagementBody that = (CASForManagementBody) o;
        return Objects.equals(cas, that.cas) &&
                Objects.equals(efficiency, that.efficiency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cas, efficiency);
    }
}
