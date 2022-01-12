package ru.javaops.restaurantvoting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "registered", "name"}, name = "dishes_unique_restaurant_registered_name_idx")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity implements Serializable {

    @Column(name = "registered", nullable = false, columnDefinition = "date default now()", updatable = false)
    @NotNull
    private LocalDate registered;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 10000)
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate registered, Double price) {
        super(id, name);
        this.registered = registered;
        this.price = price;
    }
}
