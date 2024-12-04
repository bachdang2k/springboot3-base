package vivas.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import vivas.repo.enums.StatusCate;

import java.time.LocalDateTime;

@Entity
@Table(name = "cate")
@Getter
@Setter
public class Cate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cd;
    private String name;
    private String val;
    private Integer ord;
    private StatusCate status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column(name = "parent_id")
    private Integer parentId;
}
