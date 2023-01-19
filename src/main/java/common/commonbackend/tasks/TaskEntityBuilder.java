package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;

import java.time.LocalDate;
import java.time.Period;

public class TaskEntityBuilder {
    private Long id;
    private String name;
    private long initialPrice;
    private boolean done;
    private Room room;
    private LocalDate lastDoneDate = LocalDate.now();
    private LocalDate previousLastDoneDate = LocalDate.now();
    private long lastDoneUserId = 0;
    private long previousLastDoneUserId = 0;
    private Period repetitionRate = Period.ofDays(7);
    private LocalDate beginPeriodDate = LocalDate.now();
    private long lastDonePrice = 0;

    public TaskEntityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TaskEntityBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskEntityBuilder setInitialPrice(long initialPrice) {
        this.initialPrice = initialPrice;
        return this;
    }

    public TaskEntityBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

    public TaskEntityBuilder setRoom(Room room) {
        this.room = room;
        return this;
    }

    public TaskEntityBuilder setLastDoneDate(LocalDate lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
        return this;
    }

    public TaskEntityBuilder setPreviousLastDoneDate(LocalDate previousLastDoneDate) {
        this.previousLastDoneDate = previousLastDoneDate;
        return this;
    }

    public TaskEntityBuilder setLastDoneUserId(long lastDoneUserId) {
        this.lastDoneUserId = lastDoneUserId;
        return this;
    }

    public TaskEntityBuilder setPreviousLastDoneUserId(long previousLastDoneUserId) {
        this.previousLastDoneUserId = previousLastDoneUserId;
        return this;
    }

    public TaskEntityBuilder setRepetitionRate(Period repetitionRate) {
        this.repetitionRate = repetitionRate;
        return this;
    }

    public TaskEntityBuilder setBeginPeriodDate(LocalDate beginPeriodDate) {
        this.beginPeriodDate = beginPeriodDate;
        return this;
    }

    public TaskEntityBuilder setLastDonePrice(long lastDonePrice) {
        this.lastDonePrice = lastDonePrice;
        return this;
    }

    public TaskEntity createTaskEntity() {
        return new TaskEntity(id, name, initialPrice, done, room, lastDoneDate, previousLastDoneDate, lastDoneUserId, previousLastDoneUserId, repetitionRate, beginPeriodDate, lastDonePrice);
    }
}