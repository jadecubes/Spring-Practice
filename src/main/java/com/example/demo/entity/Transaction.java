package com.example.demo.entity;
import com.example.demo.service.processing.Task;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "Transaction",
       indexes = {@Index (name="created_by_idx",columnList = "created_by ASC",unique = false)})

//https://stackoverflow.com/questions/3805584/please-explain-about-insertable-false-and-updatable-false-in-reference-to-the-jp
//https://iter01.com/599269.html
public class Transaction {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id",updatable = false, nullable = false, insertable = true, unique = true, columnDefinition = "CHAR(32) NOT NULL UNIQUE")
    private String transaction_id;

    @Column(name = "point_amount",updatable = false, nullable = false, insertable = true, unique = false, columnDefinition = "INTEGER")
    @Min(value = 1, message = "point_amount >=1")
    @Max(value = 10, message = "point_amount <=10")
    private Integer point_amount;

    @Column(name = "business_time",updatable = false, nullable = false, insertable = true, unique = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Past()
    private Date business_time;

    @Column(name = "process_cost",updatable = false, nullable = false, insertable = true, unique = false, columnDefinition = "INTEGER")
    @Min(value = 300, message = "process_cost >=300")
    @Max(value = 5000, message = "process_cost <=5000")
    private Integer process_cost;

    @Column(name = "created_by",updatable = false, nullable = false, insertable = true, unique = false, columnDefinition= "VARCHAR(60)")
    private String created_by;

    @Column(name = "created_time",updatable = false, nullable = false, insertable = true, unique = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_time;

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setPoint_amount(Integer point_amount) {
        this.point_amount = point_amount;
    }

    public void setBusiness_time(Date business_time) {
        this.business_time = business_time;
    }

    public void setProcess_cost(Integer process_cost) {
        this.process_cost = process_cost;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public void setValues(Task t){
        setBusiness_time(t.getBusiness_time());
        setTransaction_id(t.getTransaction_id());
        setPoint_amount(t.getPoint_amount());
        setProcess_cost(t.getProcess_cost());
        setCreated_by(t.getProcessor_name());
        setCreated_time(new Date());
    }
}
