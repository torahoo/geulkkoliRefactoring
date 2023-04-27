package com.geulkkoli.domain.calendar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ImplCalendarRepository implements CalendarRepository{

    private final EntityManager em;

    @Override
    public Calendar save(Calendar calendar) {
        em.persist(calendar);
        return calendar;
    }

    @Override
    public Optional<Calendar> findById(Long calendarId) {
        Calendar calendar = em.find(Calendar.class, calendarId);
        return Optional.of(calendar);
    }

    @Override
    public List<Calendar> findAll() {
        return em.createQuery("select c from Calendar c", Calendar.class)
                .getResultList();
    }

    public void clear () { em.close();}
}
