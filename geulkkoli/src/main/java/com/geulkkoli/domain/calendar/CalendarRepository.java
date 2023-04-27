package com.geulkkoli.domain.calendar;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository {

    Calendar save (Calendar calendar);

    Optional<Calendar> findById (Long calendarId);

    List<Calendar> findAll();

}
