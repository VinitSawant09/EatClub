package com.eatclub.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimePoint implements Comparable<TimePoint> {
    LocalTime time;
    int delta; // +1 for deal start, -1 for deal end

    public TimePoint(LocalTime time, int delta) {
        this.time = time;
        this.delta = delta;
    }

    @Override
    public int compareTo(TimePoint other) {
        return this.time.compareTo(other.time);
    }
}

