package de.davidbilge.kata.vvm.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Display {
    public void showMessage(String message) {
        log.info("Displaying: {}", message);
    }
}
