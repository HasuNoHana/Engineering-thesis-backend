package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;

import java.time.LocalDate;
import java.time.Period;

public class TaskBuilder {
    private Long id;
    private String name;
    private long initialPrice;
    private boolean done;
    private Room room;
    private LocalDate lastDoneDate;
    private LocalDate previousLastDoneDate;
    private long lastDoneUserId;
    private long previousLastDoneUserId;
    private Period repetitionRate;
    private LocalDate beginPeriodDate;
    private long lastDonePrice;

    public TaskBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TaskBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder setInitialPrice(long initialPrice) {
        this.initialPrice = initialPrice;
        return this;
    }

    public TaskBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

    public TaskBuilder setRoom(Room room) {
        this.room = room;
        return this;
    }

    public TaskBuilder setLastDoneDate(LocalDate lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
        return this;
    }

    public TaskBuilder setPreviousLastDoneDate(LocalDate previousLastDoneDate) {
        this.previousLastDoneDate = previousLastDoneDate;
        return this;
    }

    public TaskBuilder setLastDoneUserId(long lastDoneUserId) {
        this.lastDoneUserId = lastDoneUserId;
        return this;
    }

    public TaskBuilder setPreviousLastDoneUserId(long previousLastDoneUserId) {
        this.previousLastDoneUserId = previousLastDoneUserId;
        return this;
    }

    public TaskBuilder setRepetitionRate(Period repetitionRate) {
        this.repetitionRate = repetitionRate;
        return this;
    }

    public TaskBuilder setBeginPeriodDate(LocalDate beginPeriodDate) {
        this.beginPeriodDate = beginPeriodDate;
        return this;
    }

    public TaskBuilder setLastDonePrice(long lastDonePrice) {
        this.lastDonePrice = lastDonePrice;
        return this;
    }

    public Task createTask() {
        return new Task(id, name, initialPrice, done, room, lastDoneDate, previousLastDoneDate, lastDoneUserId,
                previousLastDoneUserId, repetitionRate, beginPeriodDate, lastDonePrice);
    }
}