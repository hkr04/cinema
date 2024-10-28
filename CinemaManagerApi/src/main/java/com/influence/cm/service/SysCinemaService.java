package com.influence.cm.service;

import com.influence.cm.domain.SysCinema;


public interface SysCinemaService {

    SysCinema findCinema();

    int updateCinema(SysCinema sysCinema);

    SysCinema findCinemaById(Long id);

}
