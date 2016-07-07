package io.koju.autopos.trade.sale.domain;

import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.web.View;
import io.koju.autopos.party.domain.Vehicle;
import io.koju.autopos.trade.domain.Trade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "sale")
@Getter
@Setter
public class Sale extends Trade {

    private static final String ID_SEQ = "sale_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    @JsonView(View.Summary.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "buyer")
    private String buyer;

    @OneToMany(mappedBy = "sale")
    private List<SaleLine> lines = new ArrayList<>();

    @Enumerated(STRING)
    private Status status;

    @Override
    public Long getId() {
        return id;
    }

    public String getClient() {
        return getVehicle().map(Vehicle::getNumber).orElse(buyer);
    }

    public Optional<Vehicle> getVehicle() {
        return Optional.ofNullable(vehicle);
    }

    public enum Status {
        PENDING,
        COMPLETED
    }
}
