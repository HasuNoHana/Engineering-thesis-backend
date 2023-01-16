package common.commonbackend.tasks;

import common.commonbackend.rooms.RoomDTO;

import java.time.LocalDate;

class TaskDTOBuilder {
    private Long id;
    private String name;
    private long initialPrice;
    private Long currentPrice;
    private boolean done;
    private RoomDTO room;
    private LocalDate lastDoneDate;

    private LocalDate previousLastDoneDate;
    private long lastDoneUserId;
    private long previousLastDoneUserId;
    private int repetitionRateInDays;

    TaskDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    TaskDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    TaskDTOBuilder setInitialPrice(long initialPrice) {
        this.initialPrice = initialPrice;
        return this;
    }

    TaskDTOBuilder setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    TaskDTOBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

    TaskDTOBuilder setRoom(RoomDTO room) {
        this.room = room;
        return this;
    }

    TaskDTOBuilder setLastDoneDate(LocalDate lastDoneDate) {
        this.lastDoneDate = lastDoneDate;
        return this;
    }

    TaskDTOBuilder setPreviousLastDoneDate(LocalDate previousLastDoneDate) {
        this.previousLastDoneDate = previousLastDoneDate;
        return this;
    }

    TaskDTOBuilder setLastDoneUserId(long lastDoneUserId) {
        this.lastDoneUserId = lastDoneUserId;
        return this;
    }

    TaskDTOBuilder setPreviousLastDoneUserId(long previousLastDoneUserId) {
        this.previousLastDoneUserId = previousLastDoneUserId;
        return this;
    }

    TaskDTOBuilder setRepetitionRateInDays(int repetitionRateInDays) {
        this.repetitionRateInDays = repetitionRateInDays;
        return this;
    }

    TaskDTO createTaskDTO() {
        return new TaskDTO(id, name, initialPrice, currentPrice, done, room, lastDoneDate,
                previousLastDoneDate, lastDoneUserId, previousLastDoneUserId, repetitionRateInDays);
    }
}