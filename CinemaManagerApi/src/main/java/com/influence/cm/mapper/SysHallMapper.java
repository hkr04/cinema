package com.influence.cm.mapper;

import com.influence.cm.domain.SysHall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysHallMapper {
    List<SysHall> findAllHalls(SysHall sysHall);

    SysHall findHallById(SysHall sysHall);

    int addHall(SysHall sysHall);

    int updateHall(SysHall sysHall);

    int deleteHall(SysHall sysHall);
}
