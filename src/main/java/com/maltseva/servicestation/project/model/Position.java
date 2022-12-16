package com.maltseva.servicestation.project.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * The position class is associated with the user by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */

@Entity
@Table(name = "positions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id", foreignKey = @ForeignKey(name = "FK_POSITIONS_SERVICE_STATIONS"))
    @ToString.Exclude
    @JsonIgnore
    private ServiceStation serviceStation;

}
