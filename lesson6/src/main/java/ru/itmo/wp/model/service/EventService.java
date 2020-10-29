package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    EventRepository eventRepository = new EventRepositoryImpl();

    public void register(Event event) {
        eventRepository.save(event);
    }
}
