package vivas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vivas.repo.entity.Relation;
import vivas.service.dto.PmsPjo;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
    @Query(value = "SELECT c.id, c.val, c.description " +
            "FROM relation r " +
            "inner join cate c " +
            "on r.obj_src = c.cd " +
            "and r.obj_src_id  = c.val " +
            "WHERE obj_dst = 'NHOM_QUYEN' " +
            "and obj_dst_id in (?1) and c.status = 1 " +
            "union all select id, val, description from cate where cd='NHOM_QUYEN_DETAIL' and val='common' " +
            "and status = 1 ", nativeQuery = true)
    List<PmsPjo> obtainPms(List<String> pmsids);

    @Query(value = "select objSrcId from Relation where objDst = :objDst and objDstId = :objDstId")
    List<String> getByObjDstAndObjDstId(String objDst, String objDstId);

    //@Query(value = "select objDstId from Relation where objDst = :objDst and objDstId = :objDstId")
    List<Relation> getByObjSrcAndObjSrcIdAndObjDst(String objSrc, String objSrcId, String objDst);

    void deleteByObjDstAndObjDstId(String objDst, String objDstId);

    void deleteByObjSrcAndObjSrcId(String obj, String objId);

    @Query(value = "select count(1) from user where group_permission_id=?1", nativeQuery = true)
    Integer checkUserLinkedGrpPermission(Integer groupPermissionId);

    @Query(value = "select objDstId from Relation where objSrc = :objSrc and objSrcId = :objSrcId and objDst = :objDst")
    List<String> getValByObjSrcAndObjSrcId(String objSrc, String objSrcId, String objDst);

    @Query(value = "select obj_src_id from relation where obj_src = ?1 and obj_dst = ?2 " +
            " and CAST(obj_dst_id as unsigned integer) = cast(?3 as unsigned integer)", nativeQuery = true)
    List<String> getValByObjDstAndDstId(String objSrc, String objDst, String objDstId);

    @Query(value = "select obj_src_id from relation where obj_src = ?1 and obj_dst = ?2 " +
            " and CAST(obj_dst_id as unsigned integer) >= cast(?3 as unsigned integer)", nativeQuery = true)
    List<String> getValByObjDstAndDstIdGt(String objSrc, String objDst, String objDstId);
}
