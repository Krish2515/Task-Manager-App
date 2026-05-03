package in.codegram.taskmgrapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String priority;
    private String status;

    // 🔥 ADD THIS
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}