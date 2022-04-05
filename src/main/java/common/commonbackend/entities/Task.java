package common.commonbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "TASK")
public class Task {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private final Long id;

    @Column(name = "NAME")
    private final String name;
}
