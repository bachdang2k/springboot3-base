package vivas.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "obj_src", length = 500)
    private String objSrc;

    @Column(name = "obj_src_id", length = 200)
    private String objSrcId;

    @Column(name = "obj_dst", length = 500)
    private String objDst;

    @Column(name = "obj_dst_id", length = 200)
    private String objDstId;
}
