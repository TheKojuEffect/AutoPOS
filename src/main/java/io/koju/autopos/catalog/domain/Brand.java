package io.koju.autopos.catalog.domain;

import io.koju.autopos.shared.AbstractEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "brand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Brand extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "brand_id_seq", sequenceName = "brand_id_seq", initialValue = 1)
    @GeneratedValue(strategy = SEQUENCE , generator = "brand_id_seq")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brand brand = (Brand) o;
        if(brand.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Brand{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
