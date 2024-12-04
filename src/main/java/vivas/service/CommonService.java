package vivas.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vivas.repo.RelationRepository;
import vivas.repo.UserRepository;
import vivas.repo.UserRoleRepository;
import vivas.repo.entity.User;
import vivas.service.dto.PmsDto;
import vivas.service.dto.PmsPjo;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CommonService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    //
    public List<PmsDto> obtainPms(String username) {
        log.info("obtainPms [{}]", username);
        List<PmsDto> lst = null;
        User u = userRepository.findByUsername(username).orElse(null);
        if (null != u) {
            List<Integer> gpids = userRoleRepository.gRolesByUserId(u.getId());
            if (null != gpids && gpids.size() > 0) {
                List<String> ids = gpids.stream().map(String::valueOf).toList();
                List<PmsPjo> pjoList = relationRepository.obtainPms(ids);
                if (!ObjectUtils.isEmpty(pjoList)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    lst = pjoList.stream().map(o -> {
                        String description = o.getDescription();
                        //log.info("description = {}", o.getDescription());
                        if (!StringUtils.isBlank(description)) {
                            try {
                                log.info("found permission: {}, description={}", o.getVal(), description);
                                return objectMapper.readValue(description
                                        , new TypeReference<List<PmsDto>>() {
                                        }).stream().toList();
                            } catch (Exception ex) {
                                log.error("loi cau hinh quyen ko dung format [{}, {}, {}]", o.getId(), o.getVal(),
                                        description, ex);
                                return null;
                            }
                        } else {
                            //log.info("cau hinh quyen empty, check db: cate.description [{}, {}]", o.getId(),
                            //        o.getVal());
                            return null;
                        }
                    }).filter(Objects::nonNull).flatMap(x2 -> x2.stream()).distinct().toList();
                } else {
                    log.info("obtainPms empty, groupPermissionId = {}", String.join(",", ids));
                }
            } else {
                log.info("error -> user chua duoc set nhom quyen. username={}", username);
            }
        }
        return lst;
    }
}
