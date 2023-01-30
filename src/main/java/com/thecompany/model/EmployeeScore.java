package com.thecompany.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "employeeScore")
public class EmployeeScore extends PanacheEntityBase {

    @Id
    @SequenceGenerator(
            name = "employeeScoreSequence",
            sequenceName = "employee_score_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(generator = "employeeScoreSequence", strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "score")
    public Integer score;
}
